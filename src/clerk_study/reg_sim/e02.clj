(ns clerk-study.reg-sim.e02
  (:require [nextjournal.clerk :as clerk]))

;Tarih:20230517

;Sorgu simulasyonu

(require '[clojure.string :as str])
(require '[clojure.walk :as walk])
(require '[datomic.client.api :as d])


(def client (d/client {:server-type :dev-local
                       :storage-dir :mem
                       :system "ci"}))

(def conn (d/connect client {:db-name "db5"}))

(def datodb (d/db conn))


(d/q
  '[:find ?e
    :where
    [?e :course/name "Matematik"]]
  datodb)
;=> [[92358976733263]]


(d/q
  '[:find ?e ?course-name ?department
    :in $ [[?course-name]]
    :where
    [?e :course/name ?course-name]
    [?e :course/department ?department]]
  datodb [["Matlab Giriş"] ["Matematik"]])
;=> [[92358976733264 "Matlab Giriş" 200]
; [92358976733263 "Matematik" 200]
; [92358976733263 "Matematik" 100]]


(d/q
  '[:find ?e ?course-name ?department ?course-code
    :in $ [[?course-name]]
    :where
    [?e :course/name ?course-name]
    [?e :course/department ?department]
    [?e :course/code ?course-code]]
  datodb [["Matlab Giriş"] ["Matematik"]])
;=>
;[[92358976733263 "Matematik" 200 "MAT101"]
; [92358976733264 "Matlab Giriş" 200 "BIL101"]
; [92358976733263 "Matematik" 100 "MAT101"]]



#_(d/q
  '[:find (pull ?e [*])
    :where
    [?e :department/id _]]
  datodb)
;[[{:db/id 92358976733259, :department/id 100, :department/title "Matematik"}]
; [{:db/id 92358976733260, :department/id 200, :department/title "Fizik"}]
; [{:db/id 92358976733261, :department/id 300, :department/title "Sosyoloji"}]]

;q1: Referans olarak department/id kullandik. Sorgu ekrani problemini cozerken programlama tarafinda yapmak durumunda
;kalmamiz gerekiyor gibi geldi.
;
(def departments (-> (d/q
                       '[:find (pull ?e [*])
                         :where
                         [?e :department/id _]]
                       datodb)
                     flatten) )

(identity departments)
;=>
;({:db/id 92358976733259, :department/id 100, :department/title "Matematik"}
; {:db/id 92358976733260, :department/id 200, :department/title "Fizik"}
; {:db/id 92358976733261, :department/id 300, :department/title "Sosyoloji"})

(def dt {:id [] :title []})

(map #(identity (% :department/id)) departments)
;=> (100 200 300)


(into [] #(% :department/id) departments)
(map #(assoc-in dt [:id] (% :department/id)) departments)
;=> ({:id 100, :title []} {:id 200, :title []} {:id 300, :title []})
(-> dt
    (map #(assoc-in (% :department/id)) departments))
    (assoc-in [:title] (departments :title)))
(clerk/table departments)