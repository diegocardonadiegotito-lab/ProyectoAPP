# ReportingOrdersService

## 1. Responsibility

Read-only consolidated view of orders across the platform, for the `Supervisor` role (OBJ-12).

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `listConsolidatedOrders(supervisor)` | `Supervisor` | `List<Order>` (read-only view) | — |

## 3. Applied business rules

- OBJ-12: consolidate administrative information for querying.
- `Supervisor` never receives write access to `Order`.

## 4. Dependencies (Output Ports)

- `ReportQueryPort`.
