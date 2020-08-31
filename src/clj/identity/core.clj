(ns identity.core
  (:require [identity.server :as server]))

(defn -main []
  (server/start))
