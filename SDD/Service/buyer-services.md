# Buyer Services (Buyer)

## 1. Responsibility

Coordinates the operations a `Buyer` can initiate over their own orders, cart, and returns. Guarantees the key restriction of DOMAIN 2: a buyer never manages other buyers' information nor inventory.

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `registerAddress(buyer, address, isPrimary)` | `Buyer`, address | void | authenticated buyer |
| `verifyCanPurchase(buyer)` | `Buyer` | Boolean | delegates to `buyer.canPurchase()` |
| `startCart(buyer)` | `Buyer` | `Order` (`Cart` status) | `commercialStatus == Enabled` |
| `requestReturn(buyer, order, reason)` | `Buyer`, `Order`, reason | `ReturnRequest` | `order.orderStatus == Delivered/Completed` and `order.buyer == buyer` |

## 3. Applied business rules

- DOMAIN 2: key restriction — no access to other buyers' data nor to inventory.
- BR-03: the service verifies that the referenced `Order` or `ReturnRequest` belongs to the `Buyer` executing the operation.
- `BuyerStatus.Restricted` blocks `startCart`.

## 4. Dependencies (Output Ports)

- `BuyerRepositoryPort`: `save`, `findById`.
- `OrderRepositoryPort`: `save`, `findById`, `listByBuyer`.
- `ReturnRequestRepositoryPort`: `save`, `listByBuyer`.
