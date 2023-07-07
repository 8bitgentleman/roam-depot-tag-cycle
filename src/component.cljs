(ns tag-cycle-v5
  (:require
   [reagent.core :as r]
   [datascript.core :as d]
   [roam.datascript.reactive :as dr]
   [roam.datascript :as rd]
   [roam.block :as block]
   [clojure.pprint :as pp]))

; THIS CODEBLOCK IS OVERWRITTEN ON EVERY VERSION UPDATE
; DO NOT MODIFY


(defn get-options [parent-uid]
  ;gets the strings of direct children below a parent
  ;sorted by block/order
  (->> (:block/children (rd/pull 
      '[:block/uid 
        :node/title 
        {:block/children [:block/order :block/string]}] 
      [:block/uid parent-uid]))
    (sort-by :block/order)
    (map :block/string)))

(defn get-block-string [uid]
  (:block/string (rd/pull 
      '[:block/string 
        ] 
      [:block/uid uid]))
)

(defn update-block-string [uid block-string]
  (block/update 
      {:block {:uid uid
               :string block-string}})
  
)

(defn cycle-tags [uid parent-uid]
  (let [tags (get-options parent-uid)
        block-string (get-block-string uid)]
    (doseq [[index tag] (map-indexed vector tags)]
      (let [re (re-pattern (str "\\[\\[" tag "\\]\\](?=( |$))"))]
        (when (re-find re block-string)
          (let [rep-index (mod (inc index) (count tags))
                new-block-string (clojure.string/replace block-string re (str "[[" (nth tags rep-index) "]]"))]
            (update-block-string uid new-block-string)))))
  )
)


(defn main [{:keys [block-uid]} & args]
  [:span.cycle-button { :class "cycle-button"
                     :draggable false
                     :style {
                             }
                     :on-click #(cycle-tags block-uid (get (first args) 1))}"↻"]
  
  )

