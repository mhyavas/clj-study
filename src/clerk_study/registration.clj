
(ns clerk-study.registration
  (:require [nextjournal.clerk :as clerk]))
(require '[clojure.walk :refer [postwalk]])
(require '[datomic.client.api :as d])
;Student registration system data template

;; Memory storage
(def client (d/client {:server-type :dev-local
                       :storage-dir :mem
                       :system "ci"}))

(d/create-database client {:db-name "db2"})

(def conn (d/connect client {:db-name "db2"}))
^{::clerk/visibility {:code :hide}}
(def schema-1
  [{:db/ident :id
    :db/valueType :db.type/long
    :db/unique :db.unique/identity
    :db/cardinality :db.cardinality/one}
   {:db/ident :title
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one}
   {:db/ident :students
    :db/valueType :db.type/tuple
    :db/cardinality :db.cardinality/one}
   ])
; [rfr](https://docs.datomic.com/pro/schema/schema.html)
^{::clerk/visibility {:code :hide}}
(def schema-2
  [{ :db/ident :id
   :db/valueType :db.type/long
   :db/unique :db.unique/identity
   :db/cardinality :db.cardinality/one}
   {:db/ident :name
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one}
   {:db/ident :department/id
    :db/valueType :db.type/tuple
    :db/tupleAttrs  []
    :db/cardinality :db.cardinality/one}
   ])
(def db
  {:department {101 {:id 101 :title "Matematik" :students []}
                102 {:id 102 :title "Fizik" :students []}
                103 {:id 103 :title "Sosyoloji" :students []}}
   :student {}})

;String doesn't have good look at the clerk table. We need the data in a vector.
(defn to-clerk [m]
  (postwalk
    #(if
       (string? %)
       (vector %)
       %)
    m))
^{::clerk/visibility {:code :hide}}
(def st {:id [] :name [] :department/id []})
; ### Student record table
^{::clerk/visibility {:code :hide}}
(clerk/table st)
; ### Student registration form
(def form1 {:id 1 :name "Can Ali" :department/id 101})
(def form2 {:id 2 :name "Ali Deniz" :department/id 102})


(def record1 (-> st
     (update-in [:id] conj (form1 :id))
     (update-in [:name] conj (form1 :name))
     (update-in [:department/id] conj (form1 :department/id))))
;=> {:id [1], :name ["Can Ali"], :department/id [101]}
(clerk/table record1)
#_(let [id (form1 :id)
      name (form1 :name)
      did (form1 :department/id)] (assoc-in st :id id :name name :department/id did))
(def record2 (-> record1
     (update-in [:id] conj (form2 :id))
     (update-in [:name] conj (form2 :name))
     (update-in [:department/id] conj (form2 :department/id))))

(clerk/table record2)
^{::clerk/visibility {:code :hide}}
(to-clerk (vals (db :department)))



; #Deparments
^{::clerk/visibility {:code :hide}}
(clerk/table (to-clerk (vals (db :department))))


(def db2 (-> db
     (assoc-in [:student] record2)
     ))
;{:department {101 {:id 101, :title "Matematik", :students []},
;              102 {:id 102, :title "Fizik", :students []},
;              103 {:id 103, :title "Sosyoloji", :students []}},
; :student {:id [1 2], :name ["Can Ali" "Ali Deniz"], :department/id [101 102]}}

(clerk/table (db2 :student))

#_(d/transact conn {:tx-data schema-2})
;:db.error/invalid-install-attribute First error: :db.error/invalid-tuple-kind
#_(d/transact conn {:tx-data schema-2})
;:db.error/invalid-tuple-value Invalid tuple value

;İlk hata     :db/tupleAttrs  [:department/id] satırını ekledikten sonra ikinci hataya dönüştü. Satırı aşağıdaki linkten dolayı ekledim

;q1: [soru linki](https://forum.datomic.com/t/troubles-with-upsert-on-composite-tuples/1355/6)


(get-in db2 [:student :id])