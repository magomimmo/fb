{:dev {:clean-targets ["out"]
       :test-paths ["target/test/clj" "target/test/cljs"]
       :source-paths ["dev-resources/tools/http" "dev-resources/tools/repl"]
       :resources-paths ["dev-resources"]
       :dependencies [[ring "1.2.1"]
                      [compojure "1.1.6"]
                      [enlive "1.1.4"]]
       :plugins [[com.cemerick/clojurescript.test "0.2.1"]
                 [com.cemerick/austin "0.1.3"]
                 [com.keminglabs/cljx "0.3.2"]]
       :cljx {:builds [{:source-paths ["test/cljx"]
                        :output-path "target/test/clj"
                        :rules :clj}
                       {:source-paths ["test/cljx"]
                        :output-path "target/test/cljs"
                        :rules :cljs}]}
       :cljsbuild
       {:builds {:fb
                 {:source-paths ["target/test/cljs" "dev-resources/tools/repl"]
                  :compiler
                  {:optimizations :whitespace
                   :pretty-print true}}}
        :test-commands {"phantomjs" ["phantomjs" :runner "dev-resources/public/js/fb.js"]}}
       :injections [(require '[ring.server :as http :refer [run-server]]
                             'cemerick.austin.repls)
                    (defn browser-repl []
                      (cemerick.austin.repls/cljs-repl (reset! cemerick.austin.repls/browser-repl-env
                                                               (cemerick.austin/repl-env))))]}
 :simple {:clean-targets ["out"]
          :test-paths ["target/test/clj" "target/test/cljs"]
          :plugins [[com.cemerick/clojurescript.test "0.2.1"]
                    [com.keminglabs/cljx "0.3.2"]]
          :cljx {:builds [{:source-paths ["test/cljx"]
                           :output-path "target/test/clj"
                           :rules :clj}
                          {:source-paths ["test/cljx"]
                           :output-path "target/test/cljs"
                           :rules :cljs}]}
          :cljsbuild
          {:builds {:fb
                    {:source-paths ["target/test/cljs"]
                     :compiler
                     {:optimizations :simple}}}
           :test-commands {"phantomjs"
                           ["phantomjs" :runner "dev-resources/public/js/fb.js"]}}}
 :advanced {:clean-targets ["out"]
            :test-paths ["target/test/clj" "target/test/cljs"]
            :plugins [[com.cemerick/clojurescript.test "0.2.1"]
                      [com.keminglabs/cljx "0.3.2"]]
            :cljx {:builds [{:source-paths ["test/cljx"]
                             :output-path "target/test/clj"
                             :rules :clj}
                            {:source-paths ["test/cljx"]
                             :output-path "target/test/cljs"
                             :rules :cljs}]}
            :cljsbuild
            {:builds {:fb
                      {:source-paths ["target/test/cljs"]
                       :compiler
                       {:optimizations :advanced}}}
             :test-commands {"phantomjs-advanced"
                             ["phantomjs" :runner "dev-resources/public/js/fb.js"]}}}}
