(ns poseidon.core
  [:require [clojure.data.json :as json]]
  [:require [clj-http.client :as client]])

(def api-base "https://api.digitalocean.com/")

(defn url-creds
  "The credential login map."
  ([] (str "client_id=" ((creds) :client-id) "&api_key=" ((creds) :api-key)))
  ([c]
     (if (and (c :client-id) (c :api-key))
       (str "client_id=" (c :client-id) "&api_key=" (c :api-key))
       nil)))

(defn digitalocean-queries
  "Queries."
  [querykeys]
  (let [
        queries {:droplets
                 {:get-all (query-fn "droplets/?" nil)
                  :new (query-fn "droplets/new?" [:name :size :image])}}]

    (rekey queries querykeys)))
     ;; :show (query-fn "droplets/")}))
     ;;  :or }
     ;; :regions () regions-query
     ;; :images images-query
     ;; :sshkeys sshkeys-query
     ;; :sizes sizes-query
     ;; :domains domains-query
     ;; :events events-query}))

(defn api-query
  "A query against DigitalOcean API."
  [& {:keys [debug]
      :or {debug false}}]
  (json/read-str
   ((client/get
     ((digitalocean-queries [:droplets :get-all]))
     {:throw-exceptions (not debug)}) :body)))

;;(let [drops ((basic-query) "droplets")
;;      ks (keys (first drops))]
;;(for [drop drops
;;      k ks] [k (get drop k)]))
