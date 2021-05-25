(ns identity.server
  (:use ring.adapter.jetty)
  (:require [compojure.core :refer :all]
            ;; [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.util.response :as r]
            [clj-http.client :as h]
            [cheshire.core :as json]
            [hiccup.page :refer [html5 include-js]]))

(defn home-page [info]
  (html5
   [:html
    [:head
     [:meta {:charset "utf-8"}]
     [:title "Identity"]]
    [:body
     [:input#challenge {:type "hidden" :value info}]
     [:div#root]
     (include-js "js/app.js")]]))

(defn challenge-info [challenge-id]
  (some-> (str "http://localhost:8001/oauth/v1/challenge/" challenge-id)
          (h/get {:accept :json})
          :body
          (json/parse-string true)))

(defn- reply-challenge [challenge-id reply]
  (some-> (str "http://localhost:8001/oauth/v1/challenge/" challenge-id)
          (h/post {:body reply
                   :content-type :json})
          :body
          (json/parse-string true)))

(defn accept-challenge [challenge-id]
  (reply-challenge challenge-id "\"Accept\""))

(defn reject-challenge [challenge-id]
  (reply-challenge challenge-id "\"Reject\""))

(defroutes app
  (GET "/failure" []
       (r/response "That didn't work"))
  (GET "/login" [challenge-id]
       (let [info (challenge-info challenge-id)]
         (home-page info)))
  (POST "/login" [challenge-id user-id password :as r]
        (let [response (if (= user-id password)
                         (accept-challenge challenge-id)
                         (reject-challenge challenge-id))]
          (r/redirect (:RedirectTo response) :see-other))))

(defn start []
  (-> app
      (wrap-resource "public")
      ;; (wrap-content-type)
      (wrap-params)
      (run-jetty {:port 8002})))
