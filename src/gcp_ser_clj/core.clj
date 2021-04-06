(ns gcp-ser-clj.core
  "Simple Error Reporting for Google Cloud (Stackdriver)"
  (:require [gcp-ser-clj.gcp :as gcp]))

(defn report
  "Send error to the authenticated Google Cloud Error Reporting"
  ([exception]
   (report exception {}))
  ([exception opts]
   (gcp/report exception opts)))

(defmacro with-error-reporting
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
