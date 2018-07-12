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


(defn input-control [state]
  [:div.field
   [:label.label (:label state)]
   [:div.control
    [(:input state) (:attr state) (:estado state)]]
   [:p.help
      {:class (teste-collor (@(:estado state) (:attr state)))}
      (if (teste-is-valid? (@(:estado state) (:attr state))) "É valido" "Não válido")]])

(defn input-text [attr estado]
  [:input.input {:type "text"
                 :class (teste-collor (attr @estado))
                 :on-change #(swap! estado assoc attr (-> % .-target .-value))}])

(defn form-teste []
  [input-control {:input input-text
                  :label "Cadastro"
                  :attr :password
                  :estado app-state
                  :fn-validation teste-is-valid?
                  }])


(defn home-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1 "Formulario de exemplo"]
     [:br]
     [:br]
     [form-teste]
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
