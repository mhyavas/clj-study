(ns data-reconstructions.d10)
;Tarih:20230417

;Girdi:
;
;{1 {:id 1 :name "ali" :surname "veli"}
; 2 {:id 2 :name "batu" :surname "can"}}
;
;Arama anahtar kelimesi:
;
;"a"
;
;Bu anahtar kelimeyi tüm property'lerde arayın. Eşleşen property'leri key-value ikilileri olarak dönün.
;
;Çıktı:
;
;[["name" "ali"] ["name" "batu"] ["surname" "can"]]

(comment
  (def x {1 {:id 1 :name "ali" :surname "veli"}
          2 {:id 2 :name "batu" :surname "can"}})
  (def x1 {:id 1 :name "ali" :surname "veli"})
  


  ;end
,)