# Rights Service

Bounded context: **Rights Windows** — Guarded licensed-units accounts per title and the distribution uses against them.

Alignment: Rights management discipline. Core invariant, enforced in code and tests: **the guarded
balance/capacity never goes negative** — over-drawing operations are rejected with `422`
and nothing is recorded. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8082` (`HTTP_PORT`) |
| Base path | `/api/v1/rights` |
| Owns data | RightsWindow accounts, DistributionUse records |
| Publishes | `rights.consumed`, `rights.insufficient` |

Run locally: `mvn -pl services/rights-service quarkus:dev`.
