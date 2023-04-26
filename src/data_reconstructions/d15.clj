(ns data-reconstructions.d15)
;Tarih: 20230426
;Girdi:
;
;{1 {:id 1
;:city "Istanbul"
;:name {:first "ali" :last "veli"}}
; 2 {:id 2
;:city "Ankara"
;:name {:first "batu" :last "can"}}}

;Çıktı:
;
;[[1 "Istanbul" "ali" "veli"]
; [2 "Ankara" "batu" "can"]]

(comment
  (def x {1 {:id 1 :city "Istanbul" :name {:first "ali" :last "veli"}}
          2 {:id 2 :city "Ankara" :name {:first "batu" :last "can"}}})

  (def x1 {:id 2 :city "Ankara" :name {:first "batu" :last "can"}})

  (get-in x1 [:name :last])
  ;=> "can"
  (let [id (get-in x1 [:id])
        city (get-in x1 [:city])
        name (get-in x1 [:name :first])
        last (get-in x1 [:name :last])] [id city name last])
  ;=> [2 "Ankara" "batu" "can"]
  (defn f1 [m]
    (let [id (get-in (second m) [:id])
          city (get-in (second m) [:city])
          name (get-in (second m) [:name :first])
          last (get-in (second m) [:name :last])] [id city name last])
    )
  (map identity x)
  ;=>
  ;([1 {:id 1, :city "Istanbul", :name {:first "ali", :last "veli"}}]
  ; [2 {:id 2, :city "Ankara", :name {:first "batu", :last "can"}}])

  (map f1 x)
  ;=> ([1 "Istanbul" "ali" "veli"] [2 "Ankara" "batu" "can"])
  (into [] (map f1 x))
  ;=> [[1 "Istanbul" "ali" "veli"] [2 "Ankara" "batu" "can"]]

  ;end
,)