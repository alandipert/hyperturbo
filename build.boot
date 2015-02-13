(set-env!
  :source-paths  #{"src"})

(task-options!
 pom {:project 'alandipert/hyperturbo
      :version "0.0.1-SNAPSHOT"})

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
