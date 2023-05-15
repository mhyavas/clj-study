(ns clerk-study.datomic-conn
  (:require [nextjournal.clerk :as clerk]))

(require '[datomic.client.api :as d])

(def client (d/client {:server-type :dev-local
                       :storage-dir :mem
                       :system "ci"}))

(def conn (d/connect client {:db-name "db2"}))

;Departments Schema
(def schema-1
  [{:db/ident :id
    :db/valueType :db.type/long
    :db/unique :db.unique/identity
    :db/cardinality :db.cardinality/one}
   {:db/ident :title
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one}
   ])

(d/transact conn {:tx-data schema-1})

(def db
  {:department {101 {:id 101 :title "Matematik"}
                102 {:id 102 :title "Fizik"}
                103 {:id 103 :title "Sosyoloji"}}
   :student {}})

(vals (db :department))
;=> ({:id 101, :title "Matematik"} {:id 102, :title "Fizik"} {:id 103, :title "Sosyoloji"})

(d/transact conn {:tx-data (vals (db :department))})

(def datodb (d/db conn))
(d/q
  '[:find ?e
    :where
    [?e :title "Sosyoloji"]
    ]
  datodb)
;=> [[87960930222159]]

(def deparment_id (ffirst (d/q
                            '[:find ?e
                              :where
                              [?e :title "Sosyoloji"]
                              ]
                            datodb)))

(identity deparment_id)
;=> 87960930222159

#_(d/q
  '[:find (pull deparment_id [*])
    :where
    [?e :title "Sosyoloji"]
    [?e :id 103]
    ]
  datodb)


(d/q
  '[:find ?e
    :where
    [_ :title ?e]
    ]
  datodb)
;=> [["Fizik"] ["Sosyoloji"] ["Matematik"]]

(d/q
  '[:find ?e
    :where
    [_ :id ?e]] datodb)
;=> [[101] [102] [103]]

(d/q
  '[:find ?a
    :where
    [?e :title "Matematik"]
    [?e ?a _]] datodb)
;=> [[73] [76]]

(d/q
  '[:find ?aname
    :where
    [?e :title "Matematik"]
    [?e ?a _]
    [?a :db/ident ?aname]] datodb)
;=> [[:title] [:id]]

(defn q [k db]
  (d/q
    '[:find ?e
      :where
      [_ k ?e]] db)
  )

#_(q :id datodb)
;q1: Yukarıdaki fonksiyonda simulasyon için genel bir fonksiyon tanımlamak istedim ama query fonksiyonu 'k' inputunu kabul etmiyor.
{::clerk/visibility {:code :hide}}
(comment
  ;; multi-arity version
  (d/q  '[:find ?name ?duration
          :where [?e :artist/name "The Beatles"]
          [?track :track/artists ?e]
          [?track :track/name ?name]
          [?track :track/duration ?duration]]
        db)
  ;rfr: https://docs.datomic.com/cloud/query/query-data-reference.html
  )

(def t1 {:id (d/q
               '[:find ?e
                 :where
                 [_ :id ?e]] datodb)
         :title (d/q
                  '[:find ?e
                    :where
                    [_ :title ?e]] datodb)})

{::clerk/visibility {:code :hide}}
(comment

  (identity t1) ;=> {:id [[101] [102] [103]], :title [["Fizik"] ["Sosyoloji"] ["Matematik"]]}


  (defn f1 [v]
    (apply concat v))

  {::clerk/visibility {:code :hide}}
  (f1 (t1 :id)) ;=> (101 102 103)


  (defn f2 [v]
    (into [] (apply concat v)))

  (f2 (t1 :id)) ;=> [101 102 103]

  ;end
,)

{::clerk/visibility {:code :hide}}
(defn f2 [v]
  (into [] (apply concat v)))
(def t2 {:id (f2 (t1 :id)) :title (f2 (t1 :title))})

{::clerk/visibility {:code :hide}}
(identity t2) ;=> {:id [101 102 103], :title ["Fizik" "Sosyoloji" "Matematik"]}
(clerk/table t2)