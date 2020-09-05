(ns identity.core
  (:require [reagent.dom :as rd]
            [re-frame.core :as rf]))

(defn startup []
  [:div [:h2 "Identity"]
   [:p @(rf/subscribe [:challenge-value])]])

(rf/reg-event-db
 :init
 (fn [_ _]
   (let [element (.getElementById js/document "challenge")]
     {:challenge (.-value element)})))

(rf/reg-sub
 :challenge-value
 (fn [db _]
   (:challenge db)))

(defn mount-root []
  (let [element (.getElementById js/document "root")]
    (rf/dispatch [:init])
    (rd/render [startup] element)))

(mount-root)
