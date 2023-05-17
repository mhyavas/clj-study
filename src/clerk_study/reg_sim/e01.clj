(ns clerk-study.reg-sim.e01)

;Tarih: 20230515

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


(def courses {101 {:id 101 :code "MAT101" :name "Matematik" :department [100 200]}
              201 {:id 201 :code "BIL101" :name "Matlab Giriş" :department [200]}})

(def departments {100 {:id 100 :title "Matematik"}
                  200 {:id 200 :title "Fizik"}
                  300 {:id 300 :title "Sosyoloji"}})
(def students {1 {:id 1 :name "Can Ali" :department/id [[:id 100 :title "Matematik"]]}
               2 {:id 2 :name "Ali Deniz" :department/id [[:id 200 :title "Fizik"] [:id 100 :title "Matematik"]]}
               3 {:id 3 :name "Mahmut Can" :department/id [[:id 300 :title "Sosyoloji"]]}})



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
    (map #(keyword (str "deparment/" %)) (map name (keys m)))
    #_(str "department/" (name (first xs)))
    )

  (map f3 (vals departments))
  ;=> ((:deparment/id :deparment/title)
  ; (:deparment/id :deparment/title)
  ; (:deparment/id :deparment/title))

  #_(str/join (cons "deparment/" (name (keys {:id 1 :title 2}))))

  #_(keyword (str/join "department" (walk/stringify-keys (keys (first (vals departments))))))


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
   {:db/ident :course/department
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/many}
   ])

(def schema-department
  [{:db/ident :department/id
    :db/valueType :db.type/long
    :db/unique :db.unique/identity
    :db/cardinality :db.cardinality/one}
   {:db/ident :department/title
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one}
   ])

(d/transact conn {:tx-data schema-department})
;=>
;{:db-before #datomic.core.db.Db{:id "b4215088-db3a-4479-9d58-8ac6d400ac4e",
;                                :basisT 5,
;                                :indexBasisT 0,
;                                :index-root-id nil,
;                                :asOfT nil,
;                                :sinceT nil,
;                                :raw nil},
; :db-after #datomic.core.db.Db{:id "b4215088-db3a-4479-9d58-8ac6d400ac4e",
;                               :basisT 6,
;                               :indexBasisT 0,
;                               :index-root-id nil,
;                               :asOfT nil,
;                               :sinceT nil,
;                               :raw nil},
; :tx-data [#datom[13194139533318 50 #inst"2023-05-16T09:54:41.756-00:00" 13194139533318 true]
;           #datom[73 10 :department/id 13194139533318 true]
;           #datom[73 40 22 13194139533318 true]
;           #datom[73 42 38 13194139533318 true]
;           #datom[73 41 35 13194139533318 true]
;           #datom[74 10 :department/title 13194139533318 true]
;           #datom[74 40 23 13194139533318 true]
;           #datom[74 41 35 13194139533318 true]
;           #datom[0 13 73 13194139533318 true]
;           #datom[0 13 74 13194139533318 true]],
; :tempids {}}

