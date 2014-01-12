{:dev {:clean-targets ["out"]
       :test-paths ["target/test/clj" "target/test/cljs"]
       :source-paths ["dev-resources/tools/http" "dev-resources/tools/repl"]
       :resources-paths ["dev-resources"]
       :dependencies [[ring "1.2.1"]
                      [compojure "1.1.6"]
                      [enlive "1.1.4"]]
       :plugins [[com.cemerick/clojurescript.test "0.2.1"]
                 [com.cemerick/austin "0.1.3"]
                 [com.keminglabs/cljx "0.3.0"]]
       :cljx {:builds [{:source-paths ["test/cljx"]
                        :output-path "target/test/clj"
                        :rules :clj}
                       {:source-paths ["test/cljx"]
                        :output-path "target/test/cljs"
                        :rules :cljs}]}
       :cljsbuild
       {:builds {:whitespace
                 {:source-paths ["src/cljs" "target/test/cljs" "dev-resources/tools/repl"]
                  :compiler
                  {:output-to "dev-resources/public/js/fb.js"
                   :output-dir "dev-resources/public/js"
                   :source-map "dev-resources/public/js/fb.js.map"
                   :optimizations :whitespace
                   :pretty-print true}}
                 #_{:source-paths ["src/cljs" "target/test/cljs"]
                  :compiler
                  {:output-to "dev-resources/public/js/simple.js"
                   :optimizations :simple
                   :pretty-print false}}
                 #_{:source-paths ["src/cljs" "target/test/cljs"]
                  :compiler
                  {:output-to "dev-resources/public/js/advanced.js"
                   :optimizations :advanced
                   :pretty-print false}}}
        :test-commands {"phantomjs-ws"
                        ["phantomjs" :runner "dev-resources/public/js/fb.js"]
                        ;"phantomjs-simple"
                        #_["phantomjs" :runner "dev-resources/public/js/simple.js"]
                        ;"phantomjs-advanced"
                        #_["phantomjs" :runner "dev-resources/public/js/advanced.js"]}}

       :injections [(require '[ring.server :as http :refer [run-server]]
                             'cemerick.austin.repls)
                    (defn browser-repl []
                      (cemerick.austin.repls/cljs-repl (reset! cemerick.austin.repls/browser-repl-env
                                                               (cemerick.austin/repl-env))))]}}
