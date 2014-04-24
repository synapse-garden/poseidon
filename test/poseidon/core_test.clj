(ns poseidon.core-test
  (:require [clojure.test :refer :all]
            [poseidon.core :refer :all]))

(def error-responses
  {:access-denied "{\"status\":\"ERROR\",\"error_message\":\"Access Denied\",\"message\":\"Access Denied\"}"
   })

(deftest api-working-test
  (testing "Checking to make sure API queries properly."
    (let rawJSON (basic-query))
    (is (= rawJSON error-responses :access-denied))))
