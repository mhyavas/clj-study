(ns sof.sof-1)

; Goal:
;> (zip '(1 2 3) '(4 5 6))
;((1 4) (2 5) (3 6))

(defn g [a & b]
  [a b])

(g 1)
;=> [1 nil]
(g 1 2)
;=> [1 (2)]
(g 1 2 3)
;=> [1 (2 3)]
;&'den sonra gelen tum degerler bir listeye toplanir.

(comment

  (interleave [1 2 3 4] [5 6 7 8])
  ;=> (1 5 2 6 3 7 4 8)
  (partition 3 (interleave [1 2 3] [5 6 7] [8 9 10]))
  ;=> ((1 5 8) (2 6 9) (3 7 10))

  (defn zipper [& colls]
    (partition (count colls) (interleave colls)))

  (zipper [1 2 3] [5 6 7] [8 9 10])
  ;=> (([1 2 3] [5 6 7] [8 9 10]))
  (defn zipper2 [& colls]
    (partition (count colls) (apply interleave colls)))
  (zipper2 [1 2 3] [5 6 7] [8 9 10])
  ;=> ((1 5 8) (2 6 9) (3 7 10))

  ;q: interleave fonksiyonunu direkt olarak cagirdigimizda iki liste icinde vektorleri koymus.
  ; interleave fonksiyonunu aplly ile calistirdigimizda tek liste icinde istenilen listeleri varmis.
  ;Bunun altinda yatan mantigi merak ettim.

  ;end
,)