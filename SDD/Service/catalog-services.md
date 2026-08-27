# Catalog Services (Product)

## 1. Responsibility

Manages the product catalog (physical and digital) and its status transitions (DOMAIN 5), ensuring that only a `Seller` manages their own products.

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `registerProduct(seller, data)` | `Seller`, data | `Product` (`status = Suspended` until published) | active seller |
| `publish(product)` | `Product` | void | delegates to `product.publishProduct()`; requires authenticated `product.seller` |
| `suspend(product)` | `Product` | void | delegates to `product.suspend()` |
| `discontinue(product)` | `Product` | void | delegates to `product.discontinue()`; **irreversible** |
| `listPublicCatalog()` | — | `List<Product>` | only `status == Published` |
| `requiresShipping(product)` | `Product` | Boolean | delegates to `product.isPhysical()` |

## 3. Applied business rules

- DOMAIN 5: `ProductType` (Physical/Digital) determines whether it requires a `Shipment`; `ProductStatus` follows the Published/Suspended/Discontinued catalog.
- `discontinue()` is irreversible: the service exposes no operation to "reactivate" a discontinued product.
- BR-03: only the owning `Seller` can change the status of their product.

## 4. Dependencies (Output Ports)

- `ProductRepositoryPort`: `save`, `findById`, `listPublished`, `listBySeller`.
