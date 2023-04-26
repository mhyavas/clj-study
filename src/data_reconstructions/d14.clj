(ns data-reconstructions.d14
  (:require [clojure.string :as str]))
;Tarih: 20230426
;  Girdi:
;

;  {1 {:id 1
;  :name {:first "ali" :last "veli"}}
;   2 {:id 2
;  :name {:first "batu" :last "can"}}}
;
;  Çıktı:
;
;  {1 {:id 1
;  :name {:first "ali" :last "veli" :fullname "ali veli"}}
;   2 {:id 2
;  :name {:first "batu" :last "can" :fullname "batu can"}}}

(comment
  (def x {1 {:id 1 :name {:first "ali" :last "veli"}}
          2 {:id 2 :name {:first "batu" :last "can"}}})
  (def x1 {:id 1 :name {:first "ali" :last "veli"}})

  (x1 :name)
  ;=> {:first "ali", :last "veli"}
  (assoc-in (x1 :name) [:fullname] (vals (x1 :name)))
  ;=> {:first "ali", :last "veli", :fullname ("ali" "veli")}
  (str/join " " (vals (x1 :name)))
  ;=> "ali veli"
  (assoc-in (x1 :name) [:fullname] (str/join " " (vals (x1 :name))))
  ;=> {:first "ali", :last "veli", :fullname "ali veli"}

  (defn f0 [m]
    (second m))
  (map f0 x)
  ;=> ({:id 1, :name {:first "ali", :last "veli"}}
  ;    {:id 2, :name {:first "batu", :last "can"}})
  (defn f1 [m]
    (assoc-in ((second m)) [:fullname] (str/join " " (vals ((second m) :name))))
    )

  (map f1 x)
  ;=> ({:first "ali", :last "veli", :fullname "ali veli"}
  ;    {:first "batu", :last "can", :fullname "batu can"})

  (defn f2 [m]
    (assoc-in m [1 :name :fullname] (str/join " " (vals ((second m) :name))))
    )

  (map f2 x)
  ;=>
  ;([1 {:id 1, :name {:first "ali", :last "veli", :fullname "ali veli"}}]
  ; [2 {:id 2, :name {:first "batu", :last "can", :fullname "batu can"}}])

  (into (empty x) (map f2 x))
  ;=>
  ;{1 {:id 1, :name {:first "ali", :last "veli", :fullname "ali veli"}},
  ; 2 {:id 2, :name {:first "batu", :last "can", :fullname "batu can"}}}

  ;end
)