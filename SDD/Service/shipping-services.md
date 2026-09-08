# Shipping Services (Shipment)

## 1. Responsibility

Manages the logistics process of a physical order, from dispatch to delivery (OBJ-10), coordinating `Warehouse`, `LogisticsOperator`, and the `Order` it belongs to.

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `createShipment(order, originWarehouse, operator)` | `Order`, `Warehouse`, `LogisticsOperator` | `Shipment` (`shipmentStatus = Preparing`) | `order.orderStatus == Paid`; physical product |
| `dispatchOrder(operator, shipment)` | `LogisticsOperator`, `Shipment` | void | delegates to `operator.dispatchOrder()`; sets `dispatchDate` and `shipmentStatus = In Transit` |
| `markDelivered(shipment)` | `Shipment` | void | triggers `order.finalize()` (transition to `Delivered/Completed`) |
| `reportIssue(shipment, detail)` | `Shipment`, detail | void | `shipmentStatus = Issue` |

## 3. Applied business rules

- OBJ-10: logistics process management for physical products only (`Product.isPhysical()`).
- The `Preparing → In Transit → Delivered` cycle is coupled to the `Order` cycle: `markDelivered()` automatically finalizes the order.
- BR-03: only the `LogisticsOperator` assigned to the shipment can dispatch it.

## 4. Dependencies (Output Ports)

- `ShipmentRepositoryPort`: `save`, `findByOrder`, `listByOperator`.
- `OrderRepositoryPort`: status update upon finalization.
- `NotificationPort`: notice of dispatch/delivery/issue.
