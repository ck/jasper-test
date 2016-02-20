(defproject demo "0.2.0-SNAPSHOT"
  :description "Test of JasperReports inside Wildfly using Immutant."
  :url "http://github.com/immutant/feature-demo"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.immutant/immutant "2.1.2"]
                 [compojure "1.4.0"]
                 [ring/ring-devel "1.4.0"]
                 [environ "1.0.1"]
                 ;; DynamicReports
                 [org.apache.poi/poi "3.13"]
                 [net.sf.jasperreports/jasperreports "6.2.0"]
                 [net.sf.jasperreports/jasperreports-fonts "6.2.0"]
                 [net.sourceforge.dynamicreports/dynamicreports-core "4.1.0"]
                 [net.sourceforge.dynamicreports/dynamicreports-adhoc "4.1.0"]]

  :repositories [["Immutant incremental builds"
                  "http://downloads.immutant.org/incremental/"]]

  :plugins [[lein-immutant "2.1.0"]
            [info.sunng/lein-bootclasspath-deps "0.2.0"]]

  :boot-dependencies [[org.mortbay.jetty.alpn/alpn-boot "8.1.5.v20150921" :prepend true]]
  :main demo.core
  :uberjar-name "demo-standalone.jar"
  :min-lein-version "2.4.0"
  :jvm-opts ["-Dhornetq.data.dir=target/hornetq-data"
             "-Dcom.arjuna.ats.arjuna.objectstore.objectStoreDir=target/ObjectStore"]
  :profiles {:uberjar {:aot [demo.core]}})
