(ns clerk-study.reg-sim.e06
  (:require [portal.api :as p]))

;Portal exercise

(def portal (p/open))

(p/tap)
;yukaridaki komut calistirdiktan sonra portalda tap> ile islem yapabiliyoruz
(tap> :hello)
(tap> {:a 1 :b 2})
