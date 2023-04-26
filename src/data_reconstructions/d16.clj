(ns data-reconstructions.d16
  (:require
    [clojure.string :as str]))
;Tarih: 20230426
;
;  Girdi:
;  {1 {:id 1
;  :city "Istanbul"
;  :name {:first "ali" :last "veli"}}
;   2 {:id 2
;  :city "Ankara"
;  :name {:first "batu" :last "can"}}}

;
;  Arama anahtar kelimesi:
;
;  "ankara"

;  Bu anahtar kelimeyi `:city` property'sinde arayın. Eşleşen (match eden) property'lerin objelerini dönün.
;
;  Çıktı:
;
;  [{:id 2
;  :city "Ankara"
;  :name {:first "batu" :last "can"}}]

(comment

  (def x {1 {:id 1 :city "Istanbul" :name {:first "ali" :last "veli"}}
          2 {:id 2 :city "Ankara" :name {:first "batu" :last "can"}}})
  (def x1 {:id 2 :city "Ankara" :name {:first "batu" :last "can"}})

  (get-in x1 [:city])
  ;=> "Ankara"
  (str/includes? (get-in x1 [:city]) "Ankara")
  ;=> true
  (defn f1 [m]
    (str/includes? (m :city) "Ankara")
    )
  (vals x)
  ;=>
  ;({:id 1, :city "Istanbul", :name {:first "ali", :last "veli"}}
  ; {:id 2, :city "Ankara", :name {:first "batu", :last "can"}})

  (map f1 (vals x))
  ;=> (false true)
  (defn f2 [m]
    (if (str/includes? (m :city) "Ankara")
      m
      nil))

  (map f2 (vals x))
  ;=> (nil {:id 2, :city "Ankara", :name {:first "batu", :last "can"}})
  (remove nil? (map f2 (vals x)))
  ;=> ({:id 2, :city "Ankara", :name {:first "batu", :last "can"}})
  (into [] (remove nil? (map f2 (vals x))))
  ;=> [{:id 2, :city "Ankara", :name {:first "batu", :last "can"}}]



  ;end
  )