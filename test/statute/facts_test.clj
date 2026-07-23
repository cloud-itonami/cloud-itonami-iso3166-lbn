(ns statute.facts-test
  (:require [clojure.string :as str]
            [clojure.test :refer [deftest is]]
            [statute.facts :as facts]))

(deftest lbn-has-spec-basis
  (let [sb (facts/spec-basis "LBN")]
    (is (= 4 (count sb)))
    (is (every? #(str/starts-with? (:statute/url %) "http") sb))
    (is (every? :statute/law-number sb))))

(deftest unknown-jurisdiction-has-no-spec-basis
  (is (nil? (facts/spec-basis "ATL")))
  (is (nil? (facts/spec-basis "ZZZ"))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["LBN" "JPN" "ATL"])]
    (is (= 3 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["ATL" "JPN"] (:missing-jurisdictions c)))))

(deftest by-topic-filters
  (is (= ["lbn.law-244-2021-public-procurement"]
         (mapv :statute/id (facts/by-topic "LBN" :public-procurement))))
  (is (= ["lbn.labor-law-1946"]
         (mapv :statute/id (facts/by-topic "LBN" :labor))))
  (is (= ["lbn.investment-law-360-2001"]
         (mapv :statute/id (facts/by-topic "LBN" :foreign-investment))))
  (is (empty? (facts/by-topic "LBN" :data-protection)))
  (is (empty? (facts/by-topic "ATL" :labor))))
