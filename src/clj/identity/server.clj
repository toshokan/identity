(ns identity.server
  (:use ring.adapter.jetty)
  (:require [compojure.core :refer :all]
            [ring.middleware.content-type :refer [wrap-content-type]]
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

(defn challenge-info [challenge]
  (some-> (str "http://localhost:8001/oauth/v1/challenge/" challenge)
          (h/get {:accept :json})
          :body
          (json/parse-string true)))

(defroutes app
  (GET "/" [challenge]
       (let [info (challenge-info challenge)]
         (home-page info))))

(defn start []
  (-> app
      (wrap-resource "public")
      (wrap-content-type)
      (wrap-params)
      (run-jetty {:port 8002})))
