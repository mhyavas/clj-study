(ns data-reconstructions.d10
  (:require [clojure.string :as str]))
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


  (map println x1)
  ;[:id 1]
  ;[:name ali]
  ;[:surname veli]
  (require '[clojure.string :as str])
  (require '[clojure.walk :as wlk])

  (map wlk/stringify-keys x1)
  ;=> ([:id 1] [:name "ali"] [:surname "veli"])
  (->> x1
       (filter #(str/includes? % "a"))
       )
  ;=> ([:name "ali"] [:surname "veli"])
  (filter #(str/includes? % "a") x1)
  ;=> ([:name "ali"] [:surname "veli"])
  (defn f1 [m]
    (filter #(str/includes? % "a") m)
    )

  (map f1 (vals x))
  ;=> (([:name "ali"] [:surname "veli"]) ([:name "batu"] [:surname "can"]))
  (map println (map f1 (vals x)))
  ;([:name ali] [:surname veli])
  ;([:name batu] [:surname can])
  (map filter   (map f1 (vals x)))
  (filter #(str/includes? % "a") (map f1 (vals x) ))



  ;end
)