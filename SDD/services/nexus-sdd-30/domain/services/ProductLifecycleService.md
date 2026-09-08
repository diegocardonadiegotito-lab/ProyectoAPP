# ProductLifecycleService

## 1. Responsibility

Controls the status transitions of an already-registered `Product` (DOMAIN 5): Suspended ⇄ Published, and the irreversible transition to Discontinued.

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `publish(product)` | `Product` | void | delegates to `product.publishProduct()`; requires authenticated `product.seller` |
| `suspend(product)` | `Product` | void | delegates to `product.suspend()` |
| `discontinue(product)` | `Product` | void | delegates to `product.discontinue()`; **irreversible** |

## 3. Applied business rules

- DOMAIN 5: `ProductStatus` follows the Published/Suspended/Discontinued catalog.
- `discontinue()` is irreversible: this service exposes no operation to "reactivate" a discontinued product.
- BR-03: only the owning `Seller` can change the status of their product.

## 4. Dependencies (Output Ports)

- `ProductRepositoryPort`: `save`, `findById`.
