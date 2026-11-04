# Release Rules Service

Bounded context: **Release Rules** — Decision rules binding territory groups to release-volume and piracy-incident envelopes.

Alignment: Release policy as auditable rules. Decisions are `ALLOWED` / `DEGRADED` / `DENIED`, always
with the matched rule and a human-readable reason for audit. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8083` (`HTTP_PORT`) |
| Base path | `/api/v1/release-rules` |
| Owns data | ReleaseRule rules |
| Publishes | `release.activated` |

Run locally: `mvn -pl services/release-rules-service quarkus:dev`.
