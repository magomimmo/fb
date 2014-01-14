#+clj (ns com.sinapsi.fb.systems.boiler-test
        (:require [clojure.test :refer [deftest testing is are use-fixtures]]
                  [com.sinapsi.fb.protocols :refer [config config! start!]]
                  [com.sinapsi.fb.systems.boiler :refer [boiler]]))

#+cljs (ns com.sinapsi.fb.systems.boiler-test
         (:require-macros [cemerick.cljs.test :refer (deftest is are testing use-fixtures)])
         (:require [cemerick.cljs.test :as t]
                   [com.sinapsi.fb.protocols :refer [config config! start!]]
                   [com.sinapsi.fb.systems.boiler :refer [boiler]]))

(deftest boiler-test
  (testing "(boiler)"
    (are [expected actual] (= expected actual)
         0.0 @(get-in (boiler) [:consts :g])))
  (testing "(boiler & kvs)"
    (are [expected actual] (= expected actual)
         1.0 @(get-in (boiler :g 1.0) [:consts :g])))
  (testing "(config boiler)"
    (are [expected actual] (= expected actual)
         {:g 0.0} (config (boiler))
         {:g 1.0} (config (boiler :g 1.0))))
  (testing "(config (config! pid))"
    (are [expected actual] (= expected actual)
         {:g 0.0} (config (config! (boiler)))
         {:g 1.0} (config (config! (boiler :g 1.0)))))
  (testing "(config (config! boiler & kvs)"
    (are [expected actual] (= expected actual)
         {:g 1.0} (config (config! (boiler) :g 1.0))))
  (testing "(start! boiler 0.0)"
    (are [expected actual] (= expected actual)
         0.0 (start! (boiler) 0.0))))