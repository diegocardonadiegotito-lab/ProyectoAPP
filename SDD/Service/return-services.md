# Return Services (ReturnRequest)

## 1. Responsibility

Manages the return and refund process initiated by a `Buyer` for a delivered order (OBJ-11), including approval/rejection by an `Administrator` and the resulting effect on inventory.

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `createRequest(buyer, order, reason)` | `Buyer`, `Order`, reason | `ReturnRequest` (`status = Requested`) | `order.orderStatus == Delivered/Completed` and `order.buyer == buyer` |
| `approve(administrator, request)` | `Administrator`, `ReturnRequest` | void | delegates to `administrator.approveReturn()`; sets `approvedBy` |
| `reject(administrator, request)` | `Administrator`, `ReturnRequest` | void | delegates to `request.reject()`; sets `approvedBy` |
| `processRefund(request)` | `ReturnRequest` | `InventoryMovement` (`type = Return`) | `requestStatus == Approved` |

## 3. Applied business rules

- OBJ-11: administration of returns and refunds.
- Responsibility matrix: "Refund Management" corresponds to Buyer (request) and Administrator (approval); the service does not allow buyers to self-approve.
- `processRefund()` only generates the inventory movement after explicit approval by the Administrator.

## 4. Dependencies (Output Ports)

- `ReturnRequestRepositoryPort`: `save`, `findById`, `listByBuyer`.
- `InventoryMovementRepositoryPort`: `save` (Return-type movement).
- `NotificationPort`: notice of approval/rejection to the buyer.
