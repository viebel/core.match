(ns clojure.core.match.test.regex
  (:use [clojure.core.match :only [match]])
  (:use clojure.core.match.regex)
  (:use clojure.test))

(deftest basic-regex
         (is (= (match ["asdf"]
                       [#"asdf"] 1
                       :else 2)
                1)))
