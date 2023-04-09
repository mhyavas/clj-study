(ns basics.ex03-brianWill)

(comment

  ;end
,)

(quote 3)

(defn factorial [n]
  (if (= n 0) 1
              (loop [val n i n]
                (if (<= i 1) val
                             (recur (*' val (dec i)) (dec i)))))
  )
(factorial 4)

