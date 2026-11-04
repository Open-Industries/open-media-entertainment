# Programming Service

Bounded context: **Programming Slots** — Programming slot catalogue for schedules.

Alignment: Schedule master data. Lifecycle transitions take no body. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8084` (`HTTP_PORT`) |
| Base path | `/api/v1/programming-slots` |
| Owns data | ProgrammingSlot records |

Run locally: `mvn -pl services/programming-service quarkus:dev`.
