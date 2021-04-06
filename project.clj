(defproject gcp-ser-clj "0.7.1"
  :description "A Clojure library trying to make reporting errors to Google Cloud's Error Reporting system (Stackdriver) simpler."
  :url "https://github.com/valerauko/gcp-ser-clj"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/tools.logging "1.1.0"]
                 [com.google.cloud/google-cloud-errorreporting "0.120.36-beta"]]
  :repl-options {:init-ns gcp-ser-clj.core}
  :profiles {:dev {:dependencies
                   [[org.clojure/clojure "1.10.3"]]}})
