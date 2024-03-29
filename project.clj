(defproject gcp-ser-clj "0.8.2"
  :scm {:name "git"
        :url "https://github.com/valerauko/gcp-ser-clj"
        :tag "v0.8.1"}
  :description "A Clojure library trying to make reporting errors to Google Cloud's Error Reporting system (Stackdriver) simpler."
  :url "https://github.com/valerauko/gcp-ser-clj"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/tools.logging "1.2.4"]
                 [com.google.cloud/google-cloud-errorreporting "0.148.0-beta"]]
  :repl-options {:init-ns gcp-ser-clj.core}
  :deploy-repositories {"github" {:url "https://maven.pkg.github.com/valerauko/gcp-ser-clj"
                                  :username :env/github_user
                                  :password :env/github_token}
                        "clojars" {:url "https://repo.clojars.org/"
                                   :username :env/clojars_user
                                   :password :env/clojars_token}}
  :profiles {:dev {:dependencies
                   [[org.clojure/clojure "1.10.3"]]
                   :global-vars {*warn-on-reflection* true
                                 *unchecked-math* :warn-on-boxed}
                   :plugins [[lein-ancient "0.7.0"
                              :exclusions [org.clojure/clojure]]]}
             :clj1.9.0 {:dependencies
                        [[org.clojure/clojure "1.9.0"]]}
             :clj1.10.3 {:dependencies
                         [[org.clojure/clojure "1.10.3"]]}
             :clj1.11.1 {:dependencies
                         [[org.clojure/clojure "1.11.1"]]}})
