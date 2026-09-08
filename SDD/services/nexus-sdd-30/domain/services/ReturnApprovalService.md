# ReturnApprovalService

## 1. Responsibility

Handles the Administrator's decision (approve/reject) over an existing `ReturnRequest`. Separated from creation and refund processing so the approval authority is isolated to a single, auditable service.

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `approve(administrator, request)` | `Administrator`, `ReturnRequest` | void | delegates to `administrator.approveReturn()`; sets `approvedBy` |
| `reject(administrator, request)` | `Administrator`, `ReturnRequest` | void | delegates to `request.reject()`; sets `approvedBy` |

## 3. Applied business rules

- Responsibility Matrix (section 12): "Gestión Reembolsos" → Buyer (request) and Administrator (approval); a buyer can never self-approve.

## 4. Dependencies (Output Ports)

- `ReturnRequestRepositoryPort`: `save`, `findById`.
- `NotificationPort`: notice of approval/rejection to the buyer.
