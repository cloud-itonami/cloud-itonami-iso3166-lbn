(ns culture.facts
  "Country-level regional-culture catalog for Lebanon (LBN) -- national
  dishes, protected products, beverages, crafts, festivals and heritage
  sites, per ADR-2607171400 addendum 2 (cloud-itonami-municipality-
  culture-catalog Wave 1, in com-junkawasaki/root). Sibling namespace to
  `marketentry.facts` / `statute.facts` (ADR-2607141700); city-level
  counterparts live in the cloud-itonami-municipality-* repos.

  Catalog is keyed by UPPERCASE ISO3 (mirrors `statute.facts`); entries
  carry no :culture/municipality (that attribute is city-level only).

  Every entry cites a source URL that was actually fetched and read on
  :culture/retrieved-at -- never fabricated. Summaries state only what the
  cited source confirms. An item not in this table has NO spec-basis, full
  stop; extend `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of culture entries."
  {"LBN"
   [{:culture/id "lbn.dish.tabbouleh"
     :culture/name "Tabbouleh"
     :culture/country "LBN"
     :culture/kind :dish
     :culture/summary "Levantine salad of finely chopped parsley, bulgur, tomatoes, mint and onion, originally from the mountains of Lebanon and Syria; Lebanon celebrates a National Tabbouleh Day on the first Saturday of July."
     :culture/url "https://en.wikipedia.org/wiki/Tabbouleh"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "lbn.dish.kibbeh"
     :culture/name "Kibbeh"
     :culture/country "LBN"
     :culture/kind :dish
     :culture/summary "Dish of spiced lean ground meat and bulgur wheat, popular in the Arab world and the Levant in particular; considered a national dish of Lebanon and Syria."
     :culture/url "https://en.wikipedia.org/wiki/Kibbeh"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "lbn.dish.manakish"
     :culture/name "Manakish"
     :culture/country "LBN"
     :culture/kind :dish
     :culture/summary "Popular Levantine pastry of dough topped with za'atar, cheese or ground meat; in 2023 manakish received UNESCO recognition as an emblematic culinary practice in Lebanon."
     :culture/url "https://en.wikipedia.org/wiki/Manakish"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "lbn.beverage.arak"
     :culture/name "Arak"
     :culture/country "LBN"
     :culture/kind :beverage
     :culture/summary "Distilled Levantine spirit made from grapes and aniseed, often called the national drink of Lebanon, produced primarily in the Bekaa Valley from Marawi and Obaideh grapes."
     :culture/url "https://en.wikipedia.org/wiki/Arak_(drink)"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "lbn.beverage.lebanese-wine"
     :culture/name "Lebanese wine"
     :culture/country "LBN"
     :culture/kind :beverage
     :culture/summary "Lebanon has a 5,000-year winemaking history and is one of the oldest wine production regions in the world, with the Bekaa Valley as its primary wine-producing area and about 50 wineries today."
     :culture/url "https://en.wikipedia.org/wiki/Lebanese_wine"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "lbn.festival.baalbeck-international-festival"
     :culture/name "Baalbeck International Festival"
     :culture/country "LBN"
     :culture/kind :festival
     :culture/summary "Cultural festival held annually since 1955 in Baalbek in Lebanon's Beqaa Valley, staging classical music, dance, theater, opera and jazz among the ancient Roman ruins in July and August."
     :culture/url "https://en.wikipedia.org/wiki/Baalbeck_International_Festival"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "lbn.heritage.baalbek"
     :culture/name "Baalbek"
     :culture/country "LBN"
     :culture/kind :heritage
     :culture/summary "City in Lebanon's Beqaa Valley about 67 km northeast of Beirut, whose Roman temple complex was inscribed as a UNESCO World Heritage Site in 1984."
     :culture/url "https://en.wikipedia.org/wiki/Baalbek"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "lbn.heritage.byblos"
     :culture/name "Byblos"
     :culture/country "LBN"
     :culture/kind :heritage
     :culture/summary "Ancient city about 42 km north of Beirut, believed first settled between 8800 and 7000 BC and among the oldest continuously inhabited cities in the world; a UNESCO World Heritage Site inscribed in 1984."
     :culture/url "https://en.wikipedia.org/wiki/Byblos"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}]})

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
      :note (str "cloud-itonami-iso3166-lbn culture catalog "
                 "(ADR-2607171400 addendum 2, Wave 1): " (count (get catalog "LBN"))
                 " LBN entries, each with a fetched-and-read citation. "
                 "Extend `culture.facts/catalog`, never fabricate an id/url.")})))

(defn by-kind [iso3 kind]
  (filterv #(= (:culture/kind %) kind) (spec-basis iso3)))
