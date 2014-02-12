(ns finance-modeling.strategies
  (:use clj-time.predicates)
  (:require [clj-time.core :as datetime]))

(defn flat-rebalance [current-investments]
  (let [tickers (keys current-investments)
        total-investments (apply + (vals current-investments))]
    (zipmap tickers (repeat (/ total-investments (count tickers))))))

(defn no-rebalance [current-investments] current-investments)

(defn is-daily-rebalance-day? [date] true)

(defn is-weekly-rebalance-day? [date] (tuesday? date))

(defn is-monthly-rebalance-day? [date]
  (or (and (= (datetime/day date) 1) (not (saturday? date)) (not (sunday? date)))
      (and (#{2 3} (datetime/day date)) (sunday? (datetime/plus date (datetime/days -1))))))


