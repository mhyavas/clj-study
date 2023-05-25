(ns clerk-study.reg-sim.e07)


(def departmetns ({:db/id 92358976733259, :department/id 100, :department/title "Matematik"}
  {:db/id 92358976733260, :department/id 200, :department/title "Fizik"}
  {:db/id 92358976733261, :department/id 300, :department/title "Sosyoloji"}))



(map #(assoc-in dt [:id] (% :department/id)) departments)
