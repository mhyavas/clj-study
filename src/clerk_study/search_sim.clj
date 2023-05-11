(ns clerk-study.search-sim
  (:require [nextjournal.clerk :as clerk]))

(require '[datomic.client.api :as d])

(def client (d/client {:server-type :dev-local
                       :storage-dir :mem
                       :system "ci"}))

(def conn (d/connect client {:db-name "db3"}))

(def datodb (d/db conn))



(comment
  ;DB'den data cekiliyor

  ;Data aranan keyword icin filtreleniyor
  ;Öğrenci, sorgulama ekranına girsin.
  ;
  ;Öğrenci sorgulama kriteri olarak, Matematik departmanını seçsin.
  ;
  ;Bu departmandaki derslerin ve öğrencilerin listesini sistem ona sunsun.

  (d/q
    '[:find ?e
      :where
      [_ :name ?e]] datodb)

  ;Clerk formatina cevriliyor


  ;end
,)


