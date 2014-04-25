(ns poseidon.core
  [:require [clojure.data.json :as json]]
  [:require [clj-http.client :as client]])

(def api-base "https://api.digitalocean.com/")

(defn rekey
  [mp ks]
  (if (> (count ks) 0)
    (rekey (get mp (first ks)) (rest ks))
    mp))

(defn env
  "The value of an environment variable k."
  [k] (System/getenv k))

(defn creds
  "DO credentials map based on environment variables $DO_CLIENT_ID and $DO_API_KEY."
  []
  {:api-key (env "DO_API_KEY")
   :client-id (env "DO_CLIENT_ID")})

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
  (let [path (fn [route] (str api-base route (url-creds)))
        args-ok? (fn [expected actual] (if (= (expected) (keys actual)) true false))
        query-fn (fn [route expected] (fn [params]
                                      (if (args-ok? expected params)
                                        (str (path route)
                                             (for [k expected]
                                               (if (= k (first expected))
                                                 (str (name k) "=" (get params k))
                                                 (str "&" (name k) "=" (get params k))))))))
        queries {:droplets
                 {:get-all (query-fn "droplets?" nil)
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
