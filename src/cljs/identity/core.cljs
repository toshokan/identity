(ns identity.core
  (:require [reagent.dom :as rd]
            [re-frame.core :as rf]
            [cljs.reader :refer [read-string]]))

(defn login-component []
  (let [challenge-info @(rf/subscribe [:challenge-info])]
    [:div 
     [:div (str "Your login is being requested on behalf of " (:client_id challenge-info))]
     [:div "The requested scopes are:"]
     [:ul
      (for [scope (clojure.string/split (:scope challenge-info) #" ")]
        [:li scope])]
     [:br]
     [:form {:method "post"}
      [:input {:name "user-id" :placeholder "Username"}]
      [:br]
      [:input {:name "password" :type "password" :placeholder "Password"}]
      [:br]
      [:br]
      [:button "Submit"]]]))

(defn startup []
  [:div [:h2 "Identity"]
   (login-component)])

(rf/reg-event-db
 :init
 (fn [_ _]
   (let [element (.getElementById js/document "challenge")]
     {:challenge (read-string (.-value element))})))

(rf/reg-sub
 :challenge-id
 (fn [db _]
   (get-in db [:challenge :id])))

(rf/reg-sub
 :challenge-info
 (fn [db _]
   (:challenge db)))

(defn mount-root []
  (let [element (.getElementById js/document "root")]
    (rf/dispatch [:init])
    (rd/render [startup] element)))

(mount-root)
