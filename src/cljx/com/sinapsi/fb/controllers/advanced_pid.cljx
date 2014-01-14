(ns com.sinapsi.fb.controllers.advanced-pid
  (:require [com.sinapsi.fb.protocols :as api :refer [Component
                                                      config
                                                      configure!
                                                      start!
                                                      config!
                                                      *sample-time*]]))

(defrecord AdvancedPID [consts vars])

(defn advanced-pid [& {:keys [kp ki kd clamp alpha]
              :or {kp 0.0 ki 0.0 kd 0.0
                   clamp [-1e10 1e10] alpha 1.0}}]
  (->AdvancedPID {:kp (atom kp) :ki (atom ki) :kd (atom kd)
                  :clamp (atom clamp) :alpha (atom alpha)}
         (atom {:e 0.0 :i 0.0 :d 0.0 :unclamped true})))

(extend-type AdvancedPID
  Component
  (config [this]
          {:kp @(get-in this [:consts :kp])
           :ki @(get-in this [:consts :ki])
           :kd @(get-in this [:consts :kd])
           :clamp @(get-in this [:consts :clamp])
           :alpha @(get-in this [:consts :alpha])})
  (configure! [this kvs]
              (let [{:keys [kp ki kd clamp alpha]} kvs]
                (when kp (reset! (get-in this [:consts :kp]) kp))
                (when ki (reset! (get-in this [:consts :ki]) ki))
                (when kd (reset! (get-in this [:consts :kd]) kd))
                (when clamp (reset! (get-in this [:consts :clamp]) clamp))
                (when alpha (reset! (get-in this [:consts :alpha]) alpha))
                this))
  (start! [this value]
          (let [{:keys [kp ki kd clamp alpha]} (:consts this)
                kp @kp
                ki @ki
                kd @kd
                [lo hi] @clamp
                alpha @alpha
                {:keys [e i d unclamped]} @(:vars this)
                integrator (if unclamped (* *sample-time* value) i)
                derivator (+ (* (/ (- value e) *sample-time*) alpha)
                             (* d (- 1.0 alpha)))]
            (reset! (:vars this) {:e value
                                  :i (+ i integrator)
                                  :d derivator
                                  :unclamped (if (or (< value lo)
                                                     (> value hi))
                                               false
                                               true)})
            (+ (* kp value)
               (* ki integrator)
               (* kd derivator)))))