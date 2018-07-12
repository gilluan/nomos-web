(ns nomos-web.views
  (:require
   [reagent.core :as r]
   [re-frame.core :as re-frame]
   [nomos-web.subs :as subs]
   ))

;; shared componentes



;; funcionalidade XPTO


;; home




(defn teste-collor
  [callback password]
  (let [valid-color "is-info"
        invalid-color "is-danger"]
    (if (callback password)
      valid-color
      invalid-color)))

(defn input-control [state]
  [:div.field
   [:label.label (:label state)]
   [:div.control
    [(:input state) (:attr state) (:estado state) (:fn-validation state)]]
   [:p.help
      {:class (teste-collor (:fn-validation state) (@(:estado state) (:attr state)))}
      (if ((:fn-validation state) (@(:estado state) (:attr state))) "É valido" "Não válido")]])

(defn input-text [attr estado callback]
  [:input.input {:type "text"
                 :class (teste-collor callback (attr @estado))
                 :on-change #(swap! estado assoc attr (-> % .-target .-value))}])



;;TODO: finalizaer o modelo
;; este eh apenas a ideia de como vai ficar os formulários de input
(def form-state (r/atom {:name nil
                        :age nil
                        :password nil}))

(defn is-name-valid?
  [name]
  (> (count name) 10))


(defn is-password-valid?
  [password]
  (> (count password) 4))


(defn form-teste []
  [:div
   [input-control {:input input-text
                   :label "Nome"
                   :attr :name
                   :estado form-state
                   :fn-validation is-name-valid?
                   }]
   [input-control {:input input-text
                   :label "Password"
                   :attr :password
                   :estado form-state
                   :fn-validation is-password-valid?}]])


(defn home-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1 "Formulario de exemplo"]
     [:br]
     [:br]
     [:pre.json {} @form-state]
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
