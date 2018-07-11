(ns nomos-web.views
  (:require
   [reagent.core :as r]
   [re-frame.core :as re-frame]
   [nomos-web.subs :as subs]
   ))

;; shared componentes



;; funcionalidade XPTO


;; home

(def app-state (r/atom {:password nil}))

(defn teste-is-valid?
  [password]
  (> (count password) 5))


(defn teste-collor
  [password]
  (let [valid-color "is-info"
        invalid-color "is-danger"]
    (if (teste-is-valid? password)
      valid-color
      invalid-color)))

(defn input-teste []
  [:input.input {:type "text"
                 :class (teste-collor (@app-state :password))
           :on-change #(swap! app-state assoc :password (-> % .-target .-value))}])

(defn home-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:div.field
      [:div.control
       [input-teste]]]
     [:p.help
      {:class (teste-collor (@app-state :password))}
      (if (teste-is-valid? (@app-state :password)) "É valido" "Não válido")]
     [:div
      [:a {:href "#/about"}
       "go to About Page"]]
     ]))

;; about

(defn about-panel []
  [:div
   [:h1 "This is the About Page."]

   [:div
    [:a {:href "#/"}
     "go to Home Page"]]])


;; main

(defn- panels [panel-name]
  (case panel-name
    :home-panel [home-panel]
    :about-panel [about-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [show-panel @active-panel]))
