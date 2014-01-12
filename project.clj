(defproject com.sinapsi/fb "0.0.1-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License - v 1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo}
  :min-lein-version "2.3.4"
  :source-paths ["src/clj" "src/cljs"]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2127"]]
  :plugins [[lein-cljsbuild "1.0.1"]]
  :hooks [leiningen.cljsbuild]
  :cljsbuild
  {:crossovers [com.sinapsi.fb.controllers.pid
                com.sinapsi.fb.controllers.advanced-pid
                com.sinapsi.fb.protocols]
   :builds {:fb
            {:source-paths ["src/cljs"]
             :compiler
             {:output-to "dev-resources/public/js/fb.js"
              :output-dir "dev-resources/public/js"
              :source-map "dev-resources/public/js/fb.js.map"
              :optimizations :none
              :pretty-print false}}}})
