# OrderLifecycleService

## 1. Responsibility

Controls the state transitions of an `Order` from `Pending Payment` onward — everything after the cart stage — coordinating payment confirmation, dispatch, and finalization (OBJ-08).

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `confirmOrder(order)` | `Order` | void (transition to `Pending Payment`) | at least 1 item; successful inventory reservation for each item (via `InventoryReservationService`) |
| `confirmPayment(order, amount)` | `Order`, amount | void (transition to `Paid`) | via `PaymentPort.confirmPayment` |
| `dispatchFromWarehouse(order, warehouse, operator)` | `Order`, `Warehouse`, `LogisticsOperator` | `Shipment` | `order.orderStatus == Paid`; only if `Product.isPhysical()`; delegates creation to `ShipmentCreationService` |
| `finalizeOrder(order)` | `Order` | void (transition to `Delivered/Completed`) | triggered by `ShipmentTrackingService.markDelivered()`; from this point the order is immutable |

## 3. Applied business rules

- OBJ-08: control of the complete order cycle.
- DOMAIN 7: status cycle `Pending Payment → Paid → Shipped → Delivered/Completed`.
- Critical validation: a finalized order cannot be modified under any circumstance (`isFinalized()` guard).

## 4. Dependencies (Output Ports)

- `OrderRepositoryPort`: `save`, `findById`, `listByBuyer`.
- `PaymentPort`: `confirmPayment`.
- `NotificationPort`: notice of relevant status changes.
- Internally depends on `InventoryReservationService` and `ShipmentCreationService`.
