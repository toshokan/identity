(defproject identity "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "MIT"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.773"]
                 [com.bhauman/figwheel-main "0.2.11"]
                 [com.bhauman/rebel-readline "0.1.4"]
                 [reagent "1.0.0-alpha2"]
                 [re-frame "1.1.1"]
                 [day8.re-frame/http-fx "0.2.1"]
                 [compojure "1.6.2"]
                 [cheshire "5.10.0"]
                 [clj-http "3.10.1"]
                 [hiccup "1.0.5"]
                 [ring/ring-core "1.7.1"]
                 [ring/ring-jetty-adapter "1.7.1"]]
  :main ^:skip-aot identity.core
  :source-paths ["src/clj" "src/cljs"]
  :resource-paths ["target" "resources"]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :aliases {"fig" ["trampoline" "run" "-m" "figwheel.main"]
            "fig-dev" ["fig" "--" "--build" "dev" "--repl"]
            "fig-build" ["fig" "--" "-bo" "prod"]})
