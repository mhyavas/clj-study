(ns data-reconstructions.d07)

;Bu problemde bir girdi veri var, bir de bir arama kriteri var.
;
;Girdi:
;
;```clojure
;{1 {:id 1 :name "ali" :surname "veli"}
; 2 {:id 2 :name "batu" :surname "can"}}
;```
;
;Arama kriteri:
;
;```
;"ca"
;```
;
;Bu anahtar kelimenin geçtiği `surname` property'sine sahip objeleri bulun.
;
;Çıktı:
;
;```clojure
;{2 {:id 2 :name "batu" :surname "can"}}
;```

(comment
  (def x {1 {:id 1 :name "ali" :surname "veli"}
          2 {:id 2 :name "batu" :surname "can"}})
  (def x1 {:id 1 :name "ali" :surname "veli"})
  (def x2 {:id 2 :name "batu" :surname "can"})
  (x1 :surname)
  ;=> "veli"
  (.contains (x2 :surname) "ca")
  ;.contains fonksiyonunu bu link ile kesfettim
  ;https://stackoverflow.com/questions/26386766/check-if-string-contains-substring-in-clojure
  
  ;=> true


  ;end
  )

