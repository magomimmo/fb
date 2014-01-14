#+clj (ns com.sinapsi.fb.systems.spring-test
        (:require [clojure.test :refer [deftest testing is are use-fixtures]]
                  [com.sinapsi.fb.protocols :refer [config config! start!]]
                  [com.sinapsi.fb.systems.spring :refer [spring]]))

#+cljs (ns com.sinapsi.fb.systems.spring-test
         (:require-macros [cemerick.cljs.test :refer (deftest is are testing use-fixtures)])
         (:require [cemerick.cljs.test :as t]
                   [com.sinapsi.fb.protocols :refer [config config! start!]]
                   [com.sinapsi.fb.systems.spring :refer [spring]]))

(deftest spring-test
  (testing "(spring)"
    (are [expected actual] (= expected actual)
         0.1 @(get-in (spring) [:consts :m])
         1.0 @(get-in (spring) [:consts :k])
         0.05 @(get-in (spring) [:consts :g])))
  (testing "(spring & kvs)"
    (are [expected actual] (= expected actual)
         1.0 @(get-in (spring :m 1.0) [:consts :m])
         0.0 @(get-in (spring :k 0.0) [:consts :k])
         1.0 @(get-in (spring :g 1.0) [:consts :g])))
  (testing "(config spring)"
    (are [expected actual] (= expected actual)
         {:m 0.1 :k 1.0 :g 0.05} (config (spring))
         {:m 1.0 :k 1.0 :g 0.05} (config (spring :m 1.0))
         {:m 0.1 :k 0.0 :g 0.05} (config (spring :k 0.0))
         {:m 0.1 :k 1.0 :g 1.0} (config (spring :g 1.0))
         {:m 1.0 :k 0.0 :g 1.0} (config (spring :m 1.0 :k 0.0 :g 1.0))))
  (testing "(config (config! spring))"
    (are [expected actual] (= expected actual)
         {:m 0.1 :k 1.0 :g 0.05} (config (config! (spring)))
         {:m 1.0 :k 1.0 :g 0.05} (config (config! (spring :m 1.0)))
         {:m 0.1 :k 0.0 :g 0.05} (config (config! (spring :k 0.0)))
         {:m 0.1 :k 1.0 :g 1.0} (config (config! (spring :g 1.0)))
         {:m 1.0 :k 0.0 :g 1.0} (config (config! (spring :m 1.0 :k 0.0 :g 1.0)))))
  (testing "(config (config! spring & kvs)"
    (are [expected actual] (= expected actual)
         {:m 1.0 :k 1.0 :g 0.05} (config (config! (spring) :m 1.0))
         {:m 0.1 :k 0.0 :g 0.05} (config (config! (spring) :k 0.0))
         {:m 0.1 :k 1.0 :g 1.0} (config (config! (spring) :g 1.0))
         {:m 1.0 :k 0.0 :g 1.0} (config (config! (spring) :m 1.0 :k 0.0 :g 1.0))))
  (testing "(start! spring 0.0)"
    (are [expected actual] (= expected actual)
         0.0 (start! (spring) 0.0))))