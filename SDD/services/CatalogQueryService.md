# CatalogQueryService

## 1. Responsibility

Read-only queries over the public catalog. No operation here changes product state.

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `listPublicCatalog()` | — | `List<Product>` | only `status == Published` |
| `requiresShipping(product)` | `Product` | Boolean | delegates to `product.isPhysical()` |

## 3. Applied business rules

- DOMAIN 5: the public catalog only shows `Published` products.
- Determines whether a `Shipment` will be needed once ordered (`isPhysical()`).

## 4. Dependencies (Output Ports)

- `ProductRepositoryPort`: `listPublished`.
