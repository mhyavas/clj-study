(ns data-reconstructions.let-egzersiz)
;Tarih: 20230424

; q: f6'yı let içinde tanımlamak yerine doğrudan f5'i neden kullanmadık?
; ödev egzersizi
(require '[clojure.string :as str])

(comment
  (def x {1 {:id 1 :name "ali" :surname "veli"}
          2 {:id 2 :name "batu" :surname "can"}})
  (def fields '(:id :name :surname))
  (def s "ali")
  (defn f4 [fields s [_k v]]
    (some #(str/includes? (% v) s) fields))

  (def f5
    (partial f4 '(:id :name :surname) "ali"))

  (defn xform4 [m]
    (filter f5 m))
  (xform4 x)
  ;=> ([1 {:id 1, :name "ali", :surname "veli"}])
  (defn filter-map-by-str3 [data s & fields]
    (let
      [f6 (partial f4 fields s)
       xform5 #(filter f6 %)]
      (into {} (xform5 data))))
  (filter-map-by-str3 x "batu" :id :name :surname)
  ;=> {2 {:id 2, :name "batu", :surname "can"}}

  (defn test1 [data s & fields]
    (let [xform5 #(filter f5 %)]
      (into {} (xform5 data))
      )
    )

  (test1 x s :id :name :surname)
  ;=> {1 {:id 1, :name "ali", :surname "veli"}}
  (test1 x "batu" :id :name :surname)
  ;=> {1 {:id 1, :name "ali", :surname "veli"}}

  ;f5'i direkt olarak çağırdığımızda aranacak string değeri değiştirelemez oluyor.
  ; Bu yüzden de let'in içinde fonksiyonu tekrardan bindinglemek gerekiyor.





  ;end


,)
