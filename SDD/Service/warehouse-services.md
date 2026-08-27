# Warehouse Services (Warehouse)

## 1. Responsibility

Controls the lifecycle of warehouses (Marketplace or Seller) and exposes the query for their associated inventory (DOMAIN 4).

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `registerMarketplaceWarehouse(administrator, data)` | `Administrator`, data | `Warehouse` (`warehouseType = Marketplace`, `owner = null`) | executed by `Administrator` |
| `queryInventory(warehouse)` | `Warehouse` | `List<InventoryItem>` | delegates to `warehouse.getInventory()` |
| `isMarketplaceWarehouse(warehouse)` | `Warehouse` | Boolean | delegates to `warehouse.isMarketplace()` |

## 3. Applied business rules

- DOMAIN 4: a distinction is made between Marketplace warehouses (no owning `Seller`) and Seller warehouses.
- A warehouse can be left without an owning `Seller` if it belongs to the Marketplace, but it never ceases to exist because of that change (association, not composition).

## 4. Dependencies (Output Ports)

- `WarehouseRepositoryPort`: `save`, `findById`, `listBySeller`.
- `InventoryItemRepositoryPort`: `listByWarehouse`.
