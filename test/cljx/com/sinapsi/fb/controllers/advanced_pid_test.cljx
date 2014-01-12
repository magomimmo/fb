#+clj (ns com.sinapsi.fb.controllers.advanced-pid-test
        (:require [clojure.test :refer [deftest testing is are use-fixtures]]
                  [com.sinapsi.fb.protocols :refer [config config! start!]]
                  [com.sinapsi.fb.controllers.advanced-pid :refer [advanced-pid]]))

#+cljs (ns com.sinapsi.fb.controllers.advanced-pid-test
         (:require-macros [cemerick.cljs.test :refer (deftest are testing)])
         (:require [cemerick.cljs.test :as t]
                   [com.sinapsi.fb.protocols :refer [config config! start!]]
                   [com.sinapsi.fb.controllers.advanced-pid :refer [advanced-pid]]))


(deftest advanced-pid-test
  (testing "(advanced-pid)"
    (are [expected actual] (= expected actual)
         0.0 @(get-in (advanced-pid) [:consts :kp])
         0.0 @(get-in (advanced-pid) [:consts :ki])
         0.0 @(get-in (advanced-pid) [:consts :kd])
         [-1e10 1e10] @(get-in (advanced-pid) [:consts :clamp])
         1.0 @(get-in (advanced-pid) [:consts :alpha])))
  (testing "(advanced-pid & kvs)"
    (are [expected actual] (= expected actual)
         1.0 @(get-in (advanced-pid :kp 1.0) [:consts :kp])
         1.0 @(get-in (advanced-pid :ki 1.0) [:consts :ki])
         1.0 @(get-in (advanced-pid :kd 1.0) [:consts :kd])
         [-1e0 1e0] @(get-in (advanced-pid :clamp [-1e0 1e0]) [:consts :clamp])
         1e1 @(get-in (advanced-pid :alpha 1e1) [:consts :alpha])))
  (testing "(config advanced-pid)"
    (are [expected actual] (= expected actual)
         {:kp 0.0 :ki 0.0 :kd 0.0 :clamp [-1e10 1e10] :alpha 1.0} (config (advanced-pid))
         {:kp 1.0 :ki 0.0 :kd 0.0 :clamp [-1e10 1e10] :alpha 1.0} (config (advanced-pid :kp 1.0))
         {:kp 0.0 :ki 1.0 :kd 0.0 :clamp [-1e10 1e10] :alpha 1.0} (config (advanced-pid :ki 1.0))
         {:kp 0.0 :ki 0.0 :kd 1.0 :clamp [-1e10 1e10] :alpha 1.0} (config (advanced-pid :kd 1.0))
         {:kp 0.0 :ki 0.0 :kd 0.0 :clamp [-1e0 1e0] :alpha 1.0} (config (advanced-pid :clamp [-1e0 1e0]))
         {:kp 0.0 :ki 0.0 :kd 0.0 :clamp [-1e10 1e10] :alpha 1e1} (config (advanced-pid :alpha 1e1))))
  (testing "(config (config! advanced-pid))"
    (are [expected actual] (= expected actual)
         {:kp 0.0 :ki 0.0 :kd 0.0 :clamp [-1e10 1e10] :alpha 1.0} (config (config! (advanced-pid)))
         {:kp 1.0 :ki 0.0 :kd 0.0 :clamp [-1e10 1e10] :alpha 1.0} (config (config! (advanced-pid :kp 1.0)))
         {:kp 0.0 :ki 1.0 :kd 0.0 :clamp [-1e10 1e10] :alpha 1.0} (config (config! (advanced-pid :ki 1.0)))
         {:kp 0.0 :ki 0.0 :kd 1.0 :clamp [-1e10 1e10] :alpha 1.0} (config (config! (advanced-pid :kd 1.0)))
         {:kp 0.0 :ki 0.0 :kd 0.0 :clamp [-1e0 1e0] :alpha 1.0} (config (config! (advanced-pid :clamp [-1e0 1e0])))
         {:kp 0.0 :ki 0.0 :kd 0.0 :clamp [-1e10 1e10] :alpha 1e1} (config (config! (advanced-pid :alpha 1e1)))))
  (testing "(config (config! advanced-pid & kvs))"
    (are [expected actual] (= expected actual)
         {:kp 1.0 :ki 0.0 :kd 0.0 :clamp [-1e10 1e10] :alpha 1.0} (config (config! (advanced-pid) :kp 1.0))
         {:kp 0.0 :ki 1.0 :kd 0.0 :clamp [-1e10 1e10] :alpha 1.0} (config (config! (advanced-pid) :ki 1.0))
         {:kp 0.0 :ki 0.0 :kd 1.0 :clamp [-1e10 1e10] :alpha 1.0} (config (config! (advanced-pid) :kd 1.0))
         {:kp 0.0 :ki 0.0 :kd 0.0 :clamp [-1e0 1e0] :alpha 1.0} (config (config! (advanced-pid) :clamp [-1e0 1e0]))
         {:kp 0.0 :ki 0.0 :kd 0.0 :clamp [-1e10 1e10] :alpha 1e1} (config (config! (advanced-pid) :alpha 1e1))
         {:kp 1.0 :ki 1.0 :kd 1.0 :clamp [-1e0 1e0] :alpha 1e1} (config (config! (advanced-pid)
                                                                                 :kp 1.0 :ki 1.0 :kd 1.0
                                                                                 :clamp [-1e0 1e0] :alpha 1e1))))
  (testing "(start! advanced-pid 0.0)"
    (are [expected actual] (= expected actual)
         0.0 (start! (advanced-pid) 0.0)
         0.0 (start! (advanced-pid) -1e10)
         0.0 (start! (advanced-pid) 1e10)
         0.0 (start! (advanced-pid :kp 1.0) 0.0)
         0.0 (start! (advanced-pid :ki 1.0) 0.0)
         0.0 (start! (advanced-pid :kd 1.0) 0.0)
         0.0 (start! (advanced-pid :clamp [-1e0 1e0]) 0.0)
         0.0 (start! (advanced-pid :alpha 1e1) 0.0)
         0.0 (start! (advanced-pid :kp 1.0 :ki 1.0 :kd 1.0 :clamp [-1e0 1e0] :alpha 1e1) 0.0))))
