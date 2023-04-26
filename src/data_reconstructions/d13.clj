(ns data-reconstructions.d13
  (:require
    [clojure.string :as str]))
;Tarih: 20230426
;Girdi:
;
;{1 {:id 1
;:name {:first "ali" :last "veli"}}
; 2 {:id 2
;:name {:first "batu" :last "can"}}}
;
;Çıktı:
;
;[[1 "ali" "veli"]
; [2 "batu" "can"]]

(comment
  (def x {1 {:id 1 :name {:first "ali" :last "veli"}}
          2 {:id 2 :name {:first "batu" :last "can"}}})

  (def x1 {:id 1 :name {:first "ali" :last "veli"}})

  (defn f1 [m]
    (vals (second m)))
  (map f1 x)
  ;=> ((1 {:first "ali", :last "veli"}) (2 {:first "batu", :last "can"}))
  (defn f2 [m]
    (vals (second (vals (second m)))))
  (map f2 x)
  ;=> (("ali" "veli") ("batu" "can"))
  (defn f3 [m]
    (into [(first (vals (second m)))] (f2 m) ))
  (map f3 x)
  ;=> ([1 "ali" "veli"] [2 "batu" "can"])
  (into [] (map f3 x))
  ;=> [[1 "ali" "veli"] [2 "batu" "can"]]

  ;a02:
  (defn f5 [m]
    (if (fn [_k v] (.contains v "a"))
      (into [] (vals (second m)))
      nil)
    )
  (map f5 x)
  ;=> ([1 "ali" "veli"] [2 "batu" "can"])
  ;end
  )