#+clj (ns com.sinapsi.fb.controllers.pid-test
        (:require [clojure.test :refer [deftest testing is are use-fixtures]]
                  [com.sinapsi.fb.protocols :refer [config config! start!]]
                  [com.sinapsi.fb.controllers.pid :refer [pid]]))

#+cljs (ns com.sinapsi.fb.controllers.pid-test
         (:require-macros [cemerick.cljs.test :refer (deftest is are testing use-fixtures)])
         (:require [cemerick.cljs.test :as t]
                   [com.sinapsi.fb.protocols :refer [config config! start!]]
                   [com.sinapsi.fb.controllers.pid :refer [pid]]))

(deftest pid-test
  (testing "(pid)"
    (are [expected actual] (= expected actual)
         0.0 @(get-in (pid) [:consts :kp])
         0.0 @(get-in (pid) [:consts :ki])
         0.0 @(get-in (pid) [:consts :kd])))
  (testing "(pid & kvs"
    (are [expected actual] (= expected actual)
         1.0 @(get-in (pid :kp 1.0) [:consts :kp])
         1.0 @(get-in (pid :ki 1.0) [:consts :ki])
         1.0 @(get-in (pid :kd 1.0) [:consts :kd])))
  (testing "(config pid"
    (are [expected actual] (= expected actual)
         {:kp 0.0 :ki 0.0 :kd 0.0} (config (pid))
         {:kp 1.0 :ki 0.0 :kd 0.0} (config (pid :kp 1.0))
         {:kp 0.0 :ki 1.0 :kd 0.0} (config (pid :ki 1.0))
         {:kp 0.0 :ki 0.0 :kd 1.0} (config (pid :kd 1.0))))
  (testing "(config (config! pid))"
    (are [expected actual] (= expected actual)
         {:kp 0.0 :ki 0.0 :kd 0.0} (config (config! (pid)))
         {:kp 1.0 :ki 0.0 :kd 0.0} (config (config! (pid :kp 1.0)))
         {:kp 0.0 :ki 1.0 :kd 0.0} (config (config! (pid :ki 1.0)))
         {:kp 0.0 :ki 0.0 :kd 1.0} (config (config! (pid :kd 1.0)))))
  (testing "(config (config! pid & kvs"
    (are [expected actual] (= expected actual)
         {:kp 1.0 :ki 0.0 :kd 0.0} (config (config! (pid) :kp 1.0))
         {:kp 0.0 :ki 1.0 :kd 0.0} (config (config! (pid) :ki 1.0))
         {:kp 0.0 :ki 0.0 :kd 1.0} (config (config! (pid) :kd 1.0))
         {:kp 1.0 :ki 1.0 :kd 1.0} (config (config! (pid) :kp 1.0 :ki 1.0 :kd 1.0))))
  (testing "(start! pid 0.0)"
    (are [expected actual] (= expected actual)
         0.0 (start! (pid) 0.0)
         0.0 (start! (pid) -1e10)
         0.0 (start! (pid) 1e10)
         0.0 (start! (pid :kp 1.0) 0.0)
         0.0 (start! (pid :ki 1.0) 0.0)
         0.0 (start! (pid :kd 1.0) 0.0)
         0.0 (start! (pid :kp 1.0 :ki 1.0 :kd 1.0) 0.0))))
