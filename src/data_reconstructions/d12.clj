(ns data-reconstructions.d12)
;Tarih: 20230425
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

  ;a01:
  (defn f1 [m]
    (into [] (vals (get-in m [:name]))))
  (f1 x1)
  ;=> ["ali" "veli"]
  (map f1 x)
  ;=> (["ali" "veli"] ["batu" "can"])
  (into [] (map f1 x))
  ;=> [["ali" "veli"] ["batu" "can"]]

  ;a02:
  (defn f1 [m]
    (vals m))
  (map f1 x)
  ;=> ((1 {:first "ali", :last "veli"}) (2 {:first "batu", :last "can"}))
  (defn f2 [m]
    (second (vals m)))
  (map f2 x)
  ;=> ({:first "ali", :last "veli"} {:first "batu", :last "can"})
  (defn f3 [m]
    (vals (second (vals m))))
  (map f3 x)
  ;=> (("ali" "veli") ("batu" "can"))
  (defn f4 [m]
    (into [] (vals (second (vals m)))))

  (map f4 x)
  ;=> (["ali" "veli"] ["batu" "can"])
  (into [] (map f4 x))
  ;=> [["ali" "veli"] ["batu" "can"]]

  ;end
  ,)


