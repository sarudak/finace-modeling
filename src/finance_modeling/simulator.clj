(ns finance-modeling.simulator
  (:use finance-modeling.data-loader)
  (:require [clj-time.core :as datetime]))

(defn count-grouping [group]
  (count (second group)))

(defn first-day-of-month? [date] (= (datetime/day date) 1))

;This function is doing too much
(defn collate-input [raw-data]
  (let [data (flatten raw-data)
        grouped (group-by :date data)
        full-set-count (apply max (map count-grouping grouped))
        is-full-set? #(= (count-grouping %) full-set-count)]
    (->> grouped
         (filter is-full-set?)
         (filter #(first-day-of-month? (first %)))
         (sort-by first)
         (map second))))

(def initial-investment 1000)

(def sample-data (get-data-for-tickers (get-file-loader) ["F" "AAPL" "XOM"]))

(->> sample-data flatten (group-by :date) (map count-grouping) (apply max))


(def collated-data (collate-input sample-data))

(count collated-data)

collated-data





















