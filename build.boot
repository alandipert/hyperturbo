(set-env!
 :dependencies '[[adzerk/bootlaces "0.1.13" :scope "test"]]
 :resource-paths  #{"src"})

(require '[adzerk.bootlaces :refer :all])

(def +version+ "1.0.0")
(bootlaces! +version+)

(task-options!
 pom {:project 'alandipert/hyperturbo
      :version "1.0.0"})

(deftask build
  "Build jar and install to local repo."
  []
  (comp (pom) (jar) (install)))

(deftask play
  "Play lunar lander"
  []
  (fn [continue]
    (fn [event]
      (require 'alandipert.hyperturbo.lunar-lander)
      ((resolve 'alandipert.hyperturbo.lunar-lander/run)))))
