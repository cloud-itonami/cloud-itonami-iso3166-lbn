(ns marketentry.governor
  "Market-Entry Compliance Governor -- the independent compliance layer
  that earns the MarketEntry-LLM the right to commit. The LLM has no
  notion of jurisdictional procurement law, whether a bidder's
  beneficial-ownership declaration is actually on file, whether a
  claimed engagement fee actually equals base + months x rate, whether
  a Commercial Registry number has been verified for a filing that
  requires it, or when a draft stops being a draft and becomes a
  real-world portal submission, so this MUST be a separate system able
  to *reject* a proposal and fall back to HOLD.

  `:itonami.blueprint/governor` is `:market-entry-compliance-governor`
  (shared family keyword on blueprints; this is the first *running*
  implementation of that governor for the iso3166 family).

  This blueprint's own text (docs/business-model.md Trust Controls:
  'any actual portal registration or filing submission requires
  Market-Entry Compliance Governor clearance and always escalates to
  human sign-off'; 'a false or fabricated regulatory-requirement claim
  is a HARD hold') names exactly the checks below.

  Seven checks, in priority order, ALL HARD violations: a human
  approver CANNOT override them. The confidence/actuation gate is
  SOFT: it asks a human to look (low confidence / actuation), and the
  human may approve -- but see `marketentry.phase`: for `:stake
  :actuation/draft-filing`/`:actuation/submit-filing` NO phase ever
  allows auto-commit either. Two independent layers agree that
  actuation is always a human call.

    1. Spec-basis                  -- did the jurisdiction proposal cite
                                       an OFFICIAL source
                                       (`marketentry.facts`), or invent
                                       one?
    2. Evidence incomplete         -- for `:filing/draft`/
                                       `:filing/submit`, has the
                                       jurisdiction actually been
                                       assessed with a full evidence
                                       checklist on file?
    3. Beneficial-ownership
       undisclosed                   -- for `:filing/submit`, when the
                                       engagement declares
                                       `:requires-beneficial-ownership-
                                       declaration? true` (near-
                                       universal for Lebanese public
                                       procurement per this blueprint's
                                       own text), INDEPENDENTLY verify
                                       `:beneficial-ownership-declared?`
                                       is true. FLAGSHIP genuinely new
                                       check for the iso3166 family
                                       (grep-verified absent as a
                                       governor check function name
                                       among the sibling repos studied
                                       this session -- egy/are/jor/srb/
                                       svn). Grounded in Lebanon's
                                       Public Procurement Law No. 244 of
                                       19 July 2021, as amended by Law
                                       No. 309 of 19 April 2023: the
                                       Public Procurement Authority's
                                       own Memorandum No. 3/PPA/2026
                                       (issued 21 January 2026, fetched
                                       and read from ppa.gov.lb this
                                       session) states that amended
                                       Article 7 makes declaration of
                                       beneficial/economic-rights
                                       owners 'down to the final degree
                                       of ownership' a condition of
                                       participation for EVERY bidder,
                                       REQUIRES AUTOMATIC EXCLUSION
                                       (استبعاد تلقائي) of any bidder
                                       who has not filed the
                                       declaration, and states that
                                       failing to exclude such a bidder
                                       is an explicit violation of the
                                       law's own text that exposes any
                                       resulting award to legal
                                       challenge and annulment. This is
                                       a HARDER legal mandate than a
                                       typical 'near-universal
                                       requirement' -- the statute
                                       itself forbids discretion here.
    4. Engagement fee mismatch     -- for `:filing/submit`,
                                       INDEPENDENTLY recompute whether
                                       the engagement's own `:claimed-
                                       fee` equals `base-fee +
                                       monthly-rate x monitoring-
                                       months` -- honest reapplication
                                       of the ground-truth-recompute
                                       discipline sibling actors use.
    5. Commercial-registry (CR)
       unverified                    -- for `:filing/submit`, when the
                                       engagement declares
                                       `:requires-cr? true`,
                                       INDEPENDENTLY check
                                       `:cr-verified?`. CONDITIONAL on
                                       the engagement's own ground
                                       truth. Grounded in the Commercial
                                       Registry (Sijil Tijari, السجل
                                       التجاري), administered by the
                                       Ministry of Justice per Article
                                       23 of the Lebanese Code of
                                       Commerce (general register) and
                                       Legislative Decree No. 11/67
                                       Article 4 (special register for
                                       commercial establishments and
                                       the contracts affecting them),
                                       both fetched and read from
                                       justice.gov.lb this session.
    6. Confidence floor / actuation
       gate                          -- LLM confidence below threshold,
                                       OR the op is `:filing/draft`/
                                       `:filing/submit` (REAL acts)
                                       -> escalate.

  Two more guards, double-draft/double-submit prevention, are enforced
  off dedicated `:drafted?`/`:submitted?` facts (never a `:status`
  value)."
  (:require [marketentry.facts :as facts]
            [marketentry.registry :as registry]
            [marketentry.store :as store]))

(def confidence-floor 0.6)

(def high-stakes
  "Stakes grave enough to always require a human, even when clean.
  Drafting a real portal package and submitting a real portal
  registration are the two real-world actuation events this actor
  performs."
  #{:actuation/draft-filing :actuation/submit-filing})

;; ----------------------------- checks -----------------------------

(defn- spec-basis-violations
  "A `:jurisdiction/assess` (or `:filing/draft`/`:filing/submit`)
  proposal with no spec-basis citation is a HARD violation -- never
  invent a jurisdiction's market-entry requirements."
  [{:keys [op]} proposal]
  (when (contains? #{:jurisdiction/assess :filing/draft :filing/submit} op)
    (let [value (:value proposal)]
      (when (or (empty? (:cites proposal))
                (and (contains? value :spec-basis) (nil? (:spec-basis value))))
        [{:rule :no-spec-basis
          :detail "公式spec-basisの引用が無い提案は法域要件として扱えない"}]))))

(defn- evidence-incomplete-violations
  "For `:filing/draft`/`:filing/submit`, the jurisdiction's required
  registration evidence must actually be satisfied."
  [{:keys [op subject]} st]
  (when (contains? #{:filing/draft :filing/submit} op)
    (let [e (store/engagement st subject)
          assessment (store/assessment-of st subject)]
      (when-not (and assessment
                     (facts/required-evidence-satisfied?
                      (:jurisdiction e) (:checklist assessment)))
        [{:rule :evidence-incomplete
          :detail "法域の必要書類(CR/受益者情報申告/PPAプラットフォーム登録/代理人確認等)が充足していない状態での提案"}]))))

(defn- beneficial-ownership-undisclosed-violations
  "For `:filing/submit`, when the engagement declares
  `:requires-beneficial-ownership-declaration? true`, INDEPENDENTLY
  verify `:beneficial-ownership-declared?` is true -- the flagship
  genuinely new check this vertical adds. CONDITIONAL on the
  engagement's own `:requires-beneficial-ownership-declaration?`
  ground truth, but per Public Procurement Law No. 244/2021 Art. 7 (as
  amended by Law No. 309/2023) this condition applies to every bidder
  in Lebanese public procurement -- the statute itself mandates
  automatic exclusion of a non-declaring bidder, not merely a
  discretionary hold."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (let [e (store/engagement st subject)]
      (when (and (true? (:requires-beneficial-ownership-declaration? e))
                 (not (true? (:beneficial-ownership-declared? e))))
        [{:rule :beneficial-ownership-undisclosed
          :detail (str subject " は受益者(経済的権利保有者)情報の申告を要するが未申告 -- "
                       "公共調達法244号/2021(309号/2023改正)第7条により自動的に排除対象、"
                       "提出提案は進められない")}]))))

(defn- engagement-fee-mismatch-violations
  "For `:filing/submit`, INDEPENDENTLY recompute whether the
  engagement's own claimed fee equals base + months x rate."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (let [e (store/engagement st subject)]
      (when-not (registry/engagement-fee-matches-claim? e)
        [{:rule :engagement-fee-mismatch
          :detail (str subject " の申告手数料(" (:claimed-fee e)
                      ")が独立再計算値(" (registry/compute-engagement-fee e) ")と一致しない")}]))))

(defn- cr-unverified-violations
  "For `:filing/submit`, when the engagement declares
  `:requires-cr? true`, INDEPENDENTLY check
  `:cr-verified?` -- CONDITIONAL on the engagement's own
  ground truth."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (let [e (store/engagement st subject)]
      (when (and (true? (:requires-cr? e))
                 (not (true? (:cr-verified? e))))
        [{:rule :cr-unverified
          :detail (str subject " はCR(商業登記/Sijil Tijari)確認を要するが未確認 -- 提出提案は進められない")}]))))

(defn- already-drafted-violations
  "For `:filing/draft`, refuses to draft the SAME engagement twice."
  [{:keys [op subject]} st]
  (when (= op :filing/draft)
    (when (store/engagement-already-drafted? st subject)
      [{:rule :already-drafted
        :detail (str subject " は既にドラフト済み")}])))

(defn- already-submitted-violations
  "For `:filing/submit`, refuses to submit the SAME engagement twice."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (when (store/engagement-already-submitted? st subject)
      [{:rule :already-submitted
        :detail (str subject " は既に提出済み")}])))

(defn check
  "Censors a MarketEntry-LLM proposal against the governor rules.
  Returns {:ok? bool :violations [..] :confidence c :escalate? bool
  :high-stakes? bool :hard? bool}."
  [request _context proposal st]
  (let [hard (into []
                   (concat (spec-basis-violations request proposal)
                           (evidence-incomplete-violations request st)
                           (beneficial-ownership-undisclosed-violations request st)
                           (engagement-fee-mismatch-violations request st)
                           (cr-unverified-violations request st)
                           (already-drafted-violations request st)
                           (already-submitted-violations request st)))
        conf (:confidence proposal 0.0)
        low? (< conf confidence-floor)
        stakes? (boolean (high-stakes (:stake proposal)))
        hard? (boolean (seq hard))]
    {:ok?          (and (not hard?) (not low?) (not stakes?))
     :violations   hard
     :confidence   conf
     :hard?        hard?
     :escalate?    (and (not hard?) (or low? stakes?))
     :high-stakes? stakes?}))

(defn hold-fact
  "The audit fact written when a proposal is rejected (HOLD)."
  [request context verdict]
  {:t          :governor-hold
   :op         (:op request)
   :actor      (:actor-id context)
   :subject    (:subject request)
   :disposition :hold
   :basis      (mapv :rule (:violations verdict))
   :violations (:violations verdict)
   :confidence (:confidence verdict)})
