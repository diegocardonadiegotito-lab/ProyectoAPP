# SellerWarehouseService

## 1. Responsibility

Handles the registration of a `Seller`'s own warehouse (as opposed to Marketplace-owned warehouses, handled by `WarehouseRegistrationService`).

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `registerInitialWarehouse(seller, data)` | `Seller`, data | `Warehouse` (`warehouseType = Seller`) | active seller |

## 3. Applied business rules

- DOMAIN 4: a warehouse is classified as belonging to a `Seller` when created through this service (`owner = seller`).
- Flow step 1 (section 6.1): the Administrator registers the seller and their first warehouse — this operation covers the warehouse leg of that step.

## 4. Dependencies (Output Ports)

- `WarehouseRepositoryPort`: `save`.
