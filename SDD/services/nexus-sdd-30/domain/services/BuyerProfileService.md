# BuyerProfileService

## 1. Responsibility

Manages a `Buyer`'s own profile data (delivery addresses) and commercial eligibility check, without touching orders or returns.

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `registerAddress(buyer, address, isPrimary)` | `Buyer`, address | void | authenticated buyer |
| `verifyCanPurchase(buyer)` | `Buyer` | Boolean | delegates to `buyer.canPurchase()` |

## 3. Applied business rules

- DOMAIN 2: key restriction — a buyer never manages another buyer's information.
- `BuyerStatus.Restricted` makes `verifyCanPurchase` return `false`.

## 4. Dependencies (Output Ports)

- `BuyerRepositoryPort`: `save`, `findById`.
