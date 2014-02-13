(ns finance-modeling.handler
  (:use compojure.core cheshire.core ring.util.response)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]
            [finance-modeling.app-service :as service]
            [clj-time.format :as datetime]))

; Here's the template definitions
(add-encoder org.joda.time.DateTime
             (fn [date jsonGenerator]
               (.writeString jsonGenerator
                 (datetime/unparse (datetime/formatters :basic-date-time) date))))


(defn run-simulation [request]
  (do
   (println (type request))
   (println (type (first (keys request))))
   (println request)
   (response (service/simulate {:tickers (request "stockTickers")}))))

 ; Here's the route definitions
(defroutes app-routes
  (GET "/" [] (slurp (clojure.java.io/resource "templates/rebalance-modeling.html")))
  (POST "/simulate" [body] (run-simulation (parse-string body)))
  (route/resources "/")
  (route/not-found "Not Found"))



(def app
  (-> (handler/site app-routes)
        (middleware/wrap-json-body)
        (middleware/wrap-json-response)))
