# Services

One independently deployable Quarkus microservice per bounded context. Every service
follows the same hexagonal layout, exposes `/openapi`, `/q/health` and `/q/metrics`,
returns RFC 9457 problem details, and is configured only through environment variables
(Twelve-Factor). Lifecycle transitions take no body; `@Consumes` appears only on
endpoints that read one.

**Implementation order** (see `../docs/implementation-roadmap.md`):
Content Assets → Rights Windows → Release Rules → Open APIs.

| Service | Port | Alignment |
|---|---|---|
| `content-service` | 8081 | IMF master posture; EIDR identifier discipline |
| `rights-service` | 8082 | Rights management discipline |
| `release-rules-service` | 8083 | Release policy as auditable rules |
| `programming-service` | 8084 | Schedule master data |
| `notification-service` | 8085 | Platform support (shared pattern) |

Run the pilot service:

```bash
mvn -pl services/content-service quarkus:dev
# OpenAPI UI: http://localhost:8081/docs — health: http://localhost:8081/q/health
```
