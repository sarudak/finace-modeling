(defproject finance-modeling "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [csv-map "0.1.0-SNAPSHOT"]
                 [clj-time "0.6.0"]
                 [compojure "1.1.5"]
                 [cheshire "5.3.1"]
                 [ring/ring-json "0.1.2"]]
  :plugins [[lein-ring "0.8.3"]]
  :ring {:handler finance-modeling.handler/app}
  :main finance-modeling.core)
