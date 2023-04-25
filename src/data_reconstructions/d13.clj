(ns data-reconstructions.d13
  (:require
    [clojure.string :as str]))

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

  (->> x1
       (vals))

  ;end
  ,)