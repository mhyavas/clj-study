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
  '[:find ?e ?product-name ?color ?product-price
    :in $ [[?product-name ?product-price]]
    :where
    [?e :product/name ?product-name]
    [?e :product/color ?color]]
  db [["Kalem" 120] ["Defter" 250]])
