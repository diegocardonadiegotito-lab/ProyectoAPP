# SellerProductService

## 1. Responsibility

Seller-facing entry point for publishing one of their own products. Thin facade: validates ownership, then delegates the actual state transition to `ProductLifecycleService.publish()`.

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `publishProduct(seller, product)` | `Seller`, `Product` | void | `product.seller == seller`; delegates to `ProductLifecycleService.publish(product)` |

## 3. Applied business rules

- BR-03: a seller only operates on their own products (`product.seller == this`).
- Responsibility Matrix (section 12): "Registro Productos" → Seller only.

## 4. Dependencies (Output Ports)

- `ProductRepositoryPort`: `findById` (ownership check).
- Internally depends on `ProductLifecycleService`.
