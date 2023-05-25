(ns clerk-study.reg-sim.e05
  (:require [nextjournal.clerk :as clerk]))


(require '[datomic.client.api :as d])

(def client (d/client {:server-type :dev-local
                       :storage-dir :mem
                       :system "ci"}))


(def conn (d/connect client {:db-name "db6"}))

#_(def form1 {:id 1 :name "Can Ali" :department/id 101})
#_(def form2 {:id 2 :name "Ali Deniz" :department/id 102})

(def student-schema
  [{:db/ident :student/id
    :db/valueType :db.type/long
    :db/unique :db.unique/identity
    :db/cardinality :db.cardinality/one}
   {:db/ident :student/name
    :db/valueType :db.type/string
    :db/unique :db.unique/identity
    :db/cardinality :db.cardinality/one}
   {:db/ident :student/department
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/one}])


(def student-list
  [{:student/id 1
    :student/name "Can Ali"
    :student/department :department/matematik}
   {:student/id 2
    :student/name "Ali Deniz"
    :student/department :department/fizik}
   ])

(d/transact conn {:tx-data student-schema})
;=>
;{:db-before #datomic.core.db.Db{:id "90b040e6-df3c-4ce1-a8ee-bac2e31551b3",
;                                :basisT 9,
;                                :indexBasisT 0,
;                                :index-root-id nil,
;                                :asOfT nil,
;                                :sinceT nil,
;                                :raw nil},
; :db-after #datomic.core.db.Db{:id "90b040e6-df3c-4ce1-a8ee-bac2e31551b3",
;                               :basisT 10,
;                               :indexBasisT 0,
;                               :index-root-id nil,
;                               :asOfT nil,
;                               :sinceT nil,
;                               :raw nil},
; :tx-data [#datom[13194139533322 50 #inst"2023-05-25T09:17:46.194-00:00" 13194139533322 true]
;           #datom[77 10 :student/id 13194139533322 true]
;           #datom[77 40 22 13194139533322 true]
;           #datom[77 42 38 13194139533322 true]
;           #datom[77 41 35 13194139533322 true]
;           #datom[78 10 :student/name 13194139533322 true]
;           #datom[78 40 23 13194139533322 true]
;           #datom[78 42 38 13194139533322 true]
;           #datom[78 41 35 13194139533322 true]
;           #datom[79 10 :student/department 13194139533322 true]
;           #datom[79 40 20 13194139533322 true]
;           #datom[79 41 35 13194139533322 true]
;           #datom[0 13 77 13194139533322 true]
;           #datom[0 13 78 13194139533322 true]
;           #datom[0 13 79 13194139533322 true]],
; :tempids {}}

(d/transact conn {:tx-data student-list})
;=>
;{:db-before #datomic.core.db.Db{:id "90b040e6-df3c-4ce1-a8ee-bac2e31551b3",
;                                :basisT 10,
;                                :indexBasisT 0,
;                                :index-root-id nil,
;                                :asOfT nil,
;                                :sinceT nil,
;                                :raw nil},
; :db-after #datomic.core.db.Db{:id "90b040e6-df3c-4ce1-a8ee-bac2e31551b3",
;                               :basisT 11,
;                               :indexBasisT 0,
;                               :index-root-id nil,
;                               :asOfT nil,
;                               :sinceT nil,
;                               :raw nil},
; :tx-data [#datom[13194139533323 50 #inst"2023-05-25T09:18:13.503-00:00" 13194139533323 true]
;           #datom[83562883711056 77 1 13194139533323 true]
;           #datom[83562883711056 78 "Can Ali" 13194139533323 true]
;           #datom[83562883711056 79 96757023244361 13194139533323 true]
;           #datom[83562883711057 77 2 13194139533323 true]
;           #datom[83562883711057 78 "Ali Deniz" 13194139533323 true]
;           #datom[83562883711057 79 96757023244362 13194139533323 true]],
; :tempids {}}

(def datodb (d/db conn))

(d/q '[:find (pull ?e [*])
       :where
       [?e :student/id _]
       [?e :student/department :department/fizik]]
     datodb)
;=>
;[[{:db/id 83562883711057,
;   :student/id 2,
;   :student/name "Ali Deniz",
;   :student/department #:db{:id 96757023244362, :ident :department/fizik}}]]

(def student-math (d/q '[:find (pull ?e [*])
                         :where
                         [?e :student/id _]
                         [?e :student/department :department/fizik]]
                       datodb))

(ffirst (identity student-math))
;=>
;{:db/id 83562883711056,
; :student/id 1,
; :student/name "Can Ali",
; :student/department #:db{:id 96757023244361, :ident :department/matematik}}
(get-in (ffirst student-math) [:student/department :db/ident] )
;=> :department/matematik

(def filtered-students {:id [] :name [] :department []})

(clerk/table filtered-students)

(def student-list2
  [{:student/id 3
    :student/name "Mahmut Can"
    :student/department :department/matematik}
   ])
(d/transact conn {:tx-data student-list2})
;=>
;{:db-before #datomic.core.db.Db{:id "90b040e6-df3c-4ce1-a8ee-bac2e31551b3",
;                                :basisT 22,
;                                :indexBasisT 0,
;                                :index-root-id nil,
;                                :asOfT nil,
;                                :sinceT nil,
;                                :raw nil},
; :db-after #datomic.core.db.Db{:id "90b040e6-df3c-4ce1-a8ee-bac2e31551b3",
;                               :basisT 23,
;                               :indexBasisT 0,
;                               :index-root-id nil,
;                               :asOfT nil,
;                               :sinceT nil,
;                               :raw nil},
; :tx-data [#datom[13194139533335 50 #inst"2023-05-25T09:47:58.431-00:00" 13194139533335 true]
;           #datom[83562883711058 77 3 13194139533335 true]
;           #datom[83562883711058 78 "Mahmut Can" 13194139533335 true]
;           #datom[83562883711058 79 96757023244361 13194139533335 true]],
; :tempids {}}
(def student-math2 (d/q '[:find (pull ?e [*])
                         :where
                         [?e :student/id _]
                         [?e :student/department :department/matematik]]
                       datodb))

(identity student-math2)
;=>
;[[{:db/id 83562883711056,
;   :student/id 1,
;   :student/name "Can Ali",
;   :student/department #:db{:id 96757023244361, :ident :department/matematik}}]
; [{:db/id 83562883711058,
;   :student/id 3,
;   :student/name "Mahmut Can",
;   :student/department #:db{:id 96757023244361, :ident :department/matematik}}]]
(d/q '[:find (pull ?e [*])
       :where [?e :student/name "Mahmut Can"]] datodb)
;=>
;[[{:db/id 83562883711058,
;   :student/id 3,
;   :student/name "Mahmut Can",
;   :student/department #:db{:id 96757023244361, :ident :department/matematik}}]]


(comment
  (apply concat student-math2)
  ;=>
  ;({:db/id 83562883711056,
  ;  :student/id 1,
  ;  :student/name "Can Ali",
  ;  :student/department #:db{:id 96757023244361, :ident :department/matematik}}
  ; {:db/id 83562883711058,
  ;  :student/id 3,
  ;  :student/name "Mahmut Can",
  ;  :student/department #:db{:id 96757023244361, :ident :department/matematik}})

  (map #(assoc-in filtered-students [:id] (:student/id %)) (apply concat student-math2))
  ;=> ({:id 1, :name [], :department []} {:id 3, :name [], :department []})



  ;end
  ,)
