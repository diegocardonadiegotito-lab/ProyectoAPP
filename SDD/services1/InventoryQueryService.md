# InventoryQueryService

## 1. Responsibility

Read-only inventory availability check. Generates no movement and changes no state.

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `isAvailable(item, quantity)` | `InventoryItem`, quantity | Boolean | delegates to `item.isAvailable()` |

## 3. Applied business rules

- DOMAIN 6: inventory is always linked to a `Product` and a `Warehouse`.

## 4. Dependencies (Output Ports)

- `InventoryItemRepositoryPort`: `findByProductAndWarehouse`.
