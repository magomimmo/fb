(ns com.sinapsi.fb-test
  (:require-macros [cemerick.cljs.test :as m :refer (deftest testing are)])
  (:require [cemerick.cljs.test :as t]
            [com.sinapsi.fb :refer (foo)]))

(deftest foo-test
  (testing "I don't do a lot\n"
    (testing "Edge cases\n"
      (testing "(foo str)"
        (are [expected actual] (= expected actual)
             "ClojureScript!" (foo "")
             "Hello, ClojureScript!" (foo nil))))))