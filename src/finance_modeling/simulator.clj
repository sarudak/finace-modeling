(ns finance-modeling.simulator
  (:require [clj-time.core :as datetime]))

(defn count-grouping [group]
  (count (second group)))

(defn first-day-of-month? [date] (= (datetime/day date) 1))

(defn collate-input [raw-data]
  (let [data (flatten raw-data)
        grouped (group-by :date data)
        full-set-count (apply max (map count-grouping grouped))
        is-full-set? #(= (count-grouping %) full-set-count)]
    (->> grouped
         (filter is-full-set?)
         (sort-by first)
         (map second))))

(def initial-investment 1000.0)

(defn list-to-map [key-resolver items]
  (->> items
       (map #(vector (key-resolver %) %))
       flatten
       (apply hash-map)))

(defn format-for-simulation [ticker-data]
  (let [securities (map :ticker (first ticker-data))
        security-count (count securities)
        to-invest-per-security (/ initial-investment security-count)
        formatted-ticker-data (map #(list-to-map :ticker %) ticker-data)]
    {:current-investments (zipmap securities (repeat to-invest-per-security))
     :returns '()
     :current-ticker-data (first formatted-ticker-data)
     :future-ticker-data  (rest formatted-ticker-data)}))

(defn calculate-new-investment-values [past-investments past-ticker-data current-ticker-data]
  (let [get-adjusted-value #(read-string (:adjusted-value %))]
    (into {} (for [[ticker investment] past-investments]
               [ticker (* investment (/ (get-adjusted-value (current-ticker-data ticker)) (get-adjusted-value (past-ticker-data ticker))))]))))

(defn simulate [rebalance-strategy simulation-data]
    (loop [{:keys [current-investments returns current-ticker-data future-ticker-data] :as new-data} simulation-data]
      (if (empty? future-ticker-data)
      new-data
      (let [future-investments (calculate-new-investment-values current-investments current-ticker-data (first future-ticker-data))
            rebalanced-investments (rebalance-strategy future-investments)
            return-date (:date (first (vals current-ticker-data)))
            new-returns (/ (apply + (vals rebalanced-investments)) initial-investment)]
        (recur {:current-investments rebalanced-investments
                :final-return new-returns
                :returns (cons {:date return-date :return new-returns} returns)
                :current-ticker-data (first future-ticker-data)
                :future-ticker-data (rest future-ticker-data)})))))





