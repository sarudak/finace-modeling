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

(def initial-investment 1000.0)

(defn format-for-simulation [ticker-data]
  (let [securities (map :ticker (first ticker-data))
        security-count (count securities)
        to-invest-per-security (/ initial-investment security-count)]
    {:current-investments (zipmap securities (repeat to-invest-per-security))
     :returns '()
     :ticker-data  ticker-data}))

;(defn simulate [simulation-data]
;  (if (seq :ticker-data )))

(def sample-data (get-data-for-tickers (get-file-loader) ["F" "AAPL" "XOM"]))

(def collated-data (collate-input sample-data))

(def formatted-data (format-for-simulation collated-data))






















