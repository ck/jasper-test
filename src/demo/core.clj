(ns demo.core
  (:require demo.web)
  (:gen-class))

(defn -main [& args]
  (apply demo.web/-main args))

(comment

  (-main)
  )
