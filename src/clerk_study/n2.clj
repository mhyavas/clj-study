^{:nextjournal.clerk/visibility {:code :hide}}

(ns clerk-study.n2
  (:require
    [nextjournal.clerk :as clerk ]))

(clerk/table
  (clerk/use-headers
    [["col1" "col2"]
     [1 2]
     [3 5]]))
