#+clj (ns com.sinapsi.fb.controllers.hysteresis-relay-test
        (:require [clojure.test :refer [deftest testing is are use-fixtures]]
                  [com.sinapsi.fb.protocols :refer [config config! start!]]
                  [com.sinapsi.fb.controllers.hysteresis-relay :refer [hysteresis-relay]]))

#+cljs (ns com.sinapsi.fb.controllers.hysteresis-relay-test
         (:require-macros [cemerick.cljs.test :refer (deftest is are testing use-fixtures)])
         (:require [cemerick.cljs.test :as t]
                   [com.sinapsi.fb.protocols :refer [config config! start!]]
                   [com.sinapsi.fb.controllers.hysteresis-relay :refer [hysteresis-relay]]))

(deftest hysteresis-relay-test
  (testing "(hysteresis-relay)"
    (are [expected actual] (= expected actual)
         0.0 @(:zone (hysteresis-relay))))
  (testing "(hysteresis-relay & kvs)"
    (are [expected actual] (= expected actual)
         1.0 @(:zone (hysteresis-relay :zone 1.0))))
  (testing "(config hysteresis-relay)"
    (are [expected actual] (= expected actual)
         {:zone 0.0} (config (hysteresis-relay))
         {:zone 1.0} (config (hysteresis-relay :zone 1.0))))
  (testing "(config (config! hysteresis-relay))"
    (are [expected actual] (= expected actual)
         {:zone 0.0} (config (config! (hysteresis-relay)))
         {:zone 1.0} (config (config! (hysteresis-relay :zone 1.0)))))
  (testing "(config (config! hysteresis-relay & kvs))"
    (are [expected actual] (= expected actual)
         {:zone 1.0} (config (config! (hysteresis-relay) :zone 1.0))))
  (testing "(start! hysteresis-relay value)"
    (are [expected actual] (= expected actual)
         -1e0 (start! (hysteresis-relay) 0.0)
         1e0 (start! (hysteresis-relay) 1e1)
         -1e0 (start! (hysteresis-relay) -1e1))))
