(defproject poseidon "0.1.0-SNAPSHOT"
  :repl-options {:port 4555}
  :description "A DigitalOcean deploy manager in Clojure."
  :url "http://synapsegarden.net/poseidon"
  :license {:name "Affero GPL"
            :url "http://www.gnu.org/licenses/agpl-3.0.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/data.json "0.2.4"]
                 [clj-http "0.9.1"]])
