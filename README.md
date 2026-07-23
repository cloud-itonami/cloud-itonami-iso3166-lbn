# cloud-itonami-iso3166-lbn

**`:implemented`** for **LBN** (Lebanon). Flagship `beneficial-ownership-undisclosed`, secondary `cr-unverified`.

```
clojure -M:dev:test
```

AGPL-3.0-or-later.

## Lebanon-specific grounding

- **Public Procurement Authority (PPA / هيئة الشراء العام)** -- Public
  Procurement Law No. 244 of 19 July 2021, as amended by Law No. 309
  of 19 April 2023 (https://www.ppa.gov.lb/). Confirmed live and
  actively operating into 2026 (memos dated as recently as March 2026
  were observed on the site this session).
- **Commercial Registry (Sijil Tijari / السجل التجاري)** -- administered
  by the Ministry of Justice (https://www.justice.gov.lb/), per Article
  23 of the Lebanese Code of Commerce and Legislative Decree No. 11/67
  Article 4. Lookup portal: http://cr.justice.gov.lb/.
- **IDAL (Investment Development Authority of Lebanon)** -- national
  investment promotion agency under Investment Law No. 360 (2001),
  guardianship authority is the Presidency of the Council of Ministers
  (https://investinlebanon.gov.lb/).
- **Labor Law** -- Law of 23 September 1946, as amended through Law
  No. 207 of 26 May 2000 (per the Ministry of Labor's own hosted PDF).

See `docs/business-model.md` for the full picture, including an
honest disclosure of what could NOT be independently confirmed this
session (the Ministry of Finance's own site was unreachable at fetch
time -- "Connection refused" / "Host is down" -- and the Ministry of
Labor's live site returned a Cloudflare bot-detection challenge, so
that citation is sourced via an Internet Archive Wayback Machine
mirror instead, disclosed as such in `src/statute/facts.cljc`).

## Culture catalog

Alongside the market-entry / statute catalogs, this repo carries a
**country-level regional-culture catalog** (ADR-2607171400 addendum 2,
`cloud-itonami-municipality-culture-catalog` Wave 1, in
`com-junkawasaki/root`) — national dishes, protected products, beverages,
crafts, festivals and heritage sites for Lebanon:

- `src/culture/facts.cljc` — the catalog, source of truth (keyed by
  uppercase ISO3, mirroring `statute.facts`).
- `schema/culture.edn` — DataScript schema.
- `data/culture-tx.edn` — derived DataScript tx-data (regenerated from
  the catalog, never hand-edited).

City-level counterparts live in the `cloud-itonami-municipality-*` repos.
Same provenance discipline as the compliance catalogs: every entry cites a
source URL that was actually fetched and read on `:culture/retrieved-at`;
summaries state only what the cited source confirms. An item not in
`culture.facts/catalog` has no spec-basis — never fabricate one.
