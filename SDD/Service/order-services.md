# Order Services (Order)

## 1. Responsibility

Controls the complete lifecycle of the order — the central process of the system (DOMAIN 7) — from `Cart` to `Delivered/Completed`, coordinating `Buyer`, `Product`, and `Inventory Services` for the associated reservations.

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `addItem(order, product, quantity)` | `Order`, `Product`, quantity | void | `order.orderStatus == Cart` |
| `removeItem(order, orderItem)` | `Order`, `OrderItem` | void | `order.orderStatus == Cart` |
| `confirmOrder(order)` | `Order` | void (transition to `Pending Payment`) | at least 1 item; successful inventory reservation for each item |
| `confirmPayment(order, amount)` | `Order`, amount | void (transition to `Paid`) | via `PaymentPort.confirmPayment` |
| `dispatchFromWarehouse(order, warehouse, operator)` | `Order`, `Warehouse`, `LogisticsOperator` | `Shipment` | `order.orderStatus == Paid`; only if `Product.isPhysical()` |
| `finalizeOrder(order)` | `Order` | void (transition to `Delivered/Completed`) | triggered by `Shipment.markDelivered()`; from this point the order is immutable |

## 3. Applied business rules

- DOMAIN 7: status cycle `Cart → Pending Payment → Paid → Shipped → Delivered/Completed`.
- Critical validation: a finalized order cannot be modified under any circumstances (`isFinalized()` as a guard).
- `Order.items` is `0..*`: a newly created cart may not have items yet.
- Responsibility matrix: "Order Management" is shared between Buyer, Seller, and Logistics Operator, each over their portion of the flow.

## 4. Dependencies (Output Ports)

- `OrderRepositoryPort`: `save`, `findById`, `listByBuyer`.
- `PaymentPort`: `confirmPayment`.
- `InventoryItemRepositoryPort` (via inventory-services): reservations upon confirmation.
- `NotificationPort`: notice of relevant status changes.
