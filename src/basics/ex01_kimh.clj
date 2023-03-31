(ns basics.ex01-kimh)

(let [a 1] a)

(when-let [a true] a)

(let [n 6]
  (cond
    (< n 3) "a"
    (< n 5) "b"
    :else "other")
  )

(boolean nil)
;;; Simply defined:
;;; Everything except false and nil is logically true in Clojure.

(nth '(3 4 2) 0)

(reduce (fn [rst x] (println(+ rst x)))  [1 2 3 4 5])
