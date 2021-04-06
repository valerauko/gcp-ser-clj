(ns gcp-ser-clj.gcp
  (:require [clojure.tools.logging :as log])
  (:import [com.google.api.gax.rpc
            ApiException]
           [com.google.cloud.errorreporting.v1beta1
            ReportErrorsServiceClient]
           [com.google.devtools.clouderrorreporting.v1beta1
            ErrorContext
            ReportedErrorEvent
            ServiceContext
            SourceLocation]))

(defn ^ServiceContext svc-ctx
  [{:keys [service-name service-version]}]
  (let [builder (ServiceContext/newBuilder)]
    (when service-name
      (.setService builder service-name))
    (when service-version
      (.setVersion builder service-version))
    (.build builder)))

(defn string-trace
  [^Throwable ex]
  (with-open [str-w (java.io.StringWriter.)
              prn-w (java.io.PrintWriter. str-w)]
    (.printStackTrace ex prn-w)
    (.toString str-w)))

(defn ex->loc
  [^Throwable exception]
  (let [frame (-> exception (.getStackTrace) (nth 0))]
    (println frame)
    (-> (SourceLocation/newBuilder)
        (.setFilePath (.getFileName frame))
        (.setLineNumber (.getLineNumber frame))
        (.setFunctionName (.getMethodName frame))
        (.build))))

(defn ex->error-ctx
  [^Throwable exception]
  (let [loc (ex->loc exception)]
    (-> (ErrorContext/newBuilder)
        (.setReportLocation loc)
        (.build))))

(defn ^ReportedErrorEvent ex->event
  [exception opts]
  (-> (ReportedErrorEvent/newBuilder)
      (.setServiceContext (svc-ctx opts))
      (.setMessage (string-trace exception))
      (.build)))

(defn report
  [exception {:keys [project-name client] :as opts}]
  (let [project-id (format "projects/%s" (name project-name))
        event-obj (ex->event exception opts)
        api-call (fn api-call [^ReportErrorsServiceClient c]
                   (try
                     (.reportErrorEvent c project-id event-obj)
                     (log/info "Reported error to GCP" exception)
                     (catch ApiException e
                       (log/error "Failed to report error to GCP" e))))]
    (if client
      (api-call client)
      (with-open [ephemeral-client (ReportErrorsServiceClient/create)]
        (api-call ephemeral-client)))))
