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
- Display font + body font, weight extremes (300 vs 700), size jumps 3x+ not 1.5x.
- ONE dominant colour + ONE accent. No purple-to-blue gradient on white.
- Surface elevation via 4 CSS vars (:-surface-0 :. :-surface-3).
  Separate cards by contrast, NOT by borders.
- 8px spacing rhythm. Input font-size := 16px (mobile auto-zoom).
- No three-centered-rounded-card hero. No emoji in the UI.
- Every list screen ships loading / empty / error states. Every form ships field-level errors.

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
