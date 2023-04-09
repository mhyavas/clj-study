(ns data-reconstructions.d05)

;Girdi:
;
;```clojure
;{1 {:id 1 :name "ali" :surname "veli"}
; 2 {:id 2 :name "batu" :surname "can"}}
;```
;
;Çıktı:
;
;```clojure
;[[1 "ali" "veli"]
; [2 "batu" "can"]]
;```

(comment

  (def x{1 {:id 1 :name "ali" :surname "veli"}
          2 {:id 2 :name "batu" :surname "can"}})

  (map type x)
  ;=> (clojure.lang.MapEntry clojure.lang.MapEntry)
  (vals x)
  ;=> ({:id 1, :name "ali", :surname "veli"} {:id 2, :name "batu", :surname "can"})
  (keys x)
  ;=> (1 2)
  (def x1 {:id 1, :name "ali", :surname "veli"})
  (vals x1)
  ;=> (1 "ali" "veli")
  (into [] (vals x1))
  ;=> [1 "ali" "veli"]
  (defn inner_func [m]
    (into [] (vals m))
    )
  (into [] (map inner_func (vals x)))
  ;=> [[1 "ali" "veli"] [2 "batu" "can"]]


  ;end
  )