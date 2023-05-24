(ns clerk-study.reg-sim.e03)

;Yeni datomic schemasi ile dbnin yeniden kurulmasi


(require '[datomic.client.api :as d])

(def client (d/client {:server-type :dev-local
                       :storage-dir :mem
                       :system "ci"}))
(d/create-database client {:db-name "db6"})

(def conn (d/connect client {:db-name "db6"}))

(d/transact
  conn
  {:tx-data [{:db/ident :department/matematik}
             {:db/ident :department/fizik}
             {:db/ident :department/sosyoloji}]})

(def course-schema [{:db/ident :course/id
                     :db/valueType :db.type/long
                     :db/unique :db.unique/identity
                     :db/cardinality :db.cardinality/one}
                    {:db/ident :course/code
                     :db/valueType :db.type/string
                     :db/unique :db.unique/identity
                     :db/cardinality :db.cardinality/one}
                    {:db/ident :course/name
                     :db/valueType :db.type/string
                     :db/unique :db.unique/identity
                     :db/cardinality :db.cardinality/one}
                    {:db/ident :course/department
                     :db/valueType :db.type/ref
                     :db/cardinality :db.cardinality/many}
                    ])
(d/transact conn {:tx-data course-schema})
;=>
;{:db-before #datomic.core.db.Db{:id "90b040e6-df3c-4ce1-a8ee-bac2e31551b3",
;                                :basisT 7,
;                                :indexBasisT 0,
;                                :index-root-id nil,
;                                :asOfT nil,
;                                :sinceT nil,
;                                :raw nil},
; :db-after #datomic.core.db.Db{:id "90b040e6-df3c-4ce1-a8ee-bac2e31551b3",
;                               :basisT 8,
;                               :indexBasisT 0,
;                               :index-root-id nil,
;                               :asOfT nil,
;                               :sinceT nil,
;                               :raw nil},
; :tx-data [#datom[13194139533320 50 #inst"2023-05-24T10:49:27.359-00:00" 13194139533320 true]
;           #datom[73 10 :course/id 13194139533320 true]
;           #datom[73 40 22 13194139533320 true]
;           #datom[73 42 38 13194139533320 true]
;           #datom[73 41 35 13194139533320 true]
;           #datom[74 10 :course/code 13194139533320 true]
;           #datom[74 40 23 13194139533320 true]
;           #datom[74 42 38 13194139533320 true]
;           #datom[74 41 35 13194139533320 true]
;           #datom[75 10 :course/name 13194139533320 true]
;           #datom[75 40 23 13194139533320 true]
;           #datom[75 42 38 13194139533320 true]
;           #datom[75 41 35 13194139533320 true]
;           #datom[76 10 :course/department 13194139533320 true]
;           #datom[76 40 20 13194139533320 true]
;           #datom[76 41 36 13194139533320 true]
;           #datom[0 13 73 13194139533320 true]
;           #datom[0 13 74 13194139533320 true]
;           #datom[0 13 75 13194139533320 true]
;           #datom[0 13 76 13194139533320 true]],
; :tempids {}}

(def course-list
  [{:course/id 201
    :course/code "MAT101"
    :course/name "Calculus 1"
    :course/department :department/matematik}
   {:course/id 301
    :course/code "BIL101"
    :course/name "MATLAB Giriş"
    :course/department :department/matematik}
   {:course/id 301
    :course/code "BIL101"
    :course/name "MATLAB Giriş"
    :course/department :department/fizik}
   ])
(d/transact conn {:tx-data course-list})
;=>
;{:db-before #datomic.core.db.Db{:id "90b040e6-df3c-4ce1-a8ee-bac2e31551b3",
;                                :basisT 8,
;                                :indexBasisT 0,
;                                :index-root-id nil,
;                                :asOfT nil,
;                                :sinceT nil,
;                                :raw nil},
; :db-after #datomic.core.db.Db{:id "90b040e6-df3c-4ce1-a8ee-bac2e31551b3",
;                               :basisT 9,
;                               :indexBasisT 0,
;                               :index-root-id nil,
;                               :asOfT nil,
;                               :sinceT nil,
;                               :raw nil},
; :tx-data [#datom[13194139533321 50 #inst"2023-05-24T10:53:45.820-00:00" 13194139533321 true]
;           #datom[74766790688845 73 201 13194139533321 true]
;           #datom[74766790688845 74 "MAT101" 13194139533321 true]
;           #datom[74766790688845 75 "Calculus 1" 13194139533321 true]
;           #datom[74766790688845 76 96757023244361 13194139533321 true]
;           #datom[74766790688846 73 301 13194139533321 true]
;           #datom[74766790688846 74 "BIL101" 13194139533321 true]
;           #datom[74766790688846 75 "MATLAB Giriş" 13194139533321 true]
;           #datom[74766790688846 76 96757023244361 13194139533321 true]
;           #datom[74766790688846 76 96757023244362 13194139533321 true]],
; :tempids {}}


