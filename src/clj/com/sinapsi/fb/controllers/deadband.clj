(ns com.sinapsi.fb.controllers.deadband
  (:require [com.sinapsi.fb.protocols :as api :refer [Component
                                                      config
                                                      configure!
                                                      start!
                                                      config!
                                                      *sample-time*]]))

(defrecord Deadband [zone])

(defn deadband [& {:keys [zone]
              :or {zone 0.0}}]
  (->Deadband (atom zone)))

(extend-type Deadband
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
              (- value zone)
              (if (< value (- zone))
                (+ value zone)
                0.0)))))
