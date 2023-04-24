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
    (filter (fn [v] (str/includes? (second v) "a"))))
  (map f1 (vals x))
  ;=> (([:name "ali"]) ([:name "batu"] [:surname "can"]))
  (reduce into [] (map f1 x))
  ;=> [[:name "ali"] [:name "batu"] [:surname "can"]]

  (filter (reduce into [] (map f1 x))
   )

  ;end
  )
