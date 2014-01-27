(ns finance-modeling.data-loader
  (:use csv-map.core))

(defn convert-for-output [{date "Date" adjusted-value "Adj Close"}]
  {:date date :adjusted-value adjusted-value})

(defn get-ticker-data-from-file [ticker]
  (->> (str ticker ".csv")
      clojure.java.io/resource
      slurp
      parse-csv
      (map convert-for-output)))

(defprotocol DataProvider
  (get-data-for-ticker [_ ticker]))

(defrecord LocalFileLoader [] DataProvider
  (get-data-for-ticker [_ ticker]
                       (get-ticker-data-from-file ticker)))




