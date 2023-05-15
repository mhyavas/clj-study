(ns clerk-study.to-datomic)

(require '[datomic.client.api :as d])

(def client (d/client {:server-type :dev-local
                       :storage-dir :mem
                       :system "ci"}))
(d/create-database client {:db-name "db4"})


(def conn (d/connect client {:db-name "db4"}))

(def courses {:id [201 301],
              :code ["MAT101" "BIL101"],
              :name ["Matematik" "Matlab Giriş"],
              :department/id [[{:id 101 :title "Matematik"} {:id 102 :title "Fizik"}] [{:id 102 :title "Fizik"}]]})
;Yukaridaki map stili ile datomice aktaramadim. Schemayi dogru kuramadigim icin-
;q2: Yukaridaki format ile dbye aktarimi nasil yapabiliriz

(def courses2 {201 {:id 201 :code "MAT101" :name "Matematik" :department/id [101 102]}
               301 {:id 301 :code "BIL101" :name "Matlab Giriş" :department/id [102]}})
(def dept {101 {:id 101 :title "Matematik"}
           102 {:id 102 :title "Fizik"}})

(def schema-dept
  [{:db/ident :department/id
    :db/valueType :db.type/long
    :db/unique :db.unique/identity
    }
                  ])
;q1: Burda departman ile course arasinda baglatiyi kuramadim. Keywordler karisiyor.

(def schema-course
  [{:db/ident :course/id
    :db/valueType :db.type/long
    :db/unique :db.unique/identity
    :db/cardinality :db.cardinality/one}
   {:db/ident :code
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one}
   {:db/ident :name
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one}
   {:db/ident :course/department
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/one}
   ])
(d/transact conn {:tx-data schema-course})

(d/transact conn {:tx-data (vals courses2)})



