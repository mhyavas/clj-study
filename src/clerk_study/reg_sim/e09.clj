(ns clerk-study.reg-sim.e09
  (:require [nextjournal.clerk :as clerk]))
;Tarih 20230529

;Datomic'den cekilen datalarin clerk uzerinden gosterilmesi

^{::clerk/visibility {:code :hide}}
(require '[datomic.client.api :as d])

^{::clerk/visibility {:code :hide}}
(def client (d/client {:server-type :dev-local
                       :storage-dir :mem
                       :system "ci"}))
^{::clerk/visibility {:code :hide}}
(def conn (d/connect client {:db-name "db6"}))

^{::clerk/visibility {:code :hide}}
(def datodb (d/db conn))

(def student-math2 (d/q '[:find (pull ?e [*])
                          :where
                          [?e :student/id _]
                          [?e :student/department :department/matematik]]
                        datodb))

^{::clerk/visibility {:code :hide}}
(comment
  (-> student-math2
      (flatten))
  ;=>
  ;({:db/id 83562883711056,
  ;  :student/id 1,
  ;  :student/name "Can Ali",
  ;  :student/department #:db{:id 96757023244361, :ident :department/matematik}}
  ; {:db/id 83562883711058,
  ;  :student/id 3,
  ;  :student/name "Mahmut Can",
  ;  :student/department #:db{:id 96757023244361, :ident :department/matematik}})

  (first (flatten student-math2))
  (defn f1 [m]
    (let [id (get-in m [:student/id])
          name (get-in m [:student/name])
          department (get-in m [:student/department])]
       {:id id :name name :department department}))

  (f1 (first (flatten student-math2)))
  ;=> {:id 1, :name "Can Ali", :department #:db{:id 96757023244361, :ident :department/matematik}}
  (map f1 (flatten student-math2))
  ;=>
  ;({:id 1, :name "Can Ali", :department #:db{:id 96757023244361, :ident :department/matematik}}
  ; {:id 3, :name "Mahmut Can", :department #:db{:id 96757023244361, :ident :department/matematik}})
  (def students {:id [] :name [] :departments []})
  #_(map #(assoc-in filtered-students [:id] (:student/id %)) (apply concat student-math2))
  (identity (first (map f1 (flatten student-math2))) )
  ;=> {:id 1, :name "Can Ali", :department #:db{:id 96757023244361, :ident :department/matematik}}
  (map #(assoc-in students [:id] (:student/id %)) (apply concat student-math2))
  ;=> ({:id 1, :name [], :departments []} {:id 3, :name [], :departments []})
  (map #(assoc-in students [:name] (:student/name %)) (apply concat student-math2))
  ;=> ({:id [], :name "Can Ali", :departments []} {:id [], :name "Mahmut Can", :departments []})
  (merge-with into (map #(assoc-in students [:name] (:student/name %)) (apply concat student-math2)))
  ;=> ({:id [], :name "Can Ali", :departments []} {:id [], :name "Mahmut Can", :departments []})

  (defn f2 [m]
    (let [id (get-in m [:student/id])
          name (get-in m [:student/name])
          department (get-in m [:student/department])]
      {:id [id] :name [name] :department [department]}))
  (map f2 (flatten student-math2))
  ;=>
  ;({:id [1], :name ["Can Ali"], :department [#:db{:id 96757023244361, :ident :department/matematik}]}
  ; {:id [3], :name ["Mahmut Can"], :department [#:db{:id 96757023244361, :ident :department/matematik}]})

  (map #(assoc-in students [:name] (:student/name %)) (map f2 (flatten student-math2)))
  ;=> ({:id [], :name nil, :departments []} {:id [], :name nil, :departments []})

  (first (map f2 (flatten student-math2)))
  (use 'clojure.walk)
  (clojure.walk/stringify-keys (map f2 (flatten student-math2)))
  ;=>
  ;({"id" [1], "name" ["Can Ali"], "department" [{"id" 96757023244361, "ident" :department/matematik}]}
  ; {"id" [3], "name" ["Mahmut Can"], "department" [{"id" 96757023244361, "ident" :department/matematik}]})
  (defn convert-input [input]
    (let [ids (mapv #(get % :id) input)
          names (mapv #(get % :name) input)
          departments (mapv #(get % :department) input)]
      {:id ids
       :name names
       :department departments}))
  (convert-input (map f1 (flatten student-math2)) )
  ;=>
  ;{:id [1 3],
  ; :name ["Can Ali" "Mahmut Can"],
  ; :department [#:db{:id 96757023244361, :ident :department/matematik}
  ;              #:db{:id 96757023244361, :ident :department/matematik}]}

  ;Convert-input fonksiyonunu chat-gpt ile olusturdum. Asagida fonksiyonu debug edecegim.

  (mapv #(get % :id) (map f1 (flatten student-math2)))
  ;=> [1 3]

  (get (first (map f1 (flatten student-math2))) :id)
  ;=> 1

  ;Department listesinde kullanici icin fazla bilgi mevcut bundan dolayi datayi sadelestiricem.
  (def departments (apply concat (mapv #(get % :department) (map f1 (flatten student-math2)))))
  (identity departments)
  ;=> ([:db/id 96757023244361] [:db/ident :department/matematik] [:db/id 96757023244361] [:db/ident :department/matematik])


  (defn convert-input [input]
    (let [ids (mapv #(get % :id) input)
          names (mapv #(get % :name) input)
          departments (mapv #(get-in % [:department]) input)
          idents (mapv #(get-in % [:db/ident]) departments)]
      {:id ids
       :name names
       :department idents
       }))

  (convert-input (map f1 (flatten student-math2)))



  ;end
  )
; ## Matematik Departmanındaki öğrencilerin listesi
(defn f1 [m]
  (let [id (get-in m [:student/id])
        name (get-in m [:student/name])
        department (get-in m [:student/department])]
    {:id id :name name :department department}))

(defn f2 [ms]
  (let [ids (mapv #(get % :id) ms)
        names (mapv #(get % :name) ms)
        departments (mapv #(get-in % [:department]) ms)
        idents (mapv #(get-in % [:db/ident]) departments)]
    {:id ids
     :name names
     :department idents
     })
  )

(f2 (map f1 (flatten student-math2)))
; => {:id [1 3], :name ["Can Ali" "Mahmut Can"], :department [:department/matematik :department/matematik]}

(clerk/table (f2 (map f1 (flatten student-math2))))

; ### Parametrik sorgu ile,

(defn dept-search [datodb deps]
  (d/q '[:find (pull ?e [*])
                         :in $ ?deps
                        :where
                        [?e :student/department ?deps]]
                        datodb deps)
  )

(def dpt (dept-search datodb :department/matematik))

(f2 (map f1 (flatten dpt)))
;=> {:id [1 3], :name ["Can Ali" "Mahmut Can"], :department [:department/matematik :department/matematik]}
(clerk/table (f2 (map f1 (flatten dpt))))


; ## Matematik Departmanindaki Derslerin Gosterilmesi

(defn course-search [datodb deps]
  (d/q '[:find (pull ?e [*])
         :in $ ?deps
         :where
         [?e :course/department ?deps]]
       datodb deps)
  )
(course-search datodb :department/matematik)
(def dpt-courses (course-search datodb :department/matematik))

(defn f3 [m]
  (let [id (get-in m [:course/id])
        code (get-in m [:course/code])
        name (get-in m [:course/name])
        department (get-in m [:course/department])]
    {:id id :code code :name name :department department}))

(defn f4 [ms]
  (let [ids (mapv #(get % :id) ms)
        codes (mapv #(get % :code) ms)
        names (mapv #(get % :name) ms)
        departments (mapv #(get-in % [:department]) ms)
        idents (mapv #(map (get-in % [:db]) ) departments)]
    {:id ids
     :code codes
     :name names
     :department idents
     })
  )
(f4 (map f3 (flatten dpt-courses)))
;=> {:id [201 301], :code ["MAT101" "BIL101"], :name ["Calculus 1" "MATLAB Giriş"], :department [nil nil]}

(comment
  (def test-dpt [[#:db{:id 96757023244361, :ident :department/matematik}]
                 [#:db{:id 96757023244361, :ident :department/matematik}
                  #:db{:id 96757023244362, :ident :department/fizik}]])

  (defn f5 [v]
    (mapv #(get-in % [:db/ident]) v))

  (map f5 test-dpt)
  ;=> ([:department/matematik] [:department/matematik :department/fizik])




  ;end
)
(defn f5 [v]
  (mapv #(get-in % [:db/ident]) v))


(defn f6 [ms]
  (let [ids (mapv #(get % :id) ms)
        codes (mapv #(get % :code) ms)
        names (mapv #(get % :name) ms)
        departments (mapv #(get-in % [:department]) ms)
        idents (map f5 departments)]
    {:id ids
     :code codes
     :name names
     :department idents
     })
  )

(f6 (map f3 (flatten dpt-courses)))
;=>
;{:id [201 301],
; :code ["MAT101" "BIL101"],
; :name ["Calculus 1" "MATLAB Giriş"],
; :department ([:department/matematik] [:department/matematik :department/fizik])}

(clerk/table (f6 (map f3 (flatten dpt-courses))))
