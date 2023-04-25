(ns data-reconstructions.d12)

;Girdi:
;
;[{:id 1
;:name {:first "ali" :last "veli"}
; {:id 2
;:name {:first "batu" :last "can"}}}

;Çıktı:
;
;[["ali" "veli"]
; ["batu" "can"]]

(comment

  (def x [{:id 1 :name {:first "ali" :last "veli"}}
           {:id 2 :name {:first "batu" :last "can"}}])
  (def x1 {:id 1 :name {:first "ali" :last "veli"}})

  (get-in x1 [:name] )
  ;=> {:first "ali", :last "veli"}

  (into [] (vals {:first "ali", :last "veli"}))
  ;=> ["ali" "veli"]

  (defn f1 [m]
    (into [] (vals (get-in m [:name]))))
  (f1 x1)
  ;=> ["ali" "veli"]
  (map f1 x)
  ;=> (["ali" "veli"] ["batu" "can"])
  (into [] (map f1 x))
  ;=> [["ali" "veli"] ["batu" "can"]]


  ;end
  ,)


