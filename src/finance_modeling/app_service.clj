(ns finance-modeling.app-service
  (:require [finance-modeling.simulator :as simulator]
            [finance-modeling.strategies :as strategies]
            [finance-modeling.data-loader :as data]))

(defn simulate [{tickers :tickers}]
          (let [data-source (data/get-file-loader)]
            (->> tickers
                 (data/get-data-for-tickers data-source)
                 simulator/collate-input
                 simulator/format-for-simulation
                 (simulator/simulate strategies/flat-rebalance))))
