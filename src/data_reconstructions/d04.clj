(ns data-reconstructions.d04)

;
;Girdi:
;
[{:id 1 :name "ali" :surname "veli"}
 {:id 2 :name "batu" :surname "can"}
 ]
;Çıktı:

[[1 "ali" "veli"]
 [2 "batu" "can"]
 ]
;
(comment
  (def x [{:id 1 :name "ali" :surname "veli"}
          {:id 2 :name "batu" :surname "can"}
          ])

  (def x1 {:id 1 :name "ali" :surname "veli"})

  (vals x1)
  ;=> (1 "ali" "veli")
  (into [] (vals x1))
  ;=> [1 "ali" "veli"]
  (defn inner_func [m]
    (into [] (vals m))
    )
  (into [] (map inner_func x))
  ;=> [[1 "ali" "veli"] [2 "batu" "can"]]
  ;end
  )
