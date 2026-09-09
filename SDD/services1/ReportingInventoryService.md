# ReportingInventoryService

## 1. Responsibility

Read-only consolidated view of inventory across all warehouses, for the `Supervisor` role (OBJ-12).

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `listConsolidatedInventory(supervisor)` | `Supervisor` | `List<InventoryItem>` (read-only view) | — |

## 3. Applied business rules

- OBJ-12: consolidate administrative information for querying.

## 4. Dependencies (Output Ports)

- `ReportQueryPort`.
