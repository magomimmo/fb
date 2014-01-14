(ns com.sinapsi.fb.controllers.relay
  (:require [com.sinapsi.fb.protocols :as api :refer [Component
                                                      config
                                                      configure!
                                                      start!
                                                      config!
                                                      *sample-time*]]))

(defrecord Relay [])

(defn relay []
  (->Relay))

(extend-type Relay
  Component
  (config [this]
          {})
  (configure! [this kvs]
              this)
  (start! [this value]
          (if (= value 0.0)
            0.0
            (/ value (Math/abs value)))))
