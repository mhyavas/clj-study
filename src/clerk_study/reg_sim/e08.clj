(ns clerk-study.reg-sim.e08)
;Tarih 20230526
;Parametrik Sorgular
(require '[datomic.client.api :as d])

(def client (d/client {:server-type :dev-local
                       :storage-dir :mem
                       :system "ci"}))

(def conn (d/connect client {:db-name "db6"}))
(def datodb (d/db conn))

(def student-math2 (d/q '[:find (pull ?e [*])
                          :where
                          [?e :student/id _]
                          [?e :student/department :department/matematik]]
                        datodb))

(defn find-student-by-name [student-name]
  (d/q
    '[:find ?e
      :in $ ?student-name
      :where
      [?e :student/name ?student-name]]
    datodb student-name))

(find-student-by-name "Mahmut Can")
;=> [[83562883711058]]

(defn find-student-by-name2 [student-name]
  (d/q
    '[:find (pull ?e [*])
      :in $ ?student-name
      :where
      [?e :student/name ?student-name]]
    datodb student-name))


(find-student-by-name2 "Mahmut Can")
;=>
;[[{:db/id 83562883711058,
;   :student/id 3,
;   :student/name "Mahmut Can",
;   :student/department #:db{:id 96757023244361, :ident :department/matematik}}]]



