(ns com.sinapsi.fb.controllers.pid
  (:require [com.sinapsi.fb.protocols :as api :refer [Component
                                                      config
                                                      configure!
                                                      start!
                                                      config!
                                                      *sample-time*]]))

(defrecord PID [consts vars])

(defn pid [& {:keys [kp ki kd]
              :or {kp 0.0 ki 0.0 kd 0.0}}]
  (->PID {:kp (atom kp) :ki (atom ki) :kd (atom kd)}
         (atom {:e 0.0 :i 0.0})))

(extend-type PID
  Component
  (config [this]
          {:kp @(get-in this [:consts :kp])
           :ki @(get-in this [:consts :ki])
           :kd @(get-in this [:consts :kd])})
  (configure! [this kvs]
              (let [{:keys [kp ki kd]} kvs]
                (when kp (reset! (get-in this [:consts :kp]) kp))
                (when ki (reset! (get-in this [:consts :ki]) ki))
                (when kd (reset! (get-in this [:consts :kd]) kd))
                this))
  (start! [this value]
          (let [{:keys [kp ki kd]} (:consts this)
                {:keys [e i]} @(:vars this)
                integrator (* *sample-time* value)
                derivator (/ (- value e) *sample-time*)]
            (reset! (:vars this) {:e value :i (+ i integrator)})
            (+ (* @kp value)
               (* @ki integrator)
               (* @kd derivator)))))