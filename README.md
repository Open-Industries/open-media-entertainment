# Open Media & Entertainment — Reference Media Core Platform

A **standard, generic media and entertainment core platform** that any organization in this vertical
can deploy and adapt: built as **microservices and serverless functions** on
**Java 21 + Quarkus**, aligned with **The Twelve-Factor App**, documented under the ISO
architecture and quality standards, and aligned with the domain standards of the
vertical (see the traceability matrix). Everything is *aligned to* the cited standards —
**no certification, conformance or regulatory-compliance claim is made**. All data is
**synthetic**.

> Reference implementation: small, complete and runnable. The rules that matter in this
> domain are enforced in code and covered by tests — start with the invariant:
> distribution uses against a title's licensed units never exceed the license — a use that would overrun the licensed units is rejected (422) and records nothing; rights are never silently exceeded.

## Standards alignment

| Standard / framework | What it governs here | Where |
|---|---|---|
| ISO/IEC/IEEE **42010** | Architecture description | `docs/architecture/architecture-description.md` |
| ISO/IEC **25010** (SQuaRE) | Product quality model and scenarios | `docs/architecture/quality-attributes.md` |
| ISO/IEC/IEEE **15288 & 12207** | Life-cycle processes | `docs/lifecycle/software-lifecycle.md` |
| ISO/IEC **27001 & 27002** | Information security | `docs/security/` |
| **The Twelve-Factor App** | Cloud-native methodology | `docs/architecture/twelve-factor.md` |
| SMPTE IMF (ST 2067) | Interoperable Master Format | `docs/domain/domain-standards.md` mapping (master/version posture) |
| IPTC | Media metadata | Catalogue metadata posture |
| EIDR | Entertainment Identifier Registry | Content identifier posture at the boundary |
| DVB / ATSC | Broadcast distribution | Boundary: transmission standards stay with broadcast systems |
| WCAG 2.2 | Accessibility (captions/audio description services) | Accessibility-service posture for catalogues |

Full traceability: `docs/compliance/standards-traceability.md`.

## The platform at a glance

### Microservices (Quarkus, `services/`)

| Service | Port | Alignment | Responsibility |
|---|---|---|---|
| `content-service` | 8081 | IMF master posture; EIDR identifier discipline | Content asset registry; the registry code is masked at registration. |
| `rights-service` | 8082 | Rights management discipline | Guarded licensed-units accounts per title and the distribution uses against them. |
| `release-rules-service` | 8083 | Release policy as auditable rules | Decision rules binding territory groups to release-volume and piracy-incident envelopes. |
| `programming-service` | 8084 | Schedule master data | Programming slot catalogue for schedules. |
| `notification-service` | 8085 | Platform support (shared pattern) | Channel-agnostic dispatch of domain notifications from events. |

### Serverless functions (Quarkus Funqy + Knative, `functions/`)

| Function | Trigger | Responsibility |
|---|---|---|
| `piracy-risk-scorer` | Event / HTTP | Scores piracy risk signals (leaks, torrent activity, social spread) with an auditable rationale. |
| `audience-sla-evaluator` | Event / HTTP | Compares buffering events and completion rate against title targets; one finding per breach. |

## Design principles

1. **One bounded context per service**, identical hexagonal layout everywhere.
2. **The domain invariant is code, not prose** — distribution uses against a title's licensed units never exceed the license — a use that would overrun the licensed units is rejected (422) and records nothing; rights are never silently exceeded.
3. **Privacy by design** — Audience personal data stays in adopter platforms; this core manages content, rights and schedules. Content identifiers are internal or registry codes; all data is synthetic.
4. **Database per service, API-first (OpenAPI), events as CloudEvents.**
5. **Adapt by configuration** — rules, thresholds, tariffs/rates and providers are data
   and configuration, not code forks (see *Adaptation guide*).

## Quick start

Prerequisites: Java 21, Maven 3.9+, optionally Docker/Podman and Knative/OpenShift.

```bash
mvn verify                                    # build and test everything
mvn -pl services/content-service quarkus:dev
docker compose up -d                          # local PostgreSQL, Kafka, Keycloak
```

## Deployment

- **Kubernetes / OpenShift**: `kubectl apply -k deploy/kubernetes/`.
- **Serverless**: Knative Services in `deploy/knative/` (scale to zero).
- **Local**: `docker-compose.yml` for dependencies; services run with `quarkus:dev`.

## Adaptation guide

| You want to… | Change only… |
|---|---|
| Adjust domain rules and thresholds | Rule data in the `release-rules-service` context and function configuration |
| Change the guarded capacity semantics | The `rights-service` context (invariant stays: never negative) |
| Plug your IdP / providers | OIDC at the gateway; provider adapters at the boundary |
| Replace in-memory persistence | The repository port's JPA adapter on the service's own database (`DB_KIND`, `DB_URL`) |

## Documentation map

- Architecture (42010): `docs/architecture/` · Decisions: `docs/adr/`
- Domain standards: `docs/domain/domain-standards.md`
- Quality (25010), Security (27001/27002), Life cycle (15288/12207), API, Data, Operations: under `docs/`

## License

Apache-2.0 — see `LICENSE`.
