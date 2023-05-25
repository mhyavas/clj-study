(ns clerk-study.reg-sim.e04)


(require '[datomic.client.api :as d])

(def client (d/client {:server-type :dev-local
                       :storage-dir :mem
                       :system "ci"}))


(def conn (d/connect client {:db-name "db6"}))

(def datodb (d/db conn))

(d/q '[:find (pull ?e [*])
       :where
       [?e :course/id _]
       [?e :course/department :department/fizik]]
     datodb)
;=>
;[[{:db/id 74766790688846,
;   :course/id 301,
;   :course/code "BIL101",
;   :course/name "MATLAB Giriş",
;   :course/department [#:db{:id 96757023244361, :ident :department/matematik}
;                       #:db{:id 96757023244362, :ident :department/fizik}]}]]

(d/q '[:find (pull ?e [*])
       :where
       [?e :course/id _]
       [?e :course/department :department/matematik]]
     datodb)
;=>
;[[{:db/id 74766790688845,
;   :course/id 201,
;   :course/code "MAT101",
;   :course/name "Calculus 1",
;   :course/department [#:db{:id 96757023244361, :ident :department/matematik}]}]
; [{:db/id 74766790688846,
;   :course/id 301,
;   :course/code "BIL101",
;   :course/name "MATLAB Giriş",
;   :course/department [#:db{:id 96757023244361, :ident :department/matematik}
;                       #:db{:id 96757023244362, :ident :department/fizik}]}]]



