# ReturnRequestCreationService

## 1. Responsibility

Creates a `ReturnRequest` in `Requested` status for a delivered order. This is the entity-oriented service that `BuyerReturnRequestService` delegates to.

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `createRequest(buyer, order, reason)` | `Buyer`, `Order`, reason | `ReturnRequest` (`status = Requested`) | `order.orderStatus == Delivered/Completed` and `order.buyer == buyer` |

## 3. Applied business rules

- OBJ-11: administration of returns and refunds.

## 4. Dependencies (Output Ports)

- `ReturnRequestRepositoryPort`: `save`.
