{:shared {:clean-targets ["out" :target-path]
          :test-paths ["target/test/clj" "target/test/cljs"]
          :resources-paths ["dev-resources"]
          :dependencies [[lein-light-nrepl "0.0.10"]]
          :plugins [[com.keminglabs/cljx "0.3.2"]
                    [com.cemerick/clojurescript.test "0.2.1"]]
          :cljx {:builds [{:incremental? true
                           :source-paths ["test/cljx"]
                           :output-path "target/test/clj"
                           :rules :clj}
                          {:source-paths ["test/cljx"]
                           :output-path "target/test/cljs"
                           :rules :cljs}]}
          :cljsbuild
          {:builds {:fb
                    {:source-paths ["target/test/cljs"]
                     :compiler
                     {:output-dir "dev-resources/public/js"
                      :source-map "dev-resources/public/js/fb.js.map"}}}
           :test-commands {"phantomjs" ["phantomjs" :runner "dev-resources/public/js/fb.js"]}}}

 :dev [:shared
       {:source-paths ["dev-resources/tools/http" "dev-resources/tools/repl"]
        :dependencies [[ring "1.2.1"]
                       [compojure "1.1.6"]
                       [enlive "1.1.4"]]
        :plugins [[com.cemerick/austin "0.1.3"]]
        :cljsbuild
        {:builds {:fb
                  {:source-paths ["dev-resources/tools/repl"]
                   :compiler
                   {:optimizations :whitespace}}}}
        :injections [(require '[ring.server :as http :refer [run-server]]
                              'cemerick.austin.repls)
                     (defn browser-repl []
                       (cemerick.austin.repls/cljs-repl (reset! cemerick.austin.repls/browser-repl-env
                                                                (cemerick.austin/repl-env))))]}]
 :simple [:shared
          {:cljsbuild
           {:builds {:fb
                     {:compiler
                      {:optimizations :simple}}}}}]
 :advanced [:shared
            {:cljsbuild
             {:builds {:fb
                       {:compiler
                        {:optimizations :advanced}}}}}]}
