(ns sof.sof-test)

(comment
  (flatten '(([1 2]) ([3 4] [5 6]) ([7 8])))
  ;=> (1 2 3 4 5 6 7 8)
  (flatten '([1 2] [3 4] [5 6] [7]))
  ;=> (1 2 3 4 5 6 7)

  ;For maps, we need to convert map to a seq

  (flatten (seq {:name "Hubert" :age 23}))
  ;=> (:name "Hubert" :age 23)

  (reduce #(.concat %1 %2) (repeat 3 "str"))
  ;=> "strstrstr"
  (repeat 3 "str")
  ;=> ("str" "str" "str")
  (def foo {:bar {:baz {:quux 123}}})

  (assoc foo :bar
             (assoc (:bar foo) :baz
                               (assoc (:baz (:bar foo)) :quux
                                                        (inc (:quux (:baz (:bar foo)))))))
  ;=> {:bar {:baz {:quux 124}}}

  (foo :bar)
  ;=> {:baz {:quux 123}}
  ;q1: Inner map valuelara nasil olusabilirim?
  ;rfr:https://stackoverflow.com/questions/15639060/clojure-get-nested-map-value

  (update-in foo [:bar :baz :quux ] inc)
  ;=> {:bar {:baz {:quux 124}}}







  ;end
,)

