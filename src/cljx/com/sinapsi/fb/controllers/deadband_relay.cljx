(ns com.sinapsi.fb.controllers.deadband-relay
  (:require [com.sinapsi.fb.protocols :as api :refer [Component
                                                      config
                                                      configure!
                                                      start!
                                                      config!
                                                      *sample-time*]]))

(defrecord DeadbandRelay [zone])

(defn deadband-relay [& {:keys [zone]
              :or {zone 0.0}}]
  (->DeadbandRelay (atom zone)))

(extend-type DeadbandRelay
  Component
  (config [this]
          {:zone @(:zone this)})
  (configure! [this kvs]
              (let [{:keys [zone]} kvs]
                (when zone (reset! (:zone this) zone))
                this))
  (start! [this value]
          (let [zone @(:zone this)]
            (if (> value zone)
              1.0
              (if (< value (- zone))
                -1.0
                0.0)))))
