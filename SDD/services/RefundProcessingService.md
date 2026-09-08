# RefundProcessingService

## 1. Responsibility

Executes the inventory-side effect of an approved return: generates the `Return`-type `InventoryMovement`. Only runs after explicit Administrator approval.

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `processRefund(request)` | `ReturnRequest` | `InventoryMovement` (`type = Return`) | `requestStatus == Approved` |

## 3. Applied business rules

- `processRefund()` only generates the inventory movement after explicit approval by the Administrator (never before).

## 4. Dependencies (Output Ports)

- `InventoryMovementRepositoryPort`: `save` (Return-type movement).
