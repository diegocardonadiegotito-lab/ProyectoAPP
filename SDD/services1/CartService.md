# CartService

## 1. Responsibility

Manages the `Cart` stage of an order: creation and item selection, before any payment or lifecycle transition. Split out from order lifecycle management because the cart is a purely additive/removable state with no business-rule transitions of its own.

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `startCart(buyer)` | `Buyer` | `Order` (`Cart` status) | `commercialStatus == Enabled` |
| `addItem(order, product, quantity)` | `Order`, `Product`, quantity | void | `order.orderStatus == Cart` |
| `removeItem(order, orderItem)` | `Order`, `OrderItem` | void | `order.orderStatus == Cart` |

## 3. Applied business rules

- OBJ-07: cart management.
- DOMAIN 7: `Cart` is the initial state of the order lifecycle; `Order.items` is `0..*` (a cart may be empty).
- `BuyerStatus.Restricted` blocks `startCart`.

## 4. Dependencies (Output Ports)

- `OrderRepositoryPort`: `save`, `findById`.
