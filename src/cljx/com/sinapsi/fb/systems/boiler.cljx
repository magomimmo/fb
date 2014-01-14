(ns com.sinapsi.fb.systems.boiler
  (:require [com.sinapsi.fb.protocols :as api :refer [Component
                                                      config
                                                      configure!
                                                      start!
                                                      config!
                                                      *sample-time*]]))

(defrecord Boiler [consts vars])

(defn boiler [& {:keys [g]
              :or {g 0.0}}]
  (->Boiler {:g (atom g)}
            (atom {:y 0.0})))

(extend-type Boiler
  Component
  (config [this]
          {:g @(get-in this [:consts :g])})
  (configure! [this kvs]
              (let [{:keys [g]} kvs]
                (when g (reset! (get-in this [:consts :g]) g))
                this))
  (start! [this value]
          (let [{:keys [g]} (:consts this)
                {:keys [y]} @(:vars this)
                integrator (* *sample-time*
                              (+ (* (- y) @g)
                                 value))
                result (+ y integrator)]
            (reset! (:vars this) {:y result})
            result)))
