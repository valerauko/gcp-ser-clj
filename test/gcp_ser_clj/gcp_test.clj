(ns gcp-ser-clj.gcp-test
  (:require [clojure.test :refer [deftest testing is]]
            [gcp-ser-clj.gcp :refer :all]))

(deftest svc-ctx-test
  (testing "when provided service-name option"
    (let [opts {:service-name "foo"}]
      (is (= "foo" (.getService (svc-ctx opts))))))
  (testing "when provided service-version option"
    (let [opts {:service-version "8.16.2-alpha7"}]
      (is (= "8.16.2-alpha7" (.getVersion (svc-ctx opts))))))
  (testing "when provided both name and version"
    (let [opts {:service-version "8.16.2-alpha7"
                :service-name "foo"}
          result (svc-ctx opts)]
      (is (= "foo" (.getService result)))
      (is (= "8.16.2-alpha7" (.getVersion result)))))
  (testing "when provided neither name nor version"
    (let [opts {}
          result (svc-ctx opts)]
      (is (= "" (.getService result)))
      (is (= "" (.getVersion result))))))

(deftest report-options-test
  (with-redefs [gcp-ser-clj.gcp/ex->event (constantly nil)
                gcp-ser-clj.gcp/api-call (fn [_ project _] project)]
    (testing ":project-name"
      (testing "when provided :project-name option"
        (is (= (report nil {:client "dummy" :project-name "foo"})
               "projects/foo")))
      (testing "without :project-name option"
        (with-redefs [gcp-ser-clj.gcp/project-from-config (constantly "bar")]
          (is (= (report nil {:client "dummy"})
                 "projects/bar")
              "uses the project id from the application-wide config"))))))
