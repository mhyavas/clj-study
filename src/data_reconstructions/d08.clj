(ns data-reconstructions.d08
  (:require [clojure.string :as str]))
; Tarih: 20230413

;Girdi:
;
;```clojure
;{1 {:id 1 :name "ali" :surname "veli"}
; 2 {:id 2 :name "batu" :surname "can"}}
;```
;
;Arama anahtar kelimesi:
;
;```
;"a"
;```
;
;Bu anahtar kelimeyi tüm property'lerde arayın. Eşleşen (match eden) property'lerin objelerini dönün.
;
;Çıktı:
;
;```clojure
;{1 {:id 1 :name "ali" :surname "veli"}
; 2 {:id 2 :name "batu" :surname "can"}}
;```

(comment
  (def x {1 {:id 1 :name "ali" :surname "veli"}
           2 {:id 2 :name "batu" :surname "can"}})
  (def x1 {:id 1 :name "ali" :surname "veli"})

  (def str_match "a")
  (keys x1)
  ;=> (:id :name :surname)

  ;a01:


  ;a02:
  (require 'clojure.walk)
  (clojure.walk/stringify-keys (keys x1))
  ;=> (:id :name :surname)
  (clojure.walk/stringify-keys x1)
  ;=> {"id" 1, "name" "ali", "surname" "veli"}
  (first (clojure.walk/stringify-keys x1))
  :=> ["id" 1]
  (first (first (clojure.walk/stringify-keys x1)))
  ;=> "id"
  (.contains (first (first (clojure.walk/stringify-keys x1))) str_match)
  ;=> false
  (map println x1)
  ;[:id 1]
  ;[:name ali]
  ;[:surname veli]

  (defn f [m]
    (name (first (clojure.walk/stringify-keys m)))
    #_(.contains (first (clojure.walk/stringify-keys m)) str_match)
    )
  (map f x1)
  ;=> ("id" "name" "surname")
  (defn f1 [m]
    (if (.contains (name (first (clojure.walk/stringify-keys m))) str_match)
      (->> x1) #_(select-keys x1 (keys x1)) #_(select-keys x1 (into [] ()))
      nil))

  #_(map f1 x1)
  ;=> (nil [:name "ali"] [:surname "veli"])

  (filter f1 x1)
  ;=> ([:name "ali"] [:surname "veli"])
  (select-keys x1 [:id :name :surname])
  ;=> {:id 1, :name "ali", :surname "veli"}

  (.contains (name :surname) "a")
  ;=> true
  (defn f2 [k]
    (.contains (name k) "a")
    )
  (filter f2 (keys x1))
  ;=> (:name :surname)
  (some (filter f2) x1)
  (f3 x)
  (require '[clojure.string :as str])
  (str/includes? (name :surname) "a")
  ;=> true

  (defn filter-map-by-str [data s & fields]
    (into (empty data)
          (filter (fn [[_k v]]
                    (some #(str/includes? (% v) s) fields)
                    )
                  )
          data)
    )
  ;src -> https://stackoverflow.com/questions/75895975/how-to-filter-values-on-maps-and-return-results
  (filter-map-by-str x "a" :id :name :surname)
  ;=> {1 {:id 1, :name "ali", :surname "veli"}, 2 {:id 2, :name "batu", :surname "can"}}

  (into (empty x) x)
  ;=> {1 {:id 1, :name "ali", :surname "veli"}, 2 {:id 2, :name "batu", :surname "can"}}
  (filter (fn [[_k v]]
            (some #(str/includes? (% v) "a") '(:id :name :surname))
            ) x )
  ;=> ([1 {:id 1, :name "ali", :surname "veli"}]
  ; [2 {:id 2, :name "batu", :surname "can"}])
  (some #(str/includes? % "a") '(:id :name :surname))
  ;=> true


  ;end
  )
