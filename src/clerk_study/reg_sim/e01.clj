(ns clerk-study.reg-sim.e01)

(require '[clojure.string :as str])
(require '[clojure.walk :as walk])
(require '[datomic.client.api :as d])
;Student registration system data template

;; Memory storage
(def client (d/client {:server-type :dev-local
                       :storage-dir :mem
                       :system "ci"}))

(d/create-database client {:db-name "db5"})

(def conn (d/connect client {:db-name "db5"}))


(def courses {101 {:id 101 :code "MAT101" :name "Matematik" :department/id [100 200]}
              201 {:id 201 :code "BIL101" :name "Matlab Giriş" :department/id [200]}})

(def departments {100 {:id 100 :title "Matematik"}
                  200 {:id 200 :title "Fizik"}
                  300 {:id 300 :title "Sosyoloji"}})
(def students {1 {:id 1 :name "Can Ali" :department/id [[:id 100 :title "Matematik"]]}
               2 {:id 2 :name "Ali Deniz" :department/id [[:id 200 :title "Fizik"] [:id 100 :title "Matematik"]]}
               3 {:id 3 :name "Mahmut Can" :department/id [[:id 300 :title "Sosyoloji"]]}})


(def schema-department
  [{:db/ident :id
    :db/valueType :db.type/long
    :db/unique :db.unique/identity
    :db/cardinality :db.cardinality/one}
   {:db/ident :title
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one}
   ])

(comment
  ;Departments mapini datomic'e aktarmak icin obje tanimlamasi:
  (identity (vals departments))
  ;=> ({:id 100, :title "Matematik"} {:id 200, :title "Fizik"} {:id 300, :title "Sosyoloji"})
  (map keys (vals departments))
  ;=> ((:id :title) (:id :title) (:id :title))
  (defn f1 [m]
    (identity (keys m))
    (identity (vals m)) )

  (map f1 (vals departments))
  ;=> ((100 "Matematik") (200 "Fizik") (300 "Sosyoloji"))

  (defn f2 [m]
    (let [k (keys m)
          v (vals m)]
      {(keyword (str/join "department" (walk/stringify-keys k))) v}))

  (map f2 (vals departments))
  ;=>
  ;({#viewer-eval(keyword ":iddepartment:title") (100 "Matematik")}
  ; {#viewer-eval(keyword ":iddepartment:title") (200 "Fizik")}
  ; {#viewer-eval(keyword ":iddepartment:title") (300 "Sosyoloji")})
  (str/join "deparment" (keys {:id 1 :title 2}))
  ;=> ":iddeparment:title"
  (cons "deparment/" (keys {:id 1 :title 2}))
  ;=> ("deparment/" :id :title)
  (str "deparment/" (name (first (keys {:id 1 :title 2}))))
  ;=> "deparment/id"

  (defn f3 [m]
    (map #(str "deparment/" %) (map name (keys m)))
    #_(str "department/" (name (first xs)))
    )
  (map f3 (vals departments))
  ;=> (("deparment/id" "deparment/title")
  ; ("deparment/id" "deparment/title")
  ; ("deparment/id" "deparment/title"))

  (str/join (cons "deparment/" (name (keys {:id 1 :title 2}))))

  (keyword (str/join "department" (walk/stringify-keys (keys (first (vals departments))))))


  ;end
        )
(def schema-course
  [{:db/ident :course/id
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
   {:db/ident :department/id
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/many}
   ])