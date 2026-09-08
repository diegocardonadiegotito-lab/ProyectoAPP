# BuyerReturnRequestService

## 1. Responsibility

Buyer-facing entry point for initiating a return. It is a thin facade: it validates that the requesting buyer owns the order, then delegates the actual creation to `ReturnRequestCreationService.createRequest()`. This is not duplicated logic — it is the role-oriented layer that fronts the entity-oriented return service.

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `requestReturn(buyer, order, reason)` | `Buyer`, `Order`, reason | `ReturnRequest` | `order.orderStatus == Delivered/Completed` and `order.buyer == buyer`; delegates to `ReturnRequestCreationService.createRequest()` |

## 3. Applied business rules

- DOMAIN 2: the service verifies the `Order` belongs to the requesting `Buyer` before delegating.
- OBJ-11: administration of returns and refunds (buyer-initiated leg).

## 4. Dependencies (Output Ports)

- `OrderRepositoryPort`: `findById` (ownership check).
- Internally depends on `ReturnRequestCreationService`.
