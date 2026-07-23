(ns marketentry.facts "Lebanon market-entry catalog.")
(def catalog
  {"LBN" {:name "Lebanon"
          :owner-authority "Public Procurement Authority (PPA) / هيئة الشراء العام"
          :legal-basis "Public Procurement Law No. 244 of 19 July 2021, as amended by Law No. 309 of 19 April 2023"
          :national-spec "PPA central e-procurement platform (المنصة الإلكترونية المركزية)"
          :provenance "https://www.ppa.gov.lb/en/pages/details/11"
          :required-evidence ["Commercial Registry record (Ministry of Justice)" "PPA e-procurement platform registration record" "Beneficial-ownership declaration record (Form M18 / Ministry of Finance)" "Authorized-representative record"]
          :rep-owner-authority "contracting authorities / PPA"
          :rep-legal-basis "Lebanese legal-entity registration (Commercial Registry) typically required for public awards"
          :rep-provenance "https://www.justice.gov.lb/index.php/department-details/4/2"
          :corporate-number-owner-authority "Ministry of Justice / Commercial Registry (السجل التجاري)"
          :corporate-number-legal-basis "Commercial Registry number (Sijil Tijari) -- Lebanese Code of Commerce Art. 23 / Legislative Decree No. 11/67 Art. 4"
          :corporate-number-provenance "http://cr.justice.gov.lb/"}
   "USA" {:name "United States" :owner-authority "GSA/SAM.gov" :legal-basis "FAR" :national-spec "SAM.gov" :provenance "https://sam.gov/"
          :required-evidence ["EIN record" "SAM.gov registration record" "State business registration record" "SAM UEI verification record"]}
   "SAU" {:name "Saudi Arabia" :owner-authority "Etimad" :legal-basis "Government Tenders Law" :national-spec "Etimad" :provenance "https://tenders.etimad.sa/"
          :required-evidence ["CR number" "Etimad registration" "CR extract" "Authorized-representative record"]}
   "EGY" {:name "Egypt" :owner-authority "e-Tenders" :legal-basis "Law 182/2018" :national-spec "e-Tenders" :provenance "https://etenders.gov.eg/"
          :required-evidence ["Commercial registry" "e-Tenders registration" "Tax card" "Authorized-representative record"]}})

(defn spec-basis [iso3] (get catalog iso3))
(defn coverage
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s) missing (remove catalog iso3s)]
     {:requested (count iso3s) :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note "R0 catalog seed"})))
(defn required-evidence-satisfied? [iso3 submitted]
  (when-let [{:keys [required-evidence]} (spec-basis iso3)]
    (= (count required-evidence) (count (filter (set submitted) required-evidence)))))
(defn evidence-checklist [iso3] (:required-evidence (spec-basis iso3) []))
(defn rep-spec-basis [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:rep-owner-authority sb)
      (select-keys sb [:rep-owner-authority :rep-legal-basis :rep-provenance]))))
(defn corporate-number-spec-basis [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:corporate-number-owner-authority sb)
      (select-keys sb [:corporate-number-owner-authority :corporate-number-legal-basis :corporate-number-provenance]))))
