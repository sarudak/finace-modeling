(ns finance-modeling.handler
  (:use compojure.core cheshire.core ring.util.response)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]
            [finance-modeling.app-service :as service]))

; Here's the template definitions

(defn run-simulation [request]
  (response (service/simulate {:tickers (:stockTickers request)})))

 ; Here's the route definitions
(defroutes app-routes
  (GET "/" [] (slurp (clojure.java.io/resource "templates/rebalance-modeling.html")))
  (POST "/simulate" {body :body} (run-simulation body))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/site app-routes)
        (middleware/wrap-json-body)
        (middleware/wrap-json-response)))
