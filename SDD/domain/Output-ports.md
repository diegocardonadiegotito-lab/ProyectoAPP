# Output Ports — NexusMarket

## Introduction

*Output ports* are the interfaces the domain defines to communicate outward (persistence, notifications, external gateways) following the hexagonal architecture pattern (Ports and Adapters). The domain **depends on these interfaces, not on their implementation**: the infrastructure layer (database, messaging, external providers) implements the concrete adapters at a stage after this document, which is business/domain-focused and not technology-focused (see section 3.2 of the functional specification: implementation technology is out of scope).

Each port is grouped by the entity or aggregate it governs, and exposes only the operations that the domain services (see `Domain Services.md`) need to invoke.

---

## Persistence Ports (Repository Ports)

| Port | Entity / Aggregate | Main operations | Used by (services) |
|---|---|---|---|
| `UserRepositoryPort` | `User` (and subclasses) | `save(user)`, `findById(id)`, `findByEmail(email)`, `emailExists(email)` | user-authentication-services, buyer-services, seller-services |
| `BuyerRepositoryPort` | `Buyer` | `save(buyer)`, `findById(id)` | buyer-services |
| `SellerRepositoryPort` | `Seller` | `save(seller)`, `findById(id)`, `listByAdministrator(adminId)` | seller-services |
| `WarehouseRepositoryPort` | `Warehouse` | `save(warehouse)`, `findById(id)`, `listBySeller(sellerId)` | warehouse-services |
| `ProductRepositoryPort` | `Product` | `save(product)`, `findById(id)`, `listPublished()`, `listBySeller(sellerId)` | catalog-services |
| `InventoryItemRepositoryPort` | `InventoryItem` | `save(item)`, `findByProductAndWarehouse(productId, warehouseId)`, `listByWarehouse(warehouseId)` | inventory-services |
| `InventoryMovementRepositoryPort` | `InventoryMovement` | `save(movement)`, `listByItem(itemId)` | inventory-services |
| `OrderRepositoryPort` | `Order` (aggregate root, includes `OrderItem`) | `save(order)`, `findById(id)`, `listByBuyer(buyerId)` | order-services |
| `InvoiceRepositoryPort` | `Invoice` | `save(invoice)`, `findByOrder(orderId)` | billing-services |
| `ShipmentRepositoryPort` | `Shipment` | `save(shipment)`, `findByOrder(orderId)`, `listByOperator(operatorId)` | shipping-services |
| `ReturnRequestRepositoryPort` | `ReturnRequest` | `save(request)`, `findById(id)`, `listByBuyer(buyerId)` | return-services |

---

## External / cross-cutting service ports

| Port | Purpose | Used by |
|---|---|---|
| `ReportQueryPort` | Read-only aggregated query for `Supervisor.queryReport()` (OBJ-12); does not modify state, operates on consolidated views of the repositories above. | reporting-services |
| `NotificationPort` | Sends notifications to the buyer/seller upon relevant status changes (order shipped, return approved). Not part of the functional scope described in the specification (section 3.2 explicitly excludes it); declared here as an extension point. | order-services, shipping-services, return-services |
| `PaymentPort` | Payment validation/confirmation for the `Pending Payment → Paid` transition. The specification does not detail the payment mechanism; the port is limited to `confirmPayment(orderId, amount)`. | order-services |

---

## Port design rules

- No port exposes infrastructure types (SQL, framework DTOs); only domain entities and Value Objects.
- Every operation that records a state change relevant to auditing (BR-01) explicitly receives the executing `User`, so the adapter can persist the traceability required by `InventoryMovement.executedBy`.
- Read-only ports (`ReportQueryPort`) are separated from write ports to respect the Supervisor's restriction: "read-only, no modification permissions."
