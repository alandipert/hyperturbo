#!/usr/bin/env boot

#tailrecursion.boot.core/version "2.2.1"

(set-env!
  :project 'alandipert/hyperturbo
  :version "0.0.1-SNAPSHOT"
  :dependencies '[[tailrecursion/boot.task "2.1.0"]]
  :src-paths #{"src"})

(deftask nrepl
  "Runs an nrepl server."
  [& [{:keys [port] :or {port 0}}]]
  (fn [continue]
    (fn [event]
      (set-env! :dependencies '[[org.clojure/tools.nrepl "0.2.3"]])
      (require '[clojure.tools.nrepl.server :refer [start-server]])
      (let [server ((resolve 'start-server) :port port)]
        (println "Started nREPL server on port" (:port server))
        (spit ".nrepl-port" (:port server))
        (.deleteOnExit (java.io.File. ".nrepl-port"))
        (continue event)
        @(promise)))))

(deftask lunar-lander []
  (fn [continue]
    (fn [event]
      (require 'alandipert.hyperturbo.lunar-lander)
      ((resolve 'alandipert.hyperturbo.lunar-lander/run)))))
