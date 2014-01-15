(ns com.sinapsi.fb.systems.spring
  (:require [com.sinapsi.fb.protocols :as api :refer [Component
                                                      config
                                                      configure!
                                                      start!
                                                      config!
                                                      *sample-time*]]))

(defrecord Spring [consts vars])

(defn spring [& {:keys [m k g]
                 :or {m 0.1 k 1.0 g 0.05}}]
  (->Spring {:m (atom m) :k (atom k) :g (atom g)}
            (atom {:x 0.0 :v 0.0})))

(extend-type Spring
  Component
  (config [this]
          {:m @(get-in this [:consts :m])
           :k @(get-in this [:consts :k])
           :g @(get-in this [:consts :g])})
  (configure! [this kvs]
              (let [{:keys [m k g]} kvs]
                (when m (reset! (get-in this [:consts :m]) m))
                (when k (reset! (get-in this [:consts :k]) k))
                (when g (reset! (get-in this [:consts :g]) g))
                this))
  (start! [this value]
          (let [{:keys [m g k]} (:consts this)
                {:keys [x v]} (:vars this)
                a (/ (+ (- (* @k *@x))
                        (- (* @g * @v))
                        value)
                     @m)
                vel (+ (* *sample-time* a)
                       v)
                pos (* *sample-time* vel)]
            (swap! this assoc-in [:vars :v] vel)
            (swap! this assoc-in [:vars :x] pos
            pos))))
