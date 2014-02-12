(ns finance-modeling.data-loader
  (:use csv-map.core)
  (:require [clj-time.format :as date-format]))

(def read-formatter (date-format/formatter "yyyy-MM-dd"))

(defn convert-for-output [ticker {date "Date" adjusted-value "Adj Close"}]
  {:date (date-format/parse read-formatter date) :adjusted-value adjusted-value :ticker ticker})

(defn get-ticker-data-from-file [ticker]
  (->> (str ticker ".csv")
      clojure.java.io/resource
      slurp
      parse-csv
      (map (partial convert-for-output ticker))))

(defprotocol TickerDataProvider
  (get-data-for-tickers [_ tickers]))

(defrecord LocalTickerFileLoader [] TickerDataProvider
  (get-data-for-tickers [_ tickers]
                       (map (memoize (fn [ticker] (get-ticker-data-from-file ticker))) tickers)))

(defn get-file-loader [] (LocalTickerFileLoader.))













