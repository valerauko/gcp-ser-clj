(ns gcp-ser-clj.core
  "Simple Error Reporting for Google Cloud (Stackdriver)"
  (:require [gcp-ser-clj.gcp :as gcp]))

(defn report
  "Send error to the authenticated Google Cloud Error Reporting.

  If no options are provided, it falls back to application-wide GCP defaults.

  Currently the following options are recognized (all optional):
  | key | description |
  | -|-|
  | `project-name` | The GCP project name to use. |
  | `client` | A ReportErrorsServiceClient instance |
  | `service-name` | The Service to categorize the reported error under |
  | `service-version` | The version of the Service where the error occurred |"
  ([exception]
   (report exception {}))
  ([exception opts]
   (gcp/report exception opts)))

(defmacro with-error-reporting
  "Convenience macro that reports (then re-throws) any Throwable that occurs while executing its body.

  If a map is provided as first argument, it's used as the options map for [[report]]."
  [& body]
  (let [maybe-options (nth body 0)]
    (if (associative? maybe-options)
      `(try
         ~@(rest body)
         (catch Throwable e#
           (report e# ~maybe-options)
           (throw e#)))
      `(try
         ~@body
         (catch Throwable e#
           (report e#)
           (throw e#))))))
