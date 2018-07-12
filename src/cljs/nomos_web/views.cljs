(ns nomos-web.views
  (:require
   [reagent.core :as r]
   [re-frame.core :as re-frame]
   [nomos-web.subs :as subs]))

;; shared componentes



;;TODO: exemplo para incluir uma funcionalidade XPTO


(defn teste-collor
  "Utilitario para validacao de cores"
  [callback password]
  (let [valid-color "is-info"
        invalid-color "is-danger"]
    (if (callback password)
      valid-color
      invalid-color)))

(defn input-control
  "Input Control para inclusão de estilos do bulma para o input"
  [state]
  [:div.field
   [:label.label (:label state)]
   [:div.control
    [(:input state) (:attr state) (:estado state) (:fn-validation state)]]
   [:p.help
    {:class (teste-collor (:fn-validation state) (@(:estado state) (:attr state)))}
    (if ((:fn-validation state) (@(:estado state) (:attr state))) "É valido" "Não válido")]])

;;criacao do input-text
(defn input-text [attr estado callback]
  [:input.input {:type "text"
                 :class (teste-collor callback (attr @estado))
                 :on-change #(swap! estado assoc attr (-> % .-target .-value))}])

;;TODO: criacao do radio-button

;;TODO: criacao do select

;;TODO: criacao do textarea


;;fim da area de shared componentes

;;TODO: finalizar o modelo
;; este eh apenas a ideia de como vai ficar os formulários de input
(def form-state (r/atom {:name nil
                        :age nil
                        :password nil}))

;;metodos personalizados de validacoes

;;old metodo de validacao de nome
;;foi substituido logo abaixo no formulario
(defn is-name-valid?
  [name]
  (> (count name) 10))


(defn is-password-valid?
  [password]
  (> (count password) 4))

;;formulario de testes
(defn form-teste [param]
  [:div
   [:h2 (:titulo param)]
   [input-control {:input input-text
                   :label "Nome"
                   :attr :name
                   :estado form-state
                   :fn-validation #(> (count %) 10)
                   }]
   [input-control {:input input-text
                   :label "Password"
                   :attr :password
                   :estado form-state
                   :fn-validation is-password-valid?}]])


(defn home-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:br]
     [:br]
     [:h2 "Estado Atual do Formulário"]
     [:pre.json {} @form-state]
     [:hr]
     [form-teste {:titulo "Formulário de Exemplo"}]
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
