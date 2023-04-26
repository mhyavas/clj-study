(ns data-reconstructions.d11
  (:require
    [clojure.string :as str]))
; Tarih: 20230420
;  Girdi:
;
;  {1 {:id 1 :name "ali" :surname "veli"}
;   2 {:id 2 :name "batu" :surname "can"}}
;
;  Arama anahtar kelimesi:
;
;  "a"
;
;  Bu anahtar kelimeyi tüm property'lerde arayın. Eşleşen property'leri aşağıdaki formda dönün:
;
;  Çıktı:
;
;  [[1 "ali"] [2 "batu"] [2 "can"]]

(comment
  (def x {1 {:id 1 :name "ali" :surname "veli"}
          2 {:id 2 :name "batu" :surname "can"}})
  (def x1 {:id 1 :name "ali" :surname "veli"})

  (filter (fn [v] (str/includes? (second v) "a")) x1)
  ;=> ([:name "ali"])

  (map identity (vals x))
  ;=> ({:id 1, :name "ali", :surname "veli"} {:id 2, :name "batu", :surname "can"})
  (defn f1 [m]
    (second m))
  (map f1 x)
  ;=> ({:id 1, :name "ali", :surname "veli"} {:id 2, :name "batu", :surname "can"})
  (defn f2 [m]
    (map identity (second m)))
  (map f2 x)
  ;=> (([:id 1] [:name "ali"] [:surname "veli"]) ([:id 2] [:name "batu"] [:surname "can"]))
  (first (map f2 x))
  ;=> ([:id 1] [:name "ali"] [:surname "veli"])
  (defn f3 [m]
    (filter (fn [v] (str/includes? (second v) "a")) m))
  (f3 x)
  ;=> ([1 {:id 1, :name "ali", :surname "veli"}]
  ;    [2 {:id 2, :name "batu", :surname "can"}])
  (defn f4 [m]
    (filter (fn [v] (str/includes? (second v) "ali")) m))
  (f4 x)
  ;=> ([1 {:id 1, :name "ali", :surname "veli"}])
  (defn f5 [m]
    (if (fn [_k v] (.contains v "a"))
      (into [] (vals (second m)))
      nil)
    )
  (map f5 x)
  ;=> ([1 "ali" "veli"] [2 "batu" "can"])

  (defn f6 [m]
    (map #(if (.contains (vals %) "a")
            true
            false) m))

  (f6 x)


  ;end
  )
