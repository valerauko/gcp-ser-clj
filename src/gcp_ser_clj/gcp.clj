(ns gcp-ser-clj.gcp
  "Clojure wrapper utility around the Google Cloud Error Reporting Java library.

  Consider all functions in this namespace private and subject to change."
  (:require [clojure.tools.logging :as log])
  (:import [com.google.api.gax.rpc
            ApiException]
           [com.google.auth.oauth2
            ServiceAccountCredentials]
           [com.google.devtools.clouderrorreporting.v1beta1
            ErrorContext
            ReportedErrorEvent
            ReportErrorsServiceClient
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

(defn ^SourceLocation ex->loc
  [^Throwable exception]
  (let [^StackTraceElement frame (-> exception (.getStackTrace) (nth 0))]
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

(defn project-from-config
  []
  (let [^ServiceAccountCredentials creds
        (ServiceAccountCredentials/getApplicationDefault)]
    (.getProjectId creds)))

(defn api-call
  [^ReportErrorsServiceClient client
   ^String project-id
   ^ReportedErrorEvent event-obj]
  (try
    (.reportErrorEvent client project-id event-obj)
    (catch ApiException e
      (log/error "Failed to report error to GCP" e))))

(defn report
  ([exception]
   (report exception {}))
  ([exception {:keys [project-name client] :as opts}]
   (let [project-id (format "projects/%s" (name (or project-name
                                                    (project-from-config))))
         event-obj (ex->event exception opts)]
     (if client
       (api-call client project-id event-obj)
       (with-open [ephemeral-client (ReportErrorsServiceClient/create)]
         (api-call ephemeral-client project-id event-obj))))))
