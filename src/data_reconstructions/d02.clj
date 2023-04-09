(ns data-reconstructions.d02
  (:require [clojure.string :as string]))

(comment
  (def x [{:name "ali" :surname "veli"}
          {:name "batu" :surname "can"}])

  (def x1 {:name "ali" :surname "veli"})

  (vals x1)
  ;=> ("ali" "veli")
  (require '[clojure.string :as string])

  (string/join (vals x1))
  ;=> "aliveli"

  (defn joiner [s]
    (string/join (vals s))
    )
  (into [] (map joiner x))
  ;=> ["aliveli" "batucan"]
  ;end
  ,)
