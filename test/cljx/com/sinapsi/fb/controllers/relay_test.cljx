#+clj (ns com.sinapsi.fb.controllers.relay-test
        (:require [clojure.test :refer [deftest testing is are use-fixtures]]
                  [com.sinapsi.fb.protocols :refer [config config! start!]]
                  [com.sinapsi.fb.controllers.relay :refer [relay]]))

#+cljs (ns com.sinapsi.fb.controllers.relay-test
         (:require-macros [cemerick.cljs.test :refer (deftest is are testing use-fixtures)])
         (:require [cemerick.cljs.test :as t]
                   [com.sinapsi.fb.protocols :refer [config config! start!]]
                   [com.sinapsi.fb.controllers.relay :refer [relay]]))

(deftest relay-test
  (testing "Relay Controller"
    (are [expected actual] (= expected actual)
         {}(config (relay))
         {} (config (config! (relay)))
         0.0 (start! (relay) 0.0)
         1e0 (start! (relay) 1e0)
         -1e0 (start! (relay) -1e0)
         1e0 (start! (relay) 1e10)
         -1e0 (start! (relay) -1e10))))
