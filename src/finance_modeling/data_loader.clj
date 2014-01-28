(ns finance-modeling.data-loader
  (:use clj-time.format csv-map.core))

(def read-formatter (formatter "yyyy-MM-dd"))

(defn convert-for-output [ticker {date "Date" adjusted-value "Adj Close"}]
  {:date (parse read-formatter date) :adjusted-value adjusted-value :ticker ticker})


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
                       (map #(get-ticker-data-from-file %) tickers)))

(get-data-for-tickers (LocalTickerFileLoader.)["F" "AAPL" "XOM"])




