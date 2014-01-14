#+clj (ns com.sinapsi.fb.controllers.deadband-relay-test
        (:require [clojure.test :refer [deftest testing is are use-fixtures]]
                  [com.sinapsi.fb.protocols :refer [config config! start!]]
                  [com.sinapsi.fb.controllers.deadband-relay :refer [deadband-relay]]))

#+cljs (ns com.sinapsi.fb.controllers.deadband-relay-test
         (:require-macros [cemerick.cljs.test :refer (deftest is are testing use-fixtures)])
         (:require [cemerick.cljs.test :as t]
                   [com.sinapsi.fb.protocols :refer [config config! start!]]
                   [com.sinapsi.fb.controllers.deadband-relay :refer [deadband-relay]]))

(deftest deadband-relay-test
  (testing "(deadband-relay)"
    (are [expected actual] (= expected actual)
         0.0 @(:zone (deadband-relay))))
  (testing "(deadband-relay & kvs)"
    (are [expected actual] (= expected actual)
         1.0 @(:zone (deadband-relay :zone 1.0))))
  (testing "(config deadband-relay)"
    (are [expected actual] (= expected actual)
         {:zone 0.0} (config (deadband-relay))
         {:zone 1.0} (config (deadband-relay :zone 1.0))))
  (testing "(config (config! deadband-relay))"
    (are [expected actual] (= expected actual)
         {:zone 0.0} (config (config! (deadband-relay)))
         {:zone 1.0} (config (config! (deadband-relay :zone 1.0)))))
  (testing "(config (config! deadband-relay & kvs))"
    (are [expected actual] (= expected actual)
         {:zone 1.0} (config (config! (deadband-relay) :zone 1.0))))
  (testing "(start! deadband-relay value)"
    (are [expected actual] (= expected actual)
         0.0 (start! (deadband-relay) 0.0)
         -1e0 (start! (deadband-relay) -1e10)
         1e0 (start! (deadband-relay) 1e10))))
