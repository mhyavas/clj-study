(ns data-reconstructions.d06)
; Tarih: 20230409

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
;{1 ["ali" "veli"]
; 2 ["batu" "can"]}
;

(comment
  (def x {1 {:id 1 :name "ali" :surname "veli"}
           2 {:id 2 :name "batu" :surname "can"}} )

  (vals x)
  ;=> ({:id 1, :name "ali", :surname "veli"} {:id 2, :name "batu", :surname "can"})
  (keys x)
  ;=> (1 2)
  (def x1 {:id 1, :name "ali", :surname "veli"})

  (rest (vals x1))
  ;=> ("ali" "veli")
  (into [] (rest (vals x1)))
  ;=> ["ali" "veli"]
  (first (vals x1))
  (array-map  (first (vals x1)) (into [] (rest (vals x1))))
  ;=> {1 ["ali" "veli"]}
  (defn f [m]
    (array-map  (first (vals m)) (into [] (rest (vals m))))
    )
  (inner_func x1)
  ;=> {1 ["ali" "veli"]}
  (seq (vals x))
  (apply array-map (map f (seq (vals x))))
  ;=> {{1 ["ali" "veli"]} {2 ["batu" "can"]}}
  (reduce map (apply array-map (map f (seq (vals x)))))
  ;=> [{1 ["ali" "veli"]} {2 ["batu" "can"]}]
  (reduce conj [{1 ["ali" "veli"]} {2 ["batu" "can"]}])
  ;=> {1 ["ali" "veli"], 2 ["batu" "can"]}

  (defn f1 [x]
    (reduce map (apply array-map (map f (seq (vals x)))))
    )
  (reduce conj (f1 x))
  ;=> {1 ["ali" "veli"], 2 ["batu" "can"]}



  ;end
  ,)
