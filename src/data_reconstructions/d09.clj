(ns data-reconstructions.d09)
;Tarih: 20230416

;Girdi:
;
;{1 {:id 1 :name "ali" :surname "veli"}
; 2 {:id 2 :name "batu" :surname "can"}}
;
;Arama anahtar kelimesi:
;"a"
;
;Bu anahtar kelimeyi tüm property'lerde arayın. Eşleşen property'lerin değerlerini dönün.
;
;Çıktı:
;
;["ali" "batu" "can"]

(comment
  (def x {1 {:id 1 :name "ali" :surname "veli"}
          2 {:id 2 :name "batu" :surname "can"}})
  (def x1 {:id 1 :name "ali" :surname "veli"})
  (def x2 {:id 2 :name "batu" :surname "can"})
  (require '[clojure.string :as str])

  (str/includes? (name :surname) "a")
  ;=> true

  (some #(str/includes? % "a") (vals x1))
  ;=> true
  (filter #(str/includes? % "a") (vals x1))
  ;=> ("ali")
  (filter #(str/includes? % "a") (vals x2))
  ;=> ("batu" "can")
  (defn f1 [m]
    (filter #(str/includes? % "a") (vals (second m)))
    )
  (map println x)
  ;[1 {:id 1, :name ali, :surname veli}]
  ;[2 {:id 2, :name batu, :surname can}]
  (map f1 x)
  ;=> (("ali") ("batu" "can"))
  (reduce cons (map f1 x))
  ;=> (("ali") "batu" "can")
  (into [] (reduce cons (map f1 x)))
  ;=> [("ali") "batu" "can"]
  (apply concat (map f1 x))
  ;=> ("ali" "batu" "can")
  (into [] (apply concat (map f1 x)))
  ;=> ["ali" "batu" "can"]

  ;end
,)
