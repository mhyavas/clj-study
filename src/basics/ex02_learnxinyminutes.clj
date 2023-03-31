(ns basics.ex02-learnxinyminutes)

(class 1)
;=> java.lang.Long
(class 1.)
;=> java.lang.Double

;if you want to create a literal list of data:
'(+ 1 2)
;=> (+ 1 2)
;Shorthand for (quote (+1 2))

(def eval_fn '(+ 1 2))
(println eval_fn)                                           ;=>(+ 1 2)
(eval eval_fn)                                              ;=> 3

(class [1 2 3])                                             ;=> clojure.lang.PersistentVector
(class '(1 2 3))                                            ;=> clojure.lang.PersistentList
; A list would be written as just (1 2 3), but we have to quote
; it to stop the reader thinking it's a function.
; Also, (list 1 2 3) is the same as '(1 2 3)

(coll? '(1 2 3))                                            ;=> true
(coll? [1 2 3])                                             ;=> true

(seq? '(1 2 3))                                             ;=> true
(seq? [1 2 3])                                              ;=> false
;Only lists are seqs

(range 4)                                                   ;=> (0 1 2 3)
(take 4 (range))                                            ;=> (0 1 2 3)

(filter even? [2 3 4 6])                                    ;=> (2 4 6)

; The [] is the list of arguments for the function.
(defn hello [name]
  (str "Hello " name))
(hello "Steve") ; => "Hello Steve"

; You can also use this shorthand to create functions:
(def hello2 #(str "Hello " %1))
(hello2 "Julie") ; => "Hello Julie"

; You can have multi-variadic functions, too
(defn hello3
  ([] "Hello World")
  ([name] (str "Hello " name)))
(hello3 "Jake") ; => "Hello Jake"
(hello3) ; => "Hello World"

; Functions can pack extra arguments up in a seq for you
(defn count-args [& args]
  (str "You passed " (count args) " args: " args))
(count-args 1 2 3) ; => "You passed 3 args: (1 2 3)"

; Test for existence by using the set as a function:
(#{1 2 3} 6)
;if the value is in the sets, the value will be returned. If not, nil will be returned

; The double arrow does the same thing, but inserts the result of
; each line at the *end* of the form. This is useful for collection
; operations in particular:
(->>
  (range 10)
  (map inc)     ;=> (map inc (range 10)
  (filter odd?) ;=> (filter odd? (map inc (range 10))
  (into []))    ;=> (into [] (filter odd? (map inc (range 10)))
; Result: [1 3 5 7 9]

(into [] (filter odd? (map inc (range 10))))
;=> [1 3 5 7 9]

(as-> [1 2 3] input
      (map inc input);=> You can use last transform's output at the last position
      (nth input 2) ;=>  and at the second position, in the same expression
      (conj [4 5 6] input 8 9 10)) ;=> or in the middle !
; Result: [4 5 6 4 8 9 10]

;MODULES
(use 'clojure.set)
(intersection #{1 2 3} #{2 3 4})
(difference #{1 2 3} #{2 3 4})

(use '[clojure.set :only [intersection]])
;we can get a subset of functions from the whole module

;Module importing
(require 'clojure.string)
(require '[clojure.string :as str])

(str/replace "This is a test." #"[a-o]" str/upper-case)
;=> "THIs Is A tEst."
;#[a-o] means that upper-case functions will be applied to letter between A and O

(ns basics.ex02-learnxinyminutes
  (:require
    [clojure.string :as str]
    [clojure.set :as set]))

;Java modules can be imported
(import java.util.Date)

(ns basics.ex02-learnxinyminutes
  (:import java.util.Date
           java.util.Calendar))


(Date.)                                                     ;=> #inst"2023-03-31T12:49:17.993-00:00"
(. (Date.) getTime)                                         ;=> 1680266994656
(.getTime (Date.))                                          ;=> 1680267029538
(doto (Calendar/getInstance)
  (.set 2000 0 1 0 0 0).getTime)                            ;=> #inst"2000-01-01T00:00:00.104-05:00"

;Software Transactional Memory
(def atom1 (atom {}))
(swap! atom1 assoc :a 1)
(swap! atom1 assoc :b 2)
(println @atom1)                                            ;=>{:a 1, :b 2}
