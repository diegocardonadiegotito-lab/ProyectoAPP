# WarehouseInventoryQueryService

## 1. Responsibility

Read-only queries over a `Warehouse`: its classification and the inventory it holds.

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `queryInventory(warehouse)` | `Warehouse` | `List<InventoryItem>` | delegates to `warehouse.getInventory()` |
| `isMarketplaceWarehouse(warehouse)` | `Warehouse` | Boolean | delegates to `warehouse.isMarketplace()` |

## 3. Applied business rules

- DOMAIN 4: a warehouse can be left without an owning `Seller` if it belongs to the Marketplace, but it never ceases to exist because of that change (association, not composition).

## 4. Dependencies (Output Ports)

- `WarehouseRepositoryPort`: `findById`.
- `InventoryItemRepositoryPort`: `listByWarehouse`.
