(ns alandipert.hyperturbo)

(declare ^:dynamic *context*)

(defn RETURN
  ([]  (RETURN nil))
  ([v] (swap! *context* assoc :return v)))

(defn GOTO [n]
  (swap! *context* assoc :program-counter n))

(defmacro LET [name v]
  `(swap! *context* assoc-in [:env '~name] ~v))

(defmacro INPUT [name]
  `(LET ~name (read)))

(defmacro GET [name]
  `(get-in @*context* [:env '~name]))

(defn run! []
  (loop []
    (let [{:keys [program return program-counter]} @*context*]
      (if (not= return ::none)
        return
        (when program-counter
          (let [{:keys [thunk next]} (get program program-counter)]
            (thunk)
            (if (= program-counter (get @*context* :program-counter))
              (swap! *context* assoc :program-counter next))
            (recur)))))))

(defn pad
  ([x xs] (concat xs (repeat x)))
  ([xs]   (pad nil xs)))

(defmacro TURBO [& lines]
  (let [nums-exprs (partition 2 lines)
        program (->> nums-exprs
                     (map cons (pad (map first (rest nums-exprs))))
                     (mapcat (fn [[next-line this-line expr]]
                               [this-line {:thunk `(fn [] ~expr) :next next-line}])))]
    `(let [program# (sorted-map ~@program)]
       (binding [*context* (atom {:program program#
                                  :return ::none
                                  :env {}
                                  :program-counter (ffirst program#)})]
         (run!)))))
