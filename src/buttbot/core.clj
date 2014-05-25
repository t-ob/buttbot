(ns buttbot.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn words []
  (str/split-lines (slurp (io/resource "words"))))

(defn capitalised? [[first-char & _]]
  (<= (int \A) (int first-char) (int \Z)))

(defn concat-butts [word]
  (str word " " (if (capitalised? word) "Butts" "butts")))

(defn next-word [word]
  (->> (words)
       (drop-while (partial not= word))
       rest
       first))
