(ns statute.facts
  "General-law compliance catalog for Lebanon (LBN) -- extends this
  repo's existing `marketentry.facts` (narrow public-procurement
  scope) with a second, orthogonal catalog of statutes a company
  generally must track for compliance. Mirrors
  cloud-itonami-iso3166-jpn/-usa/-esp/-egy/-are/-sau/-omn/-tur's
  `statute.facts` (ADR-2607141700, cloud-itonami-compliance-fact-federation).

  Every entry below was fetched and read directly this session (curl,
  with an Internet Archive Wayback Machine fallback disclosed per
  entry where the live site could not be reached -- Lebanon's
  post-2019-crisis institutional/web-infrastructure instability is a
  real, observed risk, not merely a hypothetical one: this session
  independently found live connection failures (Ministry of Finance,
  finance.gov.lb -- 'Connection refused' / 'Host is down') and a live
  Cloudflare bot-detection challenge (Ministry of Labor,
  labor.gov.lb) that made a Wayback Machine fallback necessary for
  those two sources. No content from either site was reconstructed
  from training-data memory; where a live fetch failed, the entry is
  either sourced from the historical Wayback Machine snapshot
  (disclosed via :statute/url-provenance) or omitted entirely.

  A law not in this table has NO spec-basis, full stop; extend
  `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of statute entries."
  {"LBN"
   [{:statute/id "lbn.law-244-2021-public-procurement"
     :statute/title "Public Procurement Law (Lebanon), establishing the Public Procurement Authority (PPA / هيئة الشراء العام)"
     :statute/jurisdiction "LBN"
     :statute/kind :law
     :statute/law-number "Law No. 244 of 19 July 2021, as amended by Law No. 309 of 19 April 2023"
     :statute/url "https://www.ppa.gov.lb/en/pages/details/11"
     :statute/url-provenance :official-ppa-gov-lb
     :statute/enacted-date "2021-07-19"
     :statute/last-revised-date "2023-04-19"
     :statute/retrieved-at "2026-07-22"
     :statute/topic #{:public-procurement :corporate-governance}}
    {:statute/id "lbn.legislative-decree-11-67-commercial-registry"
     :statute/title "Legislative Decree No. 11/67 -- special Commercial Registry provisions (register of commercial establishments and the contracts affecting them), Article 4; general Commercial Registry supervision by the judiciary is separately set out in Article 23 of the Lebanese Code of Commerce"
     :statute/jurisdiction "LBN"
     :statute/kind :decree
     :statute/law-number "Legislative Decree No. 11/67"
     :statute/url "https://www.justice.gov.lb/index.php/department-details/4/2"
     :statute/url-provenance :official-justice-gov-lb
     :statute/retrieved-at "2026-07-22"
     :statute/topic #{:corporate-governance :incorporation}}
    {:statute/id "lbn.labor-law-1946"
     :statute/title "Labor Law (Lebanon) -- قانون العمل"
     :statute/jurisdiction "LBN"
     :statute/kind :law
     :statute/law-number "Law of 23 September 1946, as last amended in this session's confirmed copy by Law No. 207 of 26 May 2000"
     :statute/url "http://web.archive.org/web/20250524023106/https://www.labor.gov.lb/Temp/Files/574b61dd-1233-4507-9da1-d4a3e3a6129a.pdf"
     :statute/url-provenance :wayback-machine-mirror-of-labor-gov-lb
     :statute/enacted-date "1946-09-23"
     :statute/last-revised-date "2000-05-26"
     :statute/retrieved-at "2026-07-22"
     :statute/topic #{:labor}}
    {:statute/id "lbn.investment-law-360-2001"
     :statute/title "Investment Law No. 360 -- establishes the framework administered by the Investment Development Authority of Lebanon (IDAL), under the guardianship of the Presidency of the Council of Ministers"
     :statute/jurisdiction "LBN"
     :statute/kind :law
     :statute/law-number "Law No. 360 (2001)"
     :statute/url "https://investinlebanon.gov.lb/en/about_us"
     :statute/url-provenance :official-investinlebanon-gov-lb
     :statute/enacted-date "2001"
     :statute/retrieved-at "2026-07-22"
     :statute/topic #{:foreign-investment}}]})

(defn spec-basis [iso3] (get catalog iso3))

(defn coverage
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-lbn statute.facts Wave 0 (ADR-2607141700): "
                 (count (get catalog "LBN")) " LBN statutes seeded with "
                 "ppa.gov.lb/justice.gov.lb/investinlebanon.gov.lb citations "
                 "(plus one Wayback Machine mirror for labor.gov.lb, disclosed "
                 "via :statute/url-provenance, because the live site returned a "
                 "Cloudflare bot-detection challenge this session). Extend "
                 "`statute.facts/catalog`, never fabricate a law-id or URL.")})))

(defn by-topic [iso3 topic]
  (filterv #(contains? (:statute/topic %) topic) (spec-basis iso3)))
