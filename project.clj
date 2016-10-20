(defproject org.clojure/core.match "0.3.0-alpha4-SNAPSHOT"
  :description "Optimized pattern matching for Clojure"

  :test-paths ["src/test/clojure"]
  :source-paths ["src/main/clojure"]

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [figwheel-sidecar "0.5.6"]
                 #_[org.clojure/tools.analyzer.jvm "0.6.5"]
                 [org.clojure/clojurescript "1.9.229"]]

  :plugins [[lein-cljsbuild "1.1.1"]
            [lein-figwheel "0.5.8"]
            [cider/cider-nrepl "0.8.1"]]

  :cljsbuild
  {:builds
   [{:id "dev"
     :source-paths ["src/main/clojure"]
     :figwheel true
     :compiler {:output-to "resources/public/dev/js/main.dev.js"
                :asset-path "dev/js"
                :output-dir "resources/public/dev/js"
                :verbose true}
    }
    #_{:id "test"
     :source-paths ["src/test/cljs"]
     :compiler {:output-to "out/test.js"
                :static-fns true
                :optimizations :simple}}
    #_{:id "test-adv"
     :source-paths ["src/test/cljs"]
     :compiler {:output-to "out/test.js"
                :optimizations :advanced}}]})
