(ns overtone-tut.zankoku)

(require '[leipzig.melody :refer [all bpm is phrase tempo then times where with]])
(require '[overtone.live :as overtone]
         '[leipzig.live :as live]
         '[leipzig.scale :as scale])

(def alto-sax
  (phrase [ 8/4  8/4  6/4  6/4  4/4  4/4  4/4  4/4  4/4   3/4  4/4  6/4]
          [  -1    1    2    1    2    2    2    5    4   3.5    2  3.5]))

(overtone/definst beep [freq 440 dur 1.0]
  (-> freq
      overtone/saw
      (* (overtone/env-gen (overtone/perc 0.05 dur) :action overtone/FREE))))

(defmethod live/play-note :default [{midi :pitch seconds :duration}]
  (-> midi overtone/midi->hz (beep seconds)))

(->>
  alto-sax
  (tempo (bpm 90))
  (where :pitch (comp scale/C scale/major))
  live/play)
