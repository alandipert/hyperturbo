# HyperTURBO

A language inspired by
[BASIC](http://en.wikipedia.org/wiki/BASIC_programming_language) and
[BAYSICK](https://github.com/fogus/baysick), implemented as a Clojure
macro.

### Dependency

Artifacts are published on Clojars.

[![latest version][1]][2]

### Examples

Small:

```clojure
(require '[alandipert.hyperturbo :refer [TURBO GOTO]])

(TURBO
  10 (println "Hahaha!")
  20 (GOTO 10))
```

Big:

```clojure
(require '[alandipert.hyperturbo :refer [TURBO GOTO RETURN INPUT GET LET]])

(def abs #(if (neg? %) (- %) %))

(TURBO
  10  (println "Welcome to HyperTURBO Lunar Lander v0.0.1")
  20  (LET dist 100)
  30  (LET v    1)
  40  (LET fuel 1000)
  50  (LET mass 1000)

  60  (println "You are in control of a lunar lander.")
  70  (println "You are drifing towards the surface of the moon.")
  80  (println "Each turn you must decide how much fuel to burn.")
  85  (println "To coast, enter 0.")
  90  (println "To accelerate enter a positive number, to decelerate a negative.")

  100 (println (format "Distance %.3f km, Velocity %.3f km/s, Fuel %s" (double (GET dist)) (double (GET v)) (GET fuel)))
  110 (INPUT burn)
  120 (if (<= (abs (GET burn)) (GET fuel)) (GOTO 150))
  130 (println "You don't have that much fuel.")
  140 (GOTO 100)
  150 (LET v (+ (GET v) (* (GET burn) (/ 10 (+ (GET fuel) (GET mass))))))
  160 (LET fuel (- (GET fuel) (abs (GET burn))))
  170 (LET dist (- (GET dist) (GET v)))
  180 (if (> (GET dist) 0) (GOTO 100))
  190 (println "You have hit the surface.")
  200 (if (< (GET v) 3) (GOTO 240))
  210 (println (format "Hit surface too fast (%.3f) km/s" (double (GET v))))
  220 (println "You crashed!")
  230 (GOTO 250)
  240 (println "Well done.")
  250 (RETURN))
```

### Development

- Install the [boot][3] build tool.
- Play Lunar Lander with `boot play`

[1]: https://clojars.org/alandipert/hyperturbo/latest-version.svg?bustcache=000
[2]: https://clojars.org/alandipert/hyperturbo
[3]: https://github.com/boot-clj/boot
