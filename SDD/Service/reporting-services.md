# Reporting Services (Supervisor)

## 1. Responsibility

Consolidates cross-cutting administrative information for querying (OBJ-12), through the read-only `Supervisor` profile. Does not modify state on any domain entity.

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `queryReport(supervisor, type)` | `Supervisor`, report type | `Report` | delegates to `supervisor.queryReport()` |
| `listConsolidatedOrders(supervisor)` | `Supervisor` | `List<Order>` (read-only view) | — |
| `listConsolidatedInventory(supervisor)` | `Supervisor` | `List<InventoryItem>` (read-only view) | — |

## 3. Applied business rules

- OBJ-12: consolidate administrative information for querying.
- The `Supervisor` overrides `getPermissions()` to expose read-only access, with no modification permissions — the service never exposes write operations to this role.

## 4. Dependencies (Output Ports)

- `ReportQueryPort`: read-only aggregated query over the other repositories.
