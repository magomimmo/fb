(ns com.sinapsi.fb)

(defn foo [greeting]
  (if greeting
    (str greeting "ClojureScript!")
    (str "Hello, ClojureScript!")))

(.write js/document (foo "Welcome to "))



