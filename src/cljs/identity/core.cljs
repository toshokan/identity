(ns identity.core
  (:require [reagent.dom :as rd]
            [re-frame.core :as rf]
            [cljs.reader :refer [read-string]]))

(defn startup []
  [:div [:h2 "Identity"]
   [:p @(rf/subscribe [:challenge-id])]])

(rf/reg-event-db
 :init
 (fn [_ _]
   (let [element (.getElementById js/document "challenge")]
     {:challenge (read-string (.-value element))})))

(rf/reg-sub
 :challenge-id
 (fn [db _]
   (get-in db [:challenge :id])))

(defn mount-root []
  (let [element (.getElementById js/document "root")]
    (rf/dispatch [:init])
    (rd/render [startup] element)))

(mount-root)
