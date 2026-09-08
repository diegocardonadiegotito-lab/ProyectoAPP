# SellerOnboardingService

## 1. Responsibility

Handles the incorporation of a new `Seller` into the platform, exclusively executed by an `Administrator` (DOMAIN 3).

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `registerSeller(administrator, data)` | `Administrator`, seller data | `Seller` (with `registeredBy = administrator`) | executed by `Administrator` |

## 3. Applied business rules

- DOMAIN 3: seller registration is exclusive to the `Administrator`; sellers never self-register.
- Responsibility Matrix (section 12): "Registro Vendedores" → Admin only.

## 4. Dependencies (Output Ports)

- `SellerRepositoryPort`: `save`.
