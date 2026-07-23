# Business Model: Independent Public-Sector Market-Entry & Procurement Compliance Service — Lebanon

## Classification

- Repository: `cloud-itonami-iso3166-lbn`
- ISO 3166: `LBN` (Lebanon)
- Activity: public-procurement market-entry and ongoing regulatory-
  compliance navigation for an already-incorporated operator
- Social impact: [:lebanese-sme-market-access :public-spend-transparency :anti-corruption-beneficial-ownership-disclosure]

## Customer

- an already-incorporated `cloud-itonami-cofog-{code}` /
  `cloud-itonami-isco-{code}` / `cloud-itonami-unspsc-{segment}` /
  `cloud-itonami-{ISIC}` operator wanting to bid on a Lebanese
  public contract
- a foreign SME or civic-tech vendor entering the public sector in
  Lebanon for the first time
- a `cloud-itonami-M6910` client that has just completed incorporation and
  now needs public-sector market access

## Offer

- registration walkthrough for the Public Procurement Authority's
  (PPA / هيئة الشراء العام) central e-procurement platform, under
  Public Procurement Law No. 244 of 19 July 2021 as amended by Law
  No. 309 of 19 April 2023
- business/registration checklist: Commercial Registry (Sijil Tijari
  / السجل التجاري) entry with the Ministry of Justice; beneficial-
  ownership declaration (Form M18, Ministry of Finance) required of
  every bidder under Article 7 of the Public Procurement Law (as
  amended)
- ongoing regulatory-change monitoring subscription -- this is a
  particularly live requirement in Lebanon: the PPA has issued
  binding memoranda as recently as January and March 2026 during this
  session's research, so "monitoring" is not a boilerplate promise
  here
- compliance-audit export package for the client's own records

## Revenue

- per-engagement market-entry fee (one-time registration + checklist
  completion)
- recurring regulatory-change monitoring subscription
- compliance-audit export package

## Trust Controls

- any actual portal registration or filing submission requires
  Market-Entry Compliance Governor clearance and always escalates to
  human sign-off (`:filing/submit` is never automated at any phase)
- a false or fabricated regulatory-requirement claim is a HARD hold that
  cannot be overridden by human approval alone — it must be corrected
  against a cited official source first
- an undisclosed beneficial-ownership declaration is a HARD hold: the
  Public Procurement Authority's own Memorandum No. 3/PPA/2026 (21
  January 2026) confirms that amended Article 7 of Law No. 244/2021
  makes this a condition for EVERY bidder and REQUIRES AUTOMATIC
  EXCLUSION of a non-declaring bidder -- a human approver cannot waive
  a statutory automatic-exclusion rule
- this service does **not** provide legal or tax advice; characterization
  and filing on the client's behalf beyond checklist/draft assistance
  routes to Lebanese-licensed counsel or a registered agent
- every requirement cites the official portal or regulation, never
  invented

## Institutional-instability disclosure (read before relying on any single source)

Lebanon's regulatory institutions have been under sustained strain
since the 2019 financial crisis, and this session found direct,
current evidence of that strain rather than assuming it from
memory:

- The Ministry of Finance's own website (`finance.gov.lb`) refused
  connections outright ("Connection refused" on HTTPS, "Host is down"
  on HTTP) when fetched this session; even the Internet Archive's own
  2022 crawl of it recorded a WAF "Request Rejected" page, and its
  2024-2026 crawls are almost entirely broken SharePoint term-store
  error pages rather than real content. We could NOT independently
  confirm a specific taxpayer-ID (TIN) numbering scheme from a live
  or archived Ministry of Finance page this session -- this is an
  honest gap, not an invented figure. The Ministry of Justice's own
  Commercial Registry guidance page (fetched and read this session)
  does confirm that new registrants must pay registration fees to
  the Ministry of Finance (or its governorate finance offices) as
  part of the Sijil Tijari workflow, and the PPA's own Memorandum
  No. 3/PPA/2026 references a Ministry-of-Finance-issued beneficial-
  ownership declaration form ("Form M18") -- so the Ministry's
  regulatory ROLE is confirmed even though its own web presence could
  not be.
- The Ministry of Labor's website (`labor.gov.lb`) returned a live
  Cloudflare "Attention Required!" bot-detection challenge page when
  fetched this session. Per this project's hard safety rules, no
  attempt was made to bypass it; the Labor Law citation in
  `src/statute/facts.cljc` is instead sourced from an Internet
  Archive Wayback Machine mirror of the Ministry's own hosted PDF
  (`Temp/Files/574b61dd-...pdf`), which was itself crawled and
  archived repeatedly through 2025 -- this is disclosed explicitly
  via `:statute/url-provenance :wayback-machine-mirror-of-labor-gov-lb`.
- By contrast, the Public Procurement Authority (`ppa.gov.lb`), the
  Ministry of Justice / Commercial Registry (`justice.gov.lb`,
  `cr.justice.gov.lb`), and IDAL (`investinlebanon.gov.lb`) were all
  reachable directly and returned current content (the PPA site in
  particular shows memoranda dated as recently as March 2026),
  confirming these institutions ARE genuinely operative today -- the
  degraded state of some ministries' web infrastructure is real, but
  does not mean every Lebanese institution is dysfunctional. Treat
  each citation on its own evidence, not as a blanket assumption in
  either direction.

## Boundary with adjacent actors (read before forking)

- **`com-etzhayyim-ooyake`** (etzhayyim/root): read-only civic-wayfinding
  mirror of government structure, non-commercial, barred from acting as
  or for the government (G3 impersonation ban). This blueprint is
  commercial and never claims to be an official channel.
- **`matsurigoto`** (etzhayyim/root): sovereign e-government statecraft —
  literally the government, for etzhayyim's own covenant or an adopting
  nation-state. This blueprint is an independent operator the government
  contracts with or that bids into its procurement — never the
  government.
- **`com-etzhayyim-toritsugi`** (etzhayyim/root): guides a consenting
  INDIVIDUAL citizen through their OWN procedure, non-profit,
  donation-only. This blueprint's client is a business operator, not an
  individual citizen, and it is commercial.
- **`legal-entity.etzhayyim.com`**: read-only aggregated company-registry
  data, no execution. This blueprint executes (gated) registrations.
- **`cloud-itonami-M6910`**: helps a client BECOME a legal entity
  (incorporation, ISIC 6910) — a prior, different regulatory phase
  (company law). This blueprint assumes incorporation is already done and
  handles public-procurement market entry (a different regulatory domain).
- **`cloud-itonami-cofog-{code}`**: a jurisdiction-agnostic operator
  template for ONE public function. This blueprint is the orthogonal
  jurisdiction-specific axis — the two compose (fork a COFOG-function
  blueprint AND this one to operate in Lebanon).
