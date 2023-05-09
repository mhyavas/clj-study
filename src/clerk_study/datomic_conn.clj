(ns clerk-study.datomic-conn)

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

