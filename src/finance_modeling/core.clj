(ns finance-modeling.core
  (:require [clojure.java.io :as io]
            [finance-modeling.data-loader :as loader]
            [finance-modeling.rebalancing :as strategies]
            [finance-modeling.simulator :as simulator])
  (:gen-class :main true))

(defn -main
  [& args]
  (println "Hello, World!"))

(defn run-simulation [tickers rebalance-strategy rebalance-trigger]
  (let [data-loader (loader/get-file-loader)]
     (->> tickers
          (loader/get-data-for-tickers data-loader)
          simulator/collate-input
          simulator/format-for-simulation
          (simulator/simulate rebalance-strategy))))
