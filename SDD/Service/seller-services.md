# Seller Services (Seller)

## 1. Responsibility

Manages the onboarding and maintenance of sellers, and the operations a `Seller` performs on their own products and warehouses. A seller never self-registers: they are always created by an `Administrator` (DOMAIN 3).

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `registerSeller(administrator, data)` | `Administrator`, seller data | `Seller` (with `registeredBy = administrator`) | executed by `Administrator` |
| `registerInitialWarehouse(seller, data)` | `Seller`, data | `Warehouse` (`warehouseType = Seller`) | active seller |
| `publishProduct(seller, product)` | `Seller`, `Product` | void | `product.seller == seller` |
| `registerInventoryInbound(seller, item, quantity)` | `Seller`, `InventoryItem`, quantity | `InventoryMovement` | `item.product.seller == seller` |

## 3. Applied business rules

- DOMAIN 3: seller registration is exclusive to the `Administrator`.
- BR-03: a seller only operates on their own products/warehouses (`product.seller == this`).
- Responsibility matrix: "Seller Registration" → Admin only; "Product Registration" and "Inventory Administration" → Seller (and Logistics Operator for inventory).

## 4. Dependencies (Output Ports)

- `SellerRepositoryPort`: `save`, `findById`, `listByAdministrator`.
- `WarehouseRepositoryPort`: `save`, `listBySeller`.
- `ProductRepositoryPort`: `save`, `listBySeller`.
- `InventoryMovementRepositoryPort`: `save`.
