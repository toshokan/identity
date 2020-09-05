(ns identity.server
  (:use ring.adapter.jetty)
  (:require [compojure.core :refer :all]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.util.response :as r]
            [hiccup.page :refer [html5 include-js]]))

(defn home-page [challenge]
  (html5
   [:html
    [:head
     [:meta {:charset "utf-8"}]
     [:title "Identity"]]
    [:body
     [:div#challenge {:hidden true} challenge]
     [:div#root]
     (include-js "js/app.js")]]))

(defroutes app
  (GET "/" [challenge] (home-page challenge)))

(defn start []
  (-> app
      (wrap-resource "public")
      (wrap-content-type)
      (wrap-params)
      (run-jetty {:port 8001})))
