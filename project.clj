(defproject org.clojure/core.match "0.3.0-alpha4-SNAPSHOT"
  :description "Optimized pattern matching for Clojure"

  :test-paths ["src/test/clojure"]
  :source-paths ["src/main/clojure"]

  :dependencies [[org.clojure/clojure "1.9.0-alpha13"]
                 [org.clojure/tools.analyzer.jvm "0.6.5"]
                 [org.clojure/clojurescript "1.9.229"]]

  :plugins [[lein-cljsbuild "1.1.1"]
            [cider/cider-nrepl "0.8.1"]]

  :cljsbuild
  {:builds
   [{:id "dev"
     :source-paths ["src/main/clojure"]
     :compiler {:output-to "out/dev/main.dev.js"
                :output-dir "out/dev"
                :verbose true
                :optimizations :simple}
    }
    {:id "test"
     :source-paths ["src/test/cljs"]
     :compiler {:output-to "out/test.js"
                :static-fns true
                :optimizations :simple}}
    {:id "test-adv"
     :source-paths ["src/test/cljs"]
     :compiler {:output-to "out/test.js"
                :optimizations :advanced}}]})
