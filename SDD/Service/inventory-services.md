# Inventory Services

## 1. Responsibility

Manages distributed inventory: stock per product/warehouse and the movements that affect it, guaranteeing full traceability (BR-01) and the restriction that stock must never be negative (DOMAIN 6).

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `reserve(executingUser, item, quantity)` | `User`, `InventoryItem`, quantity | `InventoryMovement` (`type = Reservation`) | `quantity <= item.quantity` and the item is not damaged |
| `release(executingUser, item, quantity)` | `User`, `InventoryItem`, quantity | `InventoryMovement` (`type = Adjustment`) | a prior reservation exists |
| `registerInbound(executingUser, item, quantity)` | `User`, `InventoryItem`, quantity | `InventoryMovement` (`type = Inbound`) | executed by `Seller` or `Administrator` |
| `registerSaleOutbound(executingUser, item, quantity)` | `User`, `InventoryItem`, quantity | `InventoryMovement` (`type = Sale Outbound`) | `isAvailable(quantity) == true` |
| `isAvailable(item, quantity)` | `InventoryItem`, quantity | Boolean | delegates to `item.isAvailable()`, generates no movement |

## 3. Applied business rules

- DOMAIN 6: inventory is always linked to a `Product` and a `Warehouse`; **stock is never negative**.
- BR-01: every `InventoryMovement` records `executedBy: User` for traceability.
- Critical validation: it is not possible to reserve non-existent inventory or inventory marked as "Damaged".

## 4. Dependencies (Output Ports)

- `InventoryItemRepositoryPort`: `save`, `findByProductAndWarehouse`, `listByWarehouse`.
- `InventoryMovementRepositoryPort`: `save`, `listByItem`.
