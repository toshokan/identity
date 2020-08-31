(ns identity.server
  (:use ring.adapter.jetty)
  (:require [compojure.core :refer :all]
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
     [:div {:hidden true}]
     [:div#root]
     (include-js "js/app.js")]]))

(defroutes app
  (GET "/" [] home-page))

(defn start []
  (-> app
      (wrap-resource "public")
      (wrap-content-type)
      (run-jetty {:port 8001})))
