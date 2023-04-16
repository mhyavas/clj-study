(ns datomic.basics)

(require '[datomic.client.api :as d])


(def cfg {:server-type :peer-server
          :access-key "myaccesskey"
          :secret "mysecret"
          :endpoint "localhost:8998"
          :validate-hostnames false})

(def client (d/client cfg))
;#'user/client
(def conn (d/connect client {:db-name "movies"}))
;#'user/conn
