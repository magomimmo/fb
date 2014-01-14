(ns com.sinapsi.fb.controllers.hysteresis-relay
  (:require [com.sinapsi.fb.protocols :as api :refer [Component
                                                      config
                                                      configure!
                                                      start!
                                                      config!
                                                      *sample-time*]]))

(defrecord HysteresisRelay [zone vars])

(defn hysteresis-relay [& {:keys [zone]
              :or {zone 0.0}}]
  (->HysteresisRelay (atom zone)
                     {:e (atom 0.0)}))

(extend-type HysteresisRelay
  Component
  (config [this]
          {:zone @(:zone this)})
  (configure! [this kvs]
              (let [{:keys [zone]} kvs]
                (when zone (reset! (:zone this) zone))
                this))
  (start! [this value]
          (let [zone @(:zone this)
                prev @(:e (:vars this))]
            (reset! (get-in this [:vars :e]) prev)
            (if (> value prev)
              (if (< value zone)
                0.0
                1.0)
              (if (> value (- zone))
                0.0
                -1.0)))))