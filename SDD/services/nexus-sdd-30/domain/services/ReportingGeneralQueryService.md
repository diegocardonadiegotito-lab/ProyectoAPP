# ReportingGeneralQueryService

## 1. Responsibility

General-purpose report query for the `Supervisor`, covering report types not already broken out into their own service (orders, inventory).

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `queryReport(supervisor, type)` | `Supervisor`, report type | `Report` | delegates to `supervisor.queryReport()` |

## 3. Applied business rules

- OBJ-12: consolidate administrative information for querying.
- The `Supervisor` overrides `getPermissions()` to expose read-only access, with no modification permissions.

## 4. Dependencies (Output Ports)

- `ReportQueryPort`.
