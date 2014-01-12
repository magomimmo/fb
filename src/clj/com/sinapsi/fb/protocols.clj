(ns com.sinapsi.fb.protocols)

(def ^:dynamic *sample-time* 1)

(defprotocol Component
  (config [this])
  (configure! [this kvs])
  (start! [this value]))

(defn config! [this & kvs]
    (configure! this (apply array-map kvs)))

(defn clamp [x a b]
  (max a (min x b)))
