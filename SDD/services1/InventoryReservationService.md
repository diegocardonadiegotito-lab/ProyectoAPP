# InventoryReservationService

## 1. Responsibility

Manages the reservation and release of inventory associated with in-progress orders — the "hold" mechanics distinct from permanent stock movements (inbound/outbound).

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `reserve(executingUser, item, quantity)` | `User`, `InventoryItem`, quantity | `InventoryMovement` (`type = Reservation`) | `quantity <= item.quantity` and the item is not damaged |
| `release(executingUser, item, quantity)` | `User`, `InventoryItem`, quantity | `InventoryMovement` (`type = Adjustment`) | a prior reservation exists |

## 3. Applied business rules

- Critical validation (section 11): it is not possible to reserve non-existent inventory or inventory marked as "Damaged".
- BR-01: every `InventoryMovement` records `executedBy: User` for traceability.
- Typically invoked by `OrderLifecycleService.confirmOrder()` for each order item.

## 4. Dependencies (Output Ports)

- `InventoryItemRepositoryPort`: `findByProductAndWarehouse`.
- `InventoryMovementRepositoryPort`: `save`.
