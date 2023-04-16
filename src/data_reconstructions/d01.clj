(ns data-reconstructions.d01)
;Tarih:20230405

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
  (defn f [v]
    (into [] (vals v)))

  (into [] (map f x ))
  ;=> [["ali" "veli"] ["batu" "can"]]
  ;end
  ,)
