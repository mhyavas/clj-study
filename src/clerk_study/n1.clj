(ns clerk-study.n1)

(require '[nextjournal.clerk :as clerk])

;TEST 123
(def form
  {:name nil
   :department nil})

;; - e01: map of seqs olması gerekiyor. map of nil hata veriyor:
;;
(clerk/table
    {:name [nil]
     :department [nil]})