(defn f3 [m]
  (map #(keyword (str "department/" %)) (map name (keys m)))
  )

(map f3 (vals departments))
;=> ((:deparment/id :deparment/title) (:deparment/id :deparment/title) (:deparment/id :deparment/title))


(map vals (vals departments))
;=> ((100 "Matematik") (200 "Fizik") (300 "Sosyoloji"))


(map interleave (map f3 (vals departments)) (map vals (vals departments)))
;=>
;((:deparment/id 100 :deparment/title "Matematik")
; (:deparment/id 200 :deparment/title "Fizik")
; (:deparment/id 300 :deparment/title "Sosyoloji"))

(into [] (map interleave (map f3 (vals departments)) (map vals (vals departments))))
;=>
;[(:deparment/id 100 :deparment/title "Matematik")
; (:deparment/id 200 :deparment/title "Fizik")
; (:deparment/id 300 :deparment/title "Sosyoloji")]

(def department_data1 (into [] (map interleave (map f3 (vals departments)) (map vals (vals departments)))) )


(hash-map (:deparment/id 100 :deparment/title "Matematik"))

(into {} (first department_data1))
;Don't know how to create ISeq from: clojure.lang.Keyword

(defn f4 [m]
  (map #(str "department/" %) (map name (keys m)))
  )
(map f4 (vals departments))

(map interleave (map f4 (vals departments)) (map vals (vals departments)))
;=>
;(("deparment/id" 100 "deparment/title" "Matematik")
; ("deparment/id" 200 "deparment/title" "Fizik")
; ("deparment/id" 300 "deparment/title" "Sosyoloji"))

(def dept_data2 (into [] (map interleave (map f4 (vals departments)) (map vals (vals departments)))))
;=>
;[("deparment/id" 100 "deparment/title" "Matematik")
; ("deparment/id" 200 "deparment/title" "Fizik")
; ("deparment/id" 300 "deparment/title" "Sosyoloji")]
(into [] (apply hash-map (first department_data1)))
;=> [[:deparment/title "Matematik"] [:deparment/id 100]]

(def build-map (partial assoc {}))

(apply build-map (first department_data1))
;=> #:deparment{:id 100, :title "Matematik"}

(apply hash-map (first dept_data2))
;=> {"deparment/title" "Matematik", "deparment/id" 100}

(defn f5 [[k v]]
  (let [k1 (keyword k)
        v1 v]
    (into {} {k1 v1})))

(defn f7 [m]
  (into {} m))
(map f5 (apply hash-map (first dept_data2)))
;=> (#:deparment{:title "Matematik"} #:deparment{:id 100})

(f7 (map f5 (apply hash-map (first dept_data2))))
;=> #:department{:id 100, :title "Matematik"}
(map #(map f5 (apply hash-map %)) dept_data2)
;=>
;((#:deparment{:title "Matematik"} #:deparment{:id 100})
; (#:deparment{:title "Fizik"} #:deparment{:id 200})
; (#:deparment{:title "Sosyoloji"} #:deparment{:id 300}))

(map #(f7 (map f5 (apply hash-map %))) dept_data2)
;=>
;(#:department{:id 100, :title "Matematik"}
; #:department{:id 200, :title "Fizik"}
; #:department{:id 300, :title "Sosyoloji"})

(into [] (map #(f7 (map f5 (apply hash-map %))) dept_data2))
;=>
;[#:department{:id 100, :title "Matematik"}
; #:department{:id 200, :title "Fizik"}
; #:department{:id 300, :title "Sosyoloji"}]


(d/transact conn {:tx-data (map #(map f5 (apply hash-map %) ) dept_data2)})
;Execution error (ExceptionInfo) at datomic.core.error/raise (error.clj:55).
;:db.error/not-a-data-function Unable to resolve data function: #:department{:id 100}


(d/transact conn {:tx-data (into [] (map #(f7 (map f5 (apply hash-map %))) dept_data2))})
;=>
;{:db-before #datomic.core.db.Db{:id "b4215088-db3a-4479-9d58-8ac6d400ac4e",
;                                :basisT 7,
;                                :indexBasisT 0,
;                                :index-root-id nil,
;                                :asOfT nil,
;                                :sinceT nil,
;                                :raw nil},
; :db-after #datomic.core.db.Db{:id "b4215088-db3a-4479-9d58-8ac6d400ac4e",
;                               :basisT 8,
;                               :indexBasisT 0,
;                               :index-root-id nil,
;                               :asOfT nil,
;                               :sinceT nil,
;                               :raw nil},
; :tx-data [#datom[13194139533320 50 #inst"2023-05-16T12:20:57.780-00:00" 13194139533320 true]
;           #datom[92358976733259 73 100 13194139533320 true]
;           #datom[92358976733259 74 "Matematik" 13194139533320 true]
;           #datom[92358976733260 73 200 13194139533320 true]
;           #datom[92358976733260 74 "Fizik" 13194139533320 true]
;           #datom[92358976733261 73 300 13194139533320 true]
;           #datom[92358976733261 74 "Sosyoloji" 13194139533320 true]],
; :tempids {}}



(d/transact conn {:tx-data schema-course})
;=>
;{:db-before #datomic.core.db.Db{:id "b4215088-db3a-4479-9d58-8ac6d400ac4e",
;                                :basisT 8,
;                                :indexBasisT 0,
;                                :index-root-id nil,
;                                :asOfT nil,
;                                :sinceT nil,
;                                :raw nil},
; :db-after #datomic.core.db.Db{:id "b4215088-db3a-4479-9d58-8ac6d400ac4e",
;                               :basisT 9,
;                               :indexBasisT 0,
;                               :index-root-id nil,
;                               :asOfT nil,
;                               :sinceT nil,
;                               :raw nil},
; :tx-data [#datom[13194139533321 50 #inst"2023-05-16T12:23:13.915-00:00" 13194139533321 true]
;           #datom[75 10 :course/id 13194139533321 true]
;           #datom[75 40 22 13194139533321 true]
;           #datom[75 42 38 13194139533321 true]
;           #datom[75 41 35 13194139533321 true]
;           #datom[76 10 :course/code 13194139533321 true]
;           #datom[76 40 23 13194139533321 true]
;           #datom[76 42 38 13194139533321 true]
;           #datom[76 41 35 13194139533321 true]
;           #datom[77 10 :course/name 13194139533321 true]
;           #datom[77 40 23 13194139533321 true]
;           #datom[77 42 38 13194139533321 true]
;           #datom[77 41 35 13194139533321 true]
;           #datom[78 10 :course/department 13194139533321 true]
;           #datom[78 40 20 13194139533321 true]
;           #datom[78 41 36 13194139533321 true]
;           #datom[0 13 75 13194139533321 true]
;           #datom[0 13 76 13194139533321 true]
;           #datom[0 13 77 13194139533321 true]
;           #datom[0 13 78 13194139533321 true]],
; :tempids {}}

(defn f8 [m]
  (map #(str "course/" %) (map name (keys m)))
  )
(into [] (map interleave (map f8 (vals courses)) (map vals (vals courses))))
;=>
;[("course/id" 101 "course/code" "MAT101" "course/name" "Matematik" "course/department" [100 200])
; ("course/id" 201 "course/code" "BIL101" "course/name" "Matlab Giriş" "course/department" [200])]
(def course_data2 (into [] (map interleave (map f8 (vals courses)) (map vals (vals courses)))))
(into [] (map #(f7 (map f5 (apply hash-map %))) course_data2))
;=>
;[#:course{:department [100 200], :id 101, :name "Matematik", :code "MAT101"}
; #:course{:department [200], :id 201, :name "Matlab Giriş", :code "BIL101"}]


(d/transact conn {:tx-data (into [] (map #(f7 (map f5 (apply hash-map %))) course_data2))})
;=>
;{:db-before #datomic.core.db.Db{:id "b4215088-db3a-4479-9d58-8ac6d400ac4e",
;                                :basisT 10,
;                                :indexBasisT 0,
;                                :index-root-id nil,
;                                :asOfT nil,
;                                :sinceT nil,
;                                :raw nil},
; :db-after #datomic.core.db.Db{:id "b4215088-db3a-4479-9d58-8ac6d400ac4e",
;                               :basisT 11,
;                               :indexBasisT 0,
;                               :index-root-id nil,
;                               :asOfT nil,
;                               :sinceT nil,
;                               :raw nil},
; :tx-data [#datom[13194139533323 50 #inst"2023-05-17T08:02:49.447-00:00" 13194139533323 true]
;           #datom[92358976733263 78 100 13194139533323 true]
;           #datom[92358976733263 78 200 13194139533323 true]
;           #datom[92358976733263 75 101 13194139533323 true]
;           #datom[92358976733263 77 "Matematik" 13194139533323 true]
;           #datom[92358976733263 76 "MAT101" 13194139533323 true]
;           #datom[92358976733264 78 200 13194139533323 true]
;           #datom[92358976733264 75 201 13194139533323 true]
;           #datom[92358976733264 77 "Matlab Giriş" 13194139533323 true]
;           #datom[92358976733264 76 "BIL101" 13194139533323 true]],
; :tempids {}}




