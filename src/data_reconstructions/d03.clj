(ns data-reconstructions.d03)

;Girdi:
;
;
;[{:name "ali" :surname "veli"}
; {:name "batu" :surname "can"}]
;
;
;Çıktı:
;
;
;[{"name" "ali" "surname" "veli"}
; {"name" "batu" "surname" "can"}]
(comment
  (def x [{:name "ali" :surname "veli"}
           {:name "batu" :surname "can"}])

  (def x1 {:name "ali" :surname "veli"})
  (map name (apply concat x1))
  ;=>("name" "ali" "surname" "veli")
  ;Bu çözümü bu linkten aldım.
  ;https://stackoverflow.com/questions/16018242/convert-map-keys-and-values-to-string-array
  ; name fonksiyonu symbol ve keywordleri string olarak döndürüyor.
  (assoc ("name" "ali" "surname" "veli"))

  (require 'clojure.walk)
  (map clojure.walk/stringify-keys x1)
  ;=> ([:name "ali"] [:surname "veli"])
  (clojure.walk/stringify-keys x1)
  ;=> {"name" "ali", "surname" "veli"}
  (reduce into (seq {"name" "ali", "surname" "veli"}))
  ;=> ["name" "ali" "surname" "veli"]
  (into {} ["name" "ali" "surname" "veli"])
  (reduce concat (clojure.walk/stringify-keys x1))
  ;=> ("name" "ali" "surname" "veli")
  (defn reducer [m]
    (reduce into (clojure.walk/stringify-keys m))
    )
 (reducer x1)
  ;=> ["name" "ali" "surname" "veli"]

  (into [] (map reducer x))
  ;=> [["name" "ali" "surname" "veli"] ["name" "batu" "surname" "can"]]
  ;q1-> İç köşeli parentezi süslü paranteze çeviremedim
  ; Array-map'e çevirmem için key tanımlama lazım ama çıktı olarak string istendiğinden yapamadım.

  (set (map reducer x))
  ;=> #{["name" "batu" "surname" "can"] ["name" "ali" "surname" "veli"]}
  ;end
  )
