# Billing Services (Invoice)

## 1. Responsibility

Generates and retrieves the commercial information associated with a completed sale (OBJ-09), based on the total calculated by the `Order`.

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `generateInvoice(order)` | `Order` | `Invoice` | `order.orderStatus == Paid` or later |
| `getInvoiceForOrder(order)` | `Order` | `Invoice` | invoice previously generated |

## 3. Applied business rules

- OBJ-09: administration of billing for purchases.
- `Invoice.generate()` uses `order.calculateTotal()` as the single source of truth for the amount — the service does not independently recalculate the total.

## 4. Dependencies (Output Ports)

- `InvoiceRepositoryPort`: `save`, `findByOrder`.
- `OrderRepositoryPort`: `findById` (reads status and total).
