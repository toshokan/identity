(ns identity.core
  (:require [reagent.dom :as rd]
            [re-frame.core :as rf]))

(defn startup []
  [:div [:h2 "Identity"]])

(rf/reg-event-db
 :init
 (fn [_ _]
   {:challenge (.value (.getElementById js/document "challenge"))}))

(defn mount-root []
  (let [element (.getElementById js/document "root")]
    (rf/dispatch [:init])
    (rd/render [startup] element)))

(mount-root)
