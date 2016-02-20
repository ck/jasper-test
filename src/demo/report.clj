(ns demo.report
  (:import [net.sf.jasperreports.engine
            JRDataSource
            JREmptyDataSource
            JRField
            JRRewindableDataSource]
           [net.sf.dynamicreports.report.builder DynamicReports]
           [net.sf.dynamicreports.report.builder.column ColumnBuilder ValueColumnBuilder]
           [net.sf.dynamicreports.report.builder.component ComponentBuilder]))

;; helpers
(defn datasource-proxy
  [s]
  (let [rows       (vec (doall s))
        last-index (dec (count rows))
        idx        (atom -1)]
    (proxy [JRDataSource JRRewindableDataSource]
        []
      (next []
        (if (< @idx last-index)
          (do
            (swap! idx inc)
            true)
          false))
      (getFieldValue [^JRField field] ((nth rows @idx) (keyword (.getName field))))
      (moveFirst [] (reset! idx -1)))))

(defn text [s] (.text (DynamicReports/cmp) s))
(defn column [{:keys [name type title] :as spec}]
  (.column (DynamicReports/col) title (clojure.core/name name) type))

(defn report
  [{:keys [columns datasource title]}]
  (cond-> (DynamicReports/report)
    columns    (.columns (into-array ColumnBuilder columns))
    datasource (.setDataSource datasource)
    title      (.title (into-array ComponentBuilder [title]))))

(defn exporter
  [opts]
  (.pdfExporter (DynamicReports/export) (:output opts)))

(defn export
  [report exporter]
  (.toPdf report exporter))

;; data
(def data
  [{:item "Notebook" :quantity 1 :unitprice 500M :test 1}
   {:item "DVD"      :quantity 5 :unitprice 30M  :test 1}
   {:item "DVD"      :quantity 1 :unitprice 28M  :test 1}
   {:item "DVD"      :quantity 5 :unitprice 32M  :test 1}
   {:item "Book"     :quantity 3 :unitprice 11M  :test 1}
   {:item "Book"     :quantity 1 :unitprice 15M  :test 1}
   {:item "Book"     :quantity 5 :unitprice 10M  :test 1}
   {:item "Book"     :quantity 8 :unitprice 9M   :test 1}])


;; design
(def design
  {:title   (text "Sample 1")
   :columns [(column {:title "Item"       :name :item      :type java.lang.String})
             (column {:title "Quantity"   :name :quantity  :type java.lang.Long})
             (column {:title "Unit price" :name :unitprice :type java.math.BigDecimal})]})

;; export options
(def export-opts
  {:format :pdf
   :output "output/sample1.pdf"})

(defn generate
  "Export report to file."
  []
  (prn "Generate Report")
  (try
    (let [datasource (datasource-proxy data)
          report     (report (assoc design :datasource datasource))
          exporter   (exporter export-opts)]
      (export report exporter))
    (catch Exception e
      (let [st (.getStackTrace e)
            n  (alength st)]
        (doseq [i (range n)
                :let [l (aget st i)]]
          (println (.getFileName l) "-" (.getClassName l) "." (.getMethodName l) ":" (.getLineNumber l)))))))
