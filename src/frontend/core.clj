(ns frontend.core
  (:require
    [io.pedestal.http :as http]
    [io.pedestal.http.route :as route]
    [environ.core :refer [env]])
  (:use
    [hiccup.core]
    [hiccup.page])
  (:gen-class))


(defn respond-health [request]
  {:status 200 :body "Ok"})

(defn respond-root [request]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    (html5
              [:a {:href (env :api-endpoint)} "Invoke backend..."])})

(def routes
  (route/expand-routes
    #{["/" :get respond-root :route-name :root]
      ["/health" :get respond-health :route-name :health]
      }))

(defn create-server []
  (http/create-server
    {::http/routes routes
     ::http/type   :jetty
     ::http/port   8080}))

(defn -main [& args]
  (http/start (create-server)))