# SellerInventoryService

## 1. Responsibility

Seller-facing entry point for registering inbound inventory on their own products. Thin facade over `InventoryStockService.registerInbound()`.

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `registerInventoryInbound(seller, item, quantity)` | `Seller`, `InventoryItem`, quantity | `InventoryMovement` | `item.product.seller == seller`; delegates to `InventoryStockService.registerInbound(seller, item, quantity)` |

## 3. Applied business rules

- BR-03: a seller only registers inventory for their own products.
- Responsibility Matrix (section 12): "Administración Inventario" → Seller and LogisticsOperator (not Administrator — see correction in `InventoryStockService.md`).

## 4. Dependencies (Output Ports)

- `ProductRepositoryPort`: `findById` (ownership check).
- Internally depends on `InventoryStockService`.
