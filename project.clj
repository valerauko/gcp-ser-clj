(defproject gcp-ser-clj "0.8.1"
  :description "A Clojure library trying to make reporting errors to Google Cloud's Error Reporting system (Stackdriver) simpler."
  :url "https://github.com/valerauko/gcp-ser-clj"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/tools.logging "1.1.0"]
                 [com.google.cloud/google-cloud-errorreporting "0.124.12-beta"]]
  :repl-options {:init-ns gcp-ser-clj.core}
  :deploy-repositories [["github" "https://maven.pkg.github.com/valerauko/gcp-ser-clj"]]
  :profiles {:dev {:dependencies
                   [[org.clojure/clojure "1.10.3"]]}
             :clj1.9.0 {:dependencies
                        [[org.clojure/clojure "1.9.0"]]}
             :clj1.10.0 {:dependencies
                         [[org.clojure/clojure "1.10.0"]]}
             :clj1.10.1 {:dependencies
                         [[org.clojure/clojure "1.10.1"]]}
             :clj1.10.2 {:dependencies
                         [[org.clojure/clojure "1.10.2"]]}
             :clj1.10.3 {:dependencies
                         [[org.clojure/clojure "1.10.3"]]}
             :clj1.11.1 {:dependencies
                         [[org.clojure/clojure "1.11.1"]]}})
