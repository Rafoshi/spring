# Project

Monorepo com microsserviços Spring Boot, organizados em hexagonal/ports-and-adapters, cada um com seu próprio banco SQLite.

## Stack

- **Java 26** / **Spring Boot 4.1.0**
- **SQLite** como banco de dados (um arquivo por serviço, em `<serviço>/data/`)
- **Flyway** para migrações de schema (Hibernate roda em modo `validate`, nunca altera o schema)
- **MapStruct** para mapeamento entre camadas (DTO ↔ domínio ↔ entidade)
- **Lombok** para getters/setters e injeção via construtor (`@RequiredArgsConstructor`)
- **springdoc-openapi** (Swagger UI em `/swagger-ui.html`)
- **Docker** / **Docker Compose** para build e execução
- Arquitetura **hexagonal (ports & adapters)**, organizada por camada e depois por domínio de negócio

## Serviços

| Serviço         | Porta | Pasta            | Banco                          |
|-----------------|-------|------------------|---------------------------------|
| `user-service`  | 8080  | `user-service/`  | `user-service/data/mydb.sqlite` |
| `songs-service` | 8081  | `songs-service/` | `songs-service/data/songs.sqlite` |

Cada serviço tem seu próprio `Dockerfile`, `pom.xml` e `CLAUDE.md` com detalhes específicos de arquitetura.

## Como rodar

Suba os dois serviços:

```bash
docker compose up --build
```

Ou suba só um deles, se não precisar do outro:

```bash
docker compose up --build user-service
# ou
docker compose up --build songs-service
```

Os dados ficam persistidos em `user-service/data/` e `songs-service/data/` (montados como volume), sobrevivendo a `docker compose down`.

Para rodar localmente sem Docker (Maven) em geral:

```bash
cd user-service && ./mvnw spring-boot:run
cd songs-service && ./mvnw spring-boot:run
```
