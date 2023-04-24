(ns data-reconstructions.d10
  (:require [clojure.string :as str]))
(require '[clojure.string :as str])
(require '[clojure.walk :as wlk])
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


  (map identity x1)
  ;=> ([:id 1] [:name "ali"] [:surname "veli"])


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
  (map identity (map f1 (vals x)))
  ;=> (([:name "ali"] [:surname "veli"])
  ; ([:name "batu"] [:surname "can"]))

  (def x2 (first (map identity (map f1 (vals x)))))
  ;Ilk ifade uzerinden debug icin x2 objesini olusturdum
  (identity x2)
  ;=> ([:name "ali"] [:surname "veli"])
  (map #(str/includes? % "a") (second x2))
  ;=> (true false)
  (map (fn [v] (identity (second v)))  x2)
  ;=> ("ali" "veli")
  (map (fn [v] (str/includes? (second v) "a")) x2)
  ;=> (true false)
  (filter (fn [v] (str/includes? (second v) "a")) x2)
  ;=> ([:name "ali"])
  (filter (fn [v] (str/includes? (second v) "a")) (map f1 (vals x)))
  ;=> (([:name "ali"] [:surname "veli"]) ([:name "batu"] [:surname "can"]))
  (defn f1 [m]
    (filter (fn [v] (str/includes? (second v) "a")) m))
  (map f1 (vals x) )
  ;=> (([:name "ali"]) ([:name "batu"] [:surname "can"]))
  (require 'clojure.walk)
  (reduce into [] (map f1 (vals x)))
  ;=> [[:name "ali"] [:name "batu"] [:surname "can"]]
  (defn f2 [[k v]]
    [ (name k) v])
  (map f2 [[:name "ali"] [:name "batu"] [:surname "can"]])
  ;=> (["name" "ali"] ["name" "batu"] ["surname" "can"])

  ;end
  )