
(ns clerk-study.registration
  (:require [nextjournal.clerk :as clerk]))
(require '[clojure.walk :refer [postwalk]])
;Student registration system data template
(def db
  {:department {101 {:id 101 :title "Matematik" :students []}
                102 {:id 102 :title "Fizik" :students []}
                103 {:id 103 :title "Sosyoloji" :students []}}
   :student {}})

;String doesn't have good look at the clerk table. We need the data in a vector.
(defn to-clerk [m]
  (postwalk
    #(if
       (string? %)
       (vector %)
       %)
    m))

(def form1 {:name "Can Ali" :department/id 101})
(def form2 {:name "Ali Deniz" :department/id 102})

(to-clerk (vals (db :department)))
(clerk/table (to-clerk (vals (db :department))))