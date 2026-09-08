# ShipmentTrackingService

## 1. Responsibility

Tracks a `Shipment` after its creation: dispatch, delivery confirmation, and issue reporting.

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `dispatchOrder(operator, shipment)` | `LogisticsOperator`, `Shipment` | void | delegates to `operator.dispatchOrder()`; sets `dispatchDate` and `shipmentStatus = In Transit` |
| `markDelivered(shipment)` | `Shipment` | void | triggers `OrderLifecycleService.finalizeOrder()` |
| `reportIssue(shipment, detail)` | `Shipment`, detail | void | `shipmentStatus = Issue` |

## 3. Applied business rules

- The `Preparing → In Transit → Delivered` cycle is coupled to the `Order` cycle: `markDelivered()` automatically finalizes the order.
- BR-03: only the `LogisticsOperator` assigned to the shipment can dispatch it.

## 4. Dependencies (Output Ports)

- `ShipmentRepositoryPort`: `save`, `findByOrder`, `listByOperator`.
- `NotificationPort`: notice of dispatch/delivery/issue.
- Internally depends on `OrderLifecycleService`.
