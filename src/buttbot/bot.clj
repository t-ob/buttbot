(ns buttbot.bot
  (:require [clojure.string :as str]
            [buttbot.core :refer [concat-butts words next-word]]
            [buttbot.twitter :refer [tweet]]
            [chime :refer [chime-at]]
            [clj-time.core :refer [now minutes]]
            [clj-time.periodic :refer [periodic-seq]]))

(def *current-word-file* "current_word")

(defn tweet-next-word! []
  (let [last-tweeted-word (slurp *current-word-file*)
        word (next-word last-tweeted-word)]
    (spit *current-word-file* word)
    (tweet (concat-butts word))))

(defn -main []
  (let [current-word (slurp *current-word-file*)
        remaining (->> (words)
                       (drop-while (partial not= current-word))
                       count)]
    (chime-at (take count (periodic-seq (now) (-> 30 minutes)))
              (fn [_]
                (tweet-next-word!)))))
