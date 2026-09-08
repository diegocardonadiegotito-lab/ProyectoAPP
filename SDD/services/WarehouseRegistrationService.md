# WarehouseRegistrationService

## 1. Responsibility

Handles the registration of Marketplace-owned warehouses (as opposed to Seller-owned warehouses, handled by `SellerWarehouseService`).

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `registerMarketplaceWarehouse(administrator, data)` | `Administrator`, data | `Warehouse` (`warehouseType = Marketplace`, `owner = null`) | executed by `Administrator` |

## 3. Applied business rules

- DOMAIN 4: distinction between Marketplace warehouses (no owning Seller) and Seller warehouses.

## 4. Dependencies (Output Ports)

- `WarehouseRepositoryPort`: `save`.
