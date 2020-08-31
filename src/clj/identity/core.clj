(ns identity.core
  (:use ring.adapter.jetty)
  (:require [identity.server :as server]
            [compojure.core :refer :all]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.util.response :as r]
            [hiccup.page :refer [html5 include-js]]))

(def home-page
  (html5
   [:html
    [:head
     [:meta {:charset "utf-8"}]
     [:title "Identity"]]
    [:body
     [:h1 "Identity"]
     [:div {:hidden true}]
     [:div#root]
     (include-js "js/app.js")]]))

(defroutes app
  (GET "/" [] home-page))

(defn -main []
  (-> app
      (wrap-resource "public")
      (wrap-content-type)
      (run-jetty {:port 8001})))
