(ns data-reconstructions.d16)
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

  
  ;end
,)