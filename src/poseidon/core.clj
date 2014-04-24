(ns poseidon.core
  [:require [clojure.data.json :as json]]
  [:require [clj-http.client :as client]])

(def creds
  {:api-key ""
   :client-id ""})

(def web-creds
  '(str "client_id=" (creds :client-id) "&api_key=" (creds :api-key)))

(def base "https://api.digitalocean.com/")

(def digitalocean-paths
  {:droplets (str base "droplets?" web-creds)})

(defn basic-query
  "A basic query against DigitalOcean API."
  []
  ((client/get (digitalocean-paths :droplets))))
