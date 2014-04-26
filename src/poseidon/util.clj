(ns poseidon.util)

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

(defn path
  "Return a DO query path constructor from constructing key.  Response is a function
   of an argument map which, if it fails to match expected arguments, will result in
   a nil string."
  [routekey]
  (str api-base route (url-creds))
  args-ok? (fn [expected actual] (if (= (expected) (keys actual)) true false))
  query-fn (fn [route expected] (fn [params]
                                 (if (args-ok? expected params)
                                   (str (path route)
                                        (for [k expected]
                                          (if (= k (first expected))
                                            (str (name k) "=" (get params k))
                                            (str "&" (name k) "=" (get params k)))))))))
