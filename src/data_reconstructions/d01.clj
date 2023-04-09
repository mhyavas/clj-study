(ns data-reconstructions.d01)
(comment
  ;girdi
  [{:name "ali" :surname "veli"}
   {:name "batu" :surname "can"}]

  ;cikti
  [["ali" "veli"]
   ["batu" "can"]]
  )

(comment

  (def x [{:name "ali" :surname "veli"}
          {:name "batu" :surname "can"}])

  (def x1 {:name "ali" :surname "veli"})
  (vals x1)
  ;=> ("ali" "veli")
  (into [] (vals x1))
  ;=> ["ali" "veli"]
  (defn vector_converter [v]
    (into [] (vals v)))

  (into [] (map vector_converter x ))
  ;end
  ,)
