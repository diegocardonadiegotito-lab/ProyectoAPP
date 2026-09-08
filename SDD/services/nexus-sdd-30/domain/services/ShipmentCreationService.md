# ShipmentCreationService

## 1. Responsibility

Creates a `Shipment` for a paid, physical order — the entry point into the logistics flow (OBJ-10).

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `createShipment(order, originWarehouse, operator)` | `Order`, `Warehouse`, `LogisticsOperator` | `Shipment` (`shipmentStatus = Preparing`) | `order.orderStatus == Paid`; physical product only |

## 3. Applied business rules

- OBJ-10: logistics process management for physical products only (`Product.isPhysical()`).

## 4. Dependencies (Output Ports)

- `ShipmentRepositoryPort`: `save`.
