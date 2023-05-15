(ns user)

(require '[nextjournal.clerk :as clerk])

;; start Clerk's built-in webserver on the default port 7777, opening the browser when done
(clerk/serve! {:browse? true})

(clerk/show! "src/clerk_study/n1.clj")
#_(clerk/show! "src/clerk_study/registration.clj")
;; or let Clerk watch the given `:paths` for changes
(clerk/serve! {:watch-paths ["src/clerk_study" "src"]})

(clerk/serve! {:watch-paths ["src/clerk_study" "src"] :show-filter-fn #(clojure.string/starts-with? % "src/clerk_study")})

