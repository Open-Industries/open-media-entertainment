# Content Service

Bounded context: **Content Assets** — Content asset registry; the registry code is masked at registration.

Alignment: IMF master posture; EIDR identifier discipline. Lifecycle transitions take no body; secret identifiers,
where the domain has them, are masked at creation and never stored or returned in clear.
All data is synthetic.

| Item | Value |
|---|---|
| Port | `8081` (`HTTP_PORT`) |
| Base path | `/api/v1/content` |
| Owns data | ContentAsset records |
| Publishes | `content.created`, lifecycle events (see `contracts/asyncapi.yaml`) |

Run locally: `mvn -pl services/content-service quarkus:dev`.
