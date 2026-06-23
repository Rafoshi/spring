# Roadmap — `health-checker-service/` (assíncrono com polling)

## Fase 0 — Esqueleto do serviço
- Novo módulo Maven no monorepo, mesmo padrão dos outros (`pom.xml`, `Dockerfile`, hexagonal layering conforme `CLAUDE.md`).
- Porta nova (ex: 8082), sem SQLite necessariamente — pode começar 100% em memória.
- Endpoints:
  - `POST /checks` → recebe `{ "urls": [...] }`, retorna `{ "id": "<uuid>", "status": "PENDING" }` (202 Accepted).
  - `GET /checks/{id}` → retorna status agregado (`PENDING` / `RUNNING` / `DONE`) + resultados parciais ou finais por URL.

## Fase 1 — Thread cru + contador errado de propósito
- `POST /checks` dispara uma `Thread` por URL (sem pool), grava resultado num `Map<String, Result>` comum e incrementa um contador `int total/sucesso` compartilhado sem sincronização.
- Rodar com muitas URLs (ex: 200+ repetidas) e mostrar/registrar contagem inconsistente — prova visível do problema do JMM.
- Corrigir com `AtomicInteger` e/ou `synchronized` no acesso ao mapa de resultados; comparar.
- Job guardado num `ConcurrentHashMap<String, JobState>` (id → estado), já que múltiplas threads escrevem nele concorrentemente.

## Fase 2 — ExecutorService
- Trocar `Thread` cru por `ThreadPoolExecutor` configurado manualmente (core/max size, fila limitada, `RejectedExecutionHandler` — decidir o que acontece se o serviço receber check demais).
- Cada chamada a `POST /checks` submete N tasks ao pool; job tracker coleta `Future<Result>` por URL.
- `GET /checks/{id}` consulta o estado agregando os `Future`s ainda não completos como "RUNNING".

## Fase 3 — CompletableFuture
- Reescrever o pipeline: cada URL gera um `CompletableFuture<Result>` (usando `HttpClient` async do JDK), com timeout por requisição e `handle`/`exceptionally` para falhas.
- `CompletableFuture.allOf(...)` marca o job como `DONE` quando todas terminam, atualizando o estado no `ConcurrentHashMap`.
- Importante para o polling: o `GET` não deve bloquear esperando o `allOf` — só lê o snapshot atual do estado.

## Fase 4 — Virtual Threads
- Trocar o executor por `Executors.newVirtualThreadPerTaskExecutor()`.
- Teste de carga: disparar um `POST /checks` com milhares de URLs (ou repetir a mesma N vezes) e comparar throughput/memória/latência de polling contra a versão de platform threads (Fase 2/3).
- Registrar números (heap usado, tempo até `DONE`, número de threads do SO) — isso vai para o README comparativo.

## Fase 5 — Limpeza/encerramento dos jobs
- Sem isso o `ConcurrentHashMap` de jobs cresce sem limite: adicionar expiração simples (TTL por job, limpo por um `ScheduledExecutorService` ou ao acessar).
- Decidir o que `GET /checks/{id}` retorna para um id expirado/inexistente (404 vs. estado especial).

## Extra (Java 25)
- Revisitar a Fase 3/4 usando Structured Concurrency (preview) para o fan-out de um job: se uma URL "crítica" falhar, cancelar as demais checagens daquele job.

## Entregável
- README do módulo comparando as 4 fases com números reais (throughput, memória, threads ativas, latência de polling) — mesmo formato da ideia original, só que agora medido via chamadas HTTP reais em vez de CLI.
