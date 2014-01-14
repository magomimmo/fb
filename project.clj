(defproject com.sinapsi/fb "0.0.1-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License - v 1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo}
  :min-lein-version "2.3.4"
  :source-paths ["target/src/clj" "target/src/cljs"]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2138"]]
  :plugins [[lein-cljsbuild "1.0.1"]
            [com.keminglabs/cljx "0.3.2"]]
  :hooks [leiningen.cljsbuild]
  :cljx {:builds [{:source-paths ["src/cljx"]
                   :output-path "target/src/clj"
                   :rules :clj}
                  {:source-paths ["src/cljx"]
                   :output-path "target/src/cljs"
                   :rules :cljs}]}
  :cljsbuild
  {:builds {:fb
            {:source-paths ["target/src/cljs"]
             :compiler
             {:output-to "dev-resources/public/js/fb.js"
              :optimizations :advanced
              :pretty-print false}}}})
