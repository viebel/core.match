(ns cljs.core.match
  (:require [klipse.match-fn :refer [*match-lookup* *recur-present* *vector-type* *no-backtrack* *warned* *line* *locals* clj-form]]))
 ;; ============================================================================
;; # Match inner functions
 
;; ============================================================================
;; # Match macros
(def preds (atom {}))

(defmacro defpred
  ([name]
     (swap! preds assoc name name))
  ([name f]
     (swap! preds assoc name f)))

(defmacro match
  "Pattern match a row of occurrences. Take a vector of occurrences, vars.
  Clause question-answer syntax is like `cond`. Questions must be
  wrapped in a vector, with same arity as vars. Last question can be :else,
  which expands to a row of wildcards. Optionally may take a single
  var not wrapped in a vector, questions then need not be wrapped in a
  vector.

  Example:
  (let [x 1
        y 2]
    (match [x y 3]
      [1 2 3] :answer1
      :else :default-answer))"
  [vars & clauses]
  (let [[vars clauses]
        (if (vector? vars)
          [vars clauses]
          [(vector vars)
            (mapcat (fn [[c a]]
                      [(if (not= c :else) (vector c) c) a])
              (partition 2 clauses))])]
   (binding [cljs.core.match-fn/*line* (-> &form meta :line)
             cljs.core.match-fn/*locals* (dissoc &env '_)
             cljs.core.match-fn/*warned* (atom false)]
     `~(cljs.core.match-fn/clj-form vars clauses))))

#_(defmacro matchv [type vars & clauses]
  (binding [*vector-type* type
            *line* (-> &form meta :line)
            *locals* (dissoc &env '_)
            *warned* (atom false)]
    `~(clj-form vars clauses)))

#_(defmacro matchm
  "Same as match but supports IMatchLookup when
  matching maps."
  [vars & clauses]
  (let [[vars clauses]
        (if (vector? vars)
          [vars clauses]
          [(vector vars)
           (mapcat (fn [[c a]]
                     [(if (not= c :else) (vector c) c) a])
             (partition 2 clauses))])]
    (binding [*match-lookup* true
              *line* (-> &form meta :line)
              *locals* (dissoc &env '_)
              *warned* (atom false)]
      `~(clj-form vars clauses))))

#_(defmacro match-let [bindings & body]
  (let [bindvars# (take-nth 2 bindings)]
    `(let ~bindings
       (match [~@bindvars#]
         ~@body))))
#_(defmacro asets [a vs]
  `(do
     ~@(map (fn [a b c] (concat a (list b c)))
         (repeat `(aset ~a)) (range (count vs)) vs)
     ~a))


#_(defmacro match*
  [vars & clauses]
  (let [[vars clauses]
        (if (vector? vars)
          [vars clauses]
          [(vector vars)
            (mapcat (fn [[c a]]
                      [(if (not= c :else) (vector c) c) a])
              (partition 2 clauses))])]
   (binding [*line* (-> &form meta :line)
             *locals* (dissoc (:locals &env) '_)
             *warned* (atom false)
             *no-backtrack* true]
     `~(clj-form vars clauses))))

#_(defmacro matchv [type vars & clauses]
  (binding [*vector-type* type
            *line* (-> &form meta :line)
            *locals* (dissoc (:locals &env) '_)
            *warned* (atom false)]
    `~(clj-form vars clauses)))


#_(defmacro matchv* [type vars & clauses]
  (binding [*vector-type* type
            *line* (-> &form meta :line)
            *locals* (dissoc (:locals &env) '_)
            *warned* (atom false)
            *no-backtrack* true]
    `~(clj-form vars clauses)))

#_(defmacro matchm
  [vars & clauses]
  (let [[vars clauses]
        (if (vector? vars)
          [vars clauses]
          [(vector vars)
            (mapcat (fn [[c a]]
                      [(if (not= c :else) (vector c) c) a])
              (partition 2 clauses))])]
   (binding [*line* (-> &form meta :line)
             *locals* (dissoc (:locals &env) '_)
             *warned* (atom false)]
     `~(clj-form vars clauses))))

#_(defmacro match-let [bindings & body]
  (let [bindvars# (take-nth 2 bindings)]
    `(let ~bindings
       (match [~@bindvars#]
         ~@body))))

#_(defmacro match-let* [bindings & body]
  (let [bindvars# (take-nth 2 bindings)]
    `(let ~bindings
       (match* [~@bindvars#]
         ~@body))))

