# ProductRegistrationService

## 1. Responsibility

Creates a new `Product` in the catalog, in `Suspended` status until explicitly published.

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `registerProduct(seller, data)` | `Seller`, data | `Product` (`status = Suspended` until published) | active seller |

## 3. Applied business rules

- DOMAIN 5: `ProductType` (Physical/Digital) and `variants` are set at registration.
- Responsibility Matrix (section 12): "Registro Productos" → Seller only.

## 4. Dependencies (Output Ports)

- `ProductRepositoryPort`: `save`.
