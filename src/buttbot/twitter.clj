(ns buttbot.twitter
  (:require [clojure.java.io :as io]
            [twitter.oauth :refer [make-oauth-creds]]
            [twitter.api.restful :refer [statuses-update]]))

(defn twitter-creds
  "Returns map of Twitter oauth creds with keys
    :app-consumer-key
    :app-consumer-secret
    :user-access-token
    :user-access-token-secret"
  []
  (read-string (slurp (io/resource "oauth.edn"))))

(defn oauth-creds []
  (let [{:keys [app-consumer-key app-consumer-secret user-access-token user-access-token-secret]} (twitter-creds)]
    (make-oauth-creds app-consumer-key app-consumer-secret user-access-token user-access-token-secret)))

(defn tweet [message]
  (statuses-update :oauth-creds (oauth-creds)
                   :params {:status message}))
