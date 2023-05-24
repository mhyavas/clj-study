(ns clerk-study.registration-2
  (:require [nextjournal.clerk :as clerk]))

^{::clerk/visibility {:code :hide}}

(require '[clojure.walk :refer [postwalk]])
^{::clerk/visibility {:code :hide}}

(require '[datomic.client.api :as d])
; ## Creating auto-increment id

^{::clerk/visibility {:code :hide}}
(def st {:id [] :name [] :department/id []})

^{::clerk/visibility {:code :hide}}
(clerk/table st)

(def form1 {:id 1 :name "Can Ali" :department/id [[:id 101 :title "Matematik"]]})
(def form2 {:name "Ali Deniz" :department/id [[:id 102 :title "Fizik"] [:id 101 :title "Matematik"]]})
(def form3 {:name "Mahmut Can" :department/id [[:id 103 :title "Sosyoloji"]]})
(def form4 {:name "Nazım Taş" :department/id [[:id 101 :title "Matematik"]]})
^{::clerk/visibility {:code :hide}}
(comment

  (def record1 (-> st
                   (update-in [:id] conj (form1 :id))
                   (update-in [:name] conj (form1 :name))
                   (update-in [:department/id] conj (form1 :department/id))))
  (identity record1)
  ;=> {:id [nil], :name ["Can Ali"], :department/id [101]}

  (def test1 (assoc-in {} [] form1))

  (apply inc (record1 :id))
  (def record2 (-> record1
               (update-in [:id] conj (apply inc (record1 :id)))
               (update-in [:name] conj (form2 :name))
               (update-in [:department/id] conj (form2 :department/id))
               ))
  (identity record2)
  ;=> {:id [1 2], :name ["Can Ali" "Ali Deniz"], :department/id [101 102]}
  (last (record2 :id))
  ;=> 2
  (def record3 (-> record2
                   (update-in [:id] conj (inc (last (record2 :id))))
                   (update-in [:name] conj (form3 :name))
                   (update-in [:department/id] conj (form3 :department/id))
                   ))
  ;Assoc-in
  (identity record3)
  ;=> {:id [1 2 3], :name ["Can Ali" "Ali Deniz" "Mahmut Can"], :department/id [101 102 103]}

  ;After changing department id formation from ":id 101" to [[:id 101 :title "Matematik"]]

  (form3 :department/id)
  :=> [[:id 103 :title "Sosyoloji"]]

  (map identity (form2 :department/id))
  ;=> ([:id 102 :title "Fizik"] [:id 101 :title "Matematik"])

  (defn f1 [xs]
    (last xs)
    )

  (map f1 (form2 :department/id))
  ;=> ("Fizik" "Matematik")

  (def record5 (-> st
                 (update-in [:id] conj (form1 :id))
                 (update-in [:name] conj (form1 :name))
                 (update-in [:department/id] conj (map f1 (form2 :department/id)))))
  (identity record5)
  ;=> {:id [1], :name ["Can Ali"], :department/id [("Fizik" "Matematik")]}
  (def record6 (-> record5
                   (update-in [:id] conj (inc (last (record5 :id))))
                   (update-in [:name] conj (form3 :name))
                   (update-in [:department/id] conj (map f1 (form3 :department/id))) ))

  (identity record6)
  => {:id [1 2], :name ["Can Ali" "Mahmut Can"], :department/id [("Fizik" "Matematik") ("Sosyoloji")]}
  ;
  (def record4 (-> record3
                   (update-in [:id] conj (inc (last (record3 :id))))
                   (update-in [:name] conj (form4 :name))
                   (update-in [:department/id] conj (form4 :department/id))
                   ))
  (identity record4)
  ;=> {:id [1 2 3 4], :name ["Can Ali" "Ali Deniz" "Mahmut Can" "Nazım Taş"], :department/id [101 102 103 101]}
  ;end
)

(def record1 (-> st
                 (update-in [:id] conj (form1 :id))
                 (update-in [:name] conj (form1 :name))
                 (update-in [:department/id] conj (form1 :department/id))))


(def record2 (-> record1
                 (update-in [:id] conj (inc (last(record1 :id))))
                 (update-in [:name] conj (form2 :name))
                 (update-in [:department/id] conj (form2 :department/id))
                 ))

(def record3 (-> record2
                 (update-in [:id] conj (inc (last (record2 :id))))
                 (update-in [:name] conj (form3 :name))
                 (update-in [:department/id] conj (form3 :department/id))
                 ))

(clerk/table record3)


; ### After changing formation of the departments
(defn f1 [xs]
  (last xs)
  )
(def record5 (-> st
                 (update-in [:id] conj (form1 :id))
                 (update-in [:name] conj (form1 :name))
                 (update-in [:department/id] conj (map f1 (form2 :department/id)))))
(def record6 (-> record5
                 (update-in [:id] conj (inc (last (record5 :id))))
                 (update-in [:name] conj (form3 :name))
                 (update-in [:department/id] conj (map f1 (form3 :department/id))) ))

(clerk/table record6)
