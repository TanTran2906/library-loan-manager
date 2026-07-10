# CLAUDE.md

## Project

Library loan manager. Spring Boot 3 + MyBatis 3 + MySQL 8 + Thymeleaf + hand-written CSS.
Runs on localhost:8080. Database `library` on a shared local MySQL instance —
NEVER touch any other schema on that server.
Four screens only: hero (/), list (/books), detail (/books/{id}), form (/books/new, /books/{id}/edit).
Anything else is out of scope — say so instead of building it.

## Backend rules

- Layering: Controller → Service → Mapper. A Mapper interface must never appear in a Controller.
- Domain objects never reach the template. Controllers pass DTOs / view models only.
- @Transactional lives on the service layer, nowhere else.
- Every schema change is a NEW Flyway migration. NEVER edit a migration that has run.
- Never run DDL by hand, never suggest I run it in Workbench.
- No dynamic SQL string concatenation. Use MyBatis <if>/<where> in the XML mapper.
- Detail screen: fetch book + loans in ONE query with a nested resultMap.
  Do not loop over loans calling a second mapper method. If you write a loop that
  calls the DB, stop and tell me.
- Search: parameterised only. Never build a LIKE clause by concatenating user input.

## Frontend rules — no CSS framework

- There is no Tailwind, no shadcn, no Bootstrap. Hand-written CSS in
  src/main/resources/static/css/ only. Three files, in @layer order:
  reset → tokens → primitives → app.
- NEVER invent a new CSS class. Use only classes defined in primitives.css.
  If a primitive is missing, STOP and ask before creating one.
- Every value comes from a CSS custom property in tokens.css.
  No raw hex, no raw px, no raw ms literals anywhere else.
- No !important. Ever.
- Prefer native elements: <dialog>, <details>, the popover attribute,
  :has(), container queries, color-mix(). Do not hand-roll a JS dropdown.

## Design rules

- FORBIDDEN fonts: Inter, Roboto, Open Sans, Lato, Arial, system-ui stacks.
- Hero display ≥ 3x body. List/detail/form: 1.5–1.8x. Drama chỉ ở "/" Weight extremes (300 vs 700) chỉ ở hero. Trong table không bao giờ < 400.
- ONE dominant colour + ONE accent. No purple-to-blue gradient on white.
- Surface elevation via 4 CSS vars (:-surface-0 :. :-surface-3).
- Separate surfaces by contrast. Rows divided by 1px hairline only. No border around a card. No zebra striping.
- 8px spacing rhythm. Input font-size := 16px (mobile auto-zoom).
- No three-centered-rounded-card hero. No emoji in the UI.
- Server-rendered: no loading state. Every list screen ships empty + error. Every form ships field-level errors.

## Design spec (locked 2026-07-10, Refero audit)

Direction: editorial. A ledger, not a dashboard.

Fonts (self-host woff2):

- Display serif: Fraunces — hero + page titles only.
- Body/UI/table: IBM Plex Sans — has real tabular figures.

Type scale (tokens.css):
--text-xs 0.75rem timestamps, muted meta
--text-sm 0.8125rem TABLE BODY default
--text-base 1rem form inputs, detail body
--text-lg 1.5rem list/section titles
--text-xl 2rem detail page title
--text-display 3.5rem HERO ONLY

Colour: one warm-neutral ink (not #000) + one accent (CTA + focus ring only).
Warm-paper neutrals, not cool grey. Semantic colours are badge/text only:
--state-available (green) · --state-on-loan (neutral) · --state-overdue (red).
Red appears only when something is wrong.

Numbers: font-variant-numeric: tabular-nums, right-aligned. Text left-aligned.
Never centre a data column. ISBN uses tabular-nums, not a mono family.

Spacing: 8px base, 4px sub-grid inside tables.
Row 40px · header row 32px · cell pad 8px/12px · toolbar 48px.
Block gap 24–32px in app screens. Hero sections 64–96px.

Surfaces:
--surface-0 app canvas (warm off-white) · --surface-1 table/card
--surface-2 hover row · --surface-3 sunken: table header, toolbar
Exactly one shadow token, floating layers only (<dialog>, popover, toast).

Hero (/): the book table IS the hero (Attio pattern). One positioning line,
three stats, one CTA. This is the only screen with drama.

List (/books): search box + one primary "Add book". No filter rail, no sort,
no row menu, no pagination footer. Columns: title (link) + author (muted,
second line) · status badge · available/total (right-aligned) · isbn (muted).
Hairline dividers, hover highlight, sticky header.

Detail (/books/{id}): serif title + author + status badge. Facts column.
Loans as a compact sub-table reusing the same table primitives.

Form: single column, left-aligned labels, 16px inputs.
Field-level errors: red text + red field border, message under the field.
Cancel (ghost, left) · Save (filled accent, right).

Never: zebra stripes · box around a row/card · shadow to fake depth ·
hero type inside app screens · centred numeric columns · decorative colour ·
icon-button row per row.

## Verify — mandatory after EVERY UI change

- Use playwright mcp to open http::/localhost:8080 and screenshot at 375 / 768 / 1440.
- Compare against the reference in refs/ and fix only the issues you can name.
- Never self-review by re-reading the code. If you did not screenshot it, it is not verified.

## Anti-patterns (never do)

- ddl-auto / hbm2ddl (we do not use JPA at all)
- SELECT \* in a mapper XML
- Business logic inside a Thymeleaf template
- git commit / git push on my behalf — I commit by hand
- font-size < 11px · ALL CAPS labels · mid-sentence bolding

## References

refs/hero.png · refs/list.png · refs/detail.png (pulled from Refero)
