(ns clerk-study.reg-sim.e04)


(require '[datomic.client.api :as d])

(def client (d/client {:server-type :dev-local
                       :storage-dir :mem
                       :system "ci"}))


