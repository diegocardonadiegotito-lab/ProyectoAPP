# Domain Services — NexusMarket

## Introduction

A Domain Service encapsulates business logic that **does not naturally belong to a single entity**, because it coordinates several entities/aggregates, validates cross-cutting rules (BR-01, BR-02, BR-03), or depends on an output port to complete. When an operation can be resolved entirely within an entity (for example `Product.suspend()`), it is left as an entity method and is not duplicated here as a service.

This document is the general index; the detail of each service (responsibility, operations, business rules, ports it consumes) lives in its own file inside `domain/services/`.

---

## Domain Services Map

| Service | File | Functional domain (spec) | Entities it orchestrates |
|---|---|---|---|
| Authentication and user management | [`user-authentication-services.md`](services/user-authentication-services.md) | DOMAIN 1 — User Administration | `User` and its subclasses |
| Buyer management | [`buyer-services.md`](services/buyer-services.md) | DOMAIN 2 — Buyer Management | `Buyer`, `Order`, `ReturnRequest` |
| Seller management | [`seller-services.md`](services/seller-services.md) | DOMAIN 3 — Seller Management | `Seller`, `Administrator`, `Product`, `Warehouse` |
| Warehouse management | [`warehouse-services.md`](services/warehouse-services.md) | DOMAIN 4 — Warehouse Management | `Warehouse`, `InventoryItem` |
| Catalog management | [`catalog-services.md`](services/catalog-services.md) | DOMAIN 5 — Catalog Management | `Product`, `Seller` |
| Inventory management | [`inventory-services.md`](services/inventory-services.md) | DOMAIN 6 — Inventory Management | `InventoryItem`, `InventoryMovement` |
| Order management | [`order-services.md`](services/order-services.md) | DOMAIN 7 — Order Management | `Order`, `OrderItem`, `Buyer`, `Product` |
| Billing | [`billing-services.md`](services/billing-services.md) | OBJ-09 — Manage billing | `Invoice`, `Order` |
| Logistics and shipping | [`shipping-services.md`](services/shipping-services.md) | OBJ-10 — Manage logistics processes | `Shipment`, `Warehouse`, `LogisticsOperator`, `Order` |
| Returns and refunds | [`return-services.md`](services/return-services.md) | OBJ-11 — Manage returns and refunds | `ReturnRequest`, `Administrator`, `InventoryMovement` |
| Administrative reporting | [`reporting-services.md`](services/reporting-services.md) | OBJ-12 — Consolidate administrative information | `Supervisor` (cross-cutting query, read-only) |

---

## Cross-cutting rules applied by all services

- **BR-01:** every write operation must receive the authenticated `User` executing it; services propagate it to the audit output ports (`InventoryMovement.executedBy`).
- **BR-02:** no service allows more than one role to be assigned to the same `User`; the role is fixed by the subclass type, not by a mutable field.
- **BR-03:** each service validates `user.validateAccess(resource)` / `user.getPermissions()` before executing the requested operation, so that no participant manages information outside their role.

## Convention for each service file

All files in `domain/services/` follow the same structure to make them easier to read:

1. **Responsibility** — what the service solves and why it does not live in a single entity.
2. **Operations** — signature, input/output, and precondition of each exposed operation.
3. **Applied business rules** — direct reference to the BR-xx / OBJ-xx / DOMAIN codes from the functional specification.
4. **Dependencies (Output Ports)** — which ports from `Output-ports.md` it consumes.
