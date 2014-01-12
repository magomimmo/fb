#+clj (ns com.sinapsi.fb.controllers.deadband-test
        (:require [clojure.test :refer [deftest testing is are use-fixtures]]
                  [com.sinapsi.fb.protocols :refer [config config! start!]]
                  [com.sinapsi.fb.controllers.deadband :refer [deadband]]))

#+cljs (ns com.sinapsi.fb.controllers.deadband-test
         (:require-macros [cemerick.cljs.test :refer (deftest is are testing use-fixtures)])
         (:require [cemerick.cljs.test :as t]
                   [com.sinapsi.fb.protocols :refer [config config! start!]]
                   [com.sinapsi.fb.controllers.deadband :refer [deadband]]))

(deftest deadband-test
  (testing "(deadband)"
    (are [expected actual] (= expected actual)
         0.0 @(:zone (deadband))))
  (testing "(deadband & kvs)"
    (are [expected actual] (= expected actual)
         1.0 @(:zone (deadband :zone 1.0))))
  (testing "(config deadband)"
    (are [expected actual] (= expected actual)
         {:zone 0.0} (config (deadband))
         {:zone 1.0} (config (deadband :zone 1.0))))
  (testing "(config (config! deadband))"
    (are [expected actual] (= expected actual)
         {:zone 0.0} (config (config! (deadband)))
         {:zone 1.0} (config (config! (deadband :zone 1.0)))))
  (testing "(config (config! deadband & kvs))"
    (are [expected actual] (= expected actual)
         {:zone 1.0} (config (config! (deadband) :zone 1.0))))
  (testing "(start! deadband 0.0)"
    (are [expected actual] (= expected actual)
         0.0 (start! (deadband) 0.0)
         -1e10 (start! (deadband) -1e10)
         1e10 (start! (deadband) 1e10))))
