# InventoryStockService

## 1. Responsibility

Manages permanent stock changes: goods entering the warehouse (Inbound) and goods leaving due to a completed sale (Sale Outbound). Kept separate from `InventoryReservationService` because these movements are not "holds" — they permanently change the stored quantity.

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `registerInbound(executingUser, item, quantity)` | `User`, `InventoryItem`, quantity | `InventoryMovement` (`type = Inbound`) | executed by `Seller` or `LogisticsOperator` (per Responsibility Matrix, section 12 — **not** `Administrator`) |
| `registerSaleOutbound(executingUser, item, quantity)` | `User`, `InventoryItem`, quantity | `InventoryMovement` (`type = Sale Outbound`) | `isAvailable(quantity) == true`; typically system-triggered from `OrderLifecycleService`, but if manually invoked, restricted to `Seller` or `LogisticsOperator` |

## 3. Applied business rules

- DOMAIN 6: stock is never negative.
- BR-01: every `InventoryMovement` records `executedBy: User`.
- **Correction applied:** the Responsibility Matrix (section 12, "Administración Inventario") assigns this domain to `Seller` and `LogisticsOperator` only — `Administrator` is never a valid executor for inventory movements. The original `inventory-services.md` incorrectly listed `Administrator` as a valid executor for `registerInbound`; this has been fixed here.

## 4. Dependencies (Output Ports)

- `InventoryItemRepositoryPort`: `save`, `findByProductAndWarehouse`.
- `InventoryMovementRepositoryPort`: `save`.
