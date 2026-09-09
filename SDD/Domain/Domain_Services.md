# Domain Services — NexusMarket

## Introduction

A Domain Service encapsulates business logic that **does not naturally belong to a single entity**, because it coordinates several entities/aggregates, validates cross-cutting rules (BR-01, BR-02, BR-03), or depends on an output port to complete. When an operation can be resolved entirely within an entity (for example `Product.suspend()`), it is left as an entity method and is not duplicated here as a service.

1. **Command/Query separation:** a service that writes (creates or changes state) is separated from one that only reads (queries).
2. **One service per entity/responsibility, not per role that calls it:** where a single file previously mixed operations over several entities (e.g. `Seller`, `Warehouse`, `Product`, `InventoryMovement` all in one file), each entity now has its own service.

Some services are **role-facing facades** (e.g. `BuyerReturnRequestService`, `SellerProductService`) that validate ownership/role and then delegate to the corresponding **entity-oriented service** (e.g. `ReturnRequestCreationService`, `ProductLifecycleService`). This is intentional layering, not duplicated logic — the facade is what a `Buyer` or `Seller` interacts with; the entity service is what actually executes and persists the change.

This document is the general index; the detail of each service lives in its own file inside `domain/services/`.

---

## Domain Services Map

### Users & Authentication (DOMAIN 1)

| Service | File | Operations |
|---|---|---|
| User registration | `UserRegistrationService.md` | `registerUser` |
| Authentication | `AuthenticationService.md` | `authenticate` |
| User status management | `UserStatusService.md` | `blockUser`, `reactivateUser` |
| Permission query | `PermissionQueryService.md` | `getPermissionsOf` |
| Access control (cross-cutting) | `AccessControlService.md` | `validateAccess` |

### Buyers & Orders (DOMAIN 2, DOMAIN 7, OBJ-07, OBJ-08)

| Service | File | Operations |
|---|---|---|
| Buyer profile | `BuyerProfileService.md` | `registerAddress`, `verifyCanPurchase` |
| Buyer return request (facade) | `BuyerReturnRequestService.md` | `requestReturn` |
| Cart management | `CartService.md` | `startCart`, `addItem`, `removeItem` |
| Order lifecycle | `OrderLifecycleService.md` | `confirmOrder`, `confirmPayment`, `dispatchFromWarehouse`, `finalizeOrder` |

### Sellers (DOMAIN 3)

| Service | File | Operations |
|---|---|---|
| Seller onboarding | `SellerOnboardingService.md` | `registerSeller` |
| Seller warehouse (facade) | `SellerWarehouseService.md` | `registerInitialWarehouse` |
| Seller product (facade) | `SellerProductService.md` | `publishProduct` |
| Seller inventory (facade) | `SellerInventoryService.md` | `registerInventoryInbound` |

### Warehouses (DOMAIN 4)

| Service | File | Operations |
|---|---|---|
| Warehouse registration | `WarehouseRegistrationService.md` | `registerMarketplaceWarehouse` |
| Warehouse inventory query | `WarehouseInventoryQueryService.md` | `queryInventory`, `isMarketplaceWarehouse` |

### Catalog (DOMAIN 5)

| Service | File | Operations |
|---|---|---|
| Product registration | `ProductRegistrationService.md` | `registerProduct` |
| Product lifecycle | `ProductLifecycleService.md` | `publish`, `suspend`, `discontinue` |
| Catalog query | `CatalogQueryService.md` | `listPublicCatalog`, `requiresShipping` |

### Inventory (DOMAIN 6)

| Service | File | Operations |
|---|---|---|
| Inventory reservation | `InventoryReservationService.md` | `reserve`, `release` |
| Inventory stock movements | `InventoryStockService.md` | `registerInbound`, `registerSaleOutbound` |
| Inventory availability query | `InventoryQueryService.md` | `isAvailable` |

### Billing & Shipping (OBJ-09, OBJ-10)

| Service | File | Operations |
|---|---|---|
| Billing | `BillingService.md` | `generateInvoice`, `getInvoiceForOrder` |
| Shipment creation | `ShipmentCreationService.md` | `createShipment` |
| Shipment tracking | `ShipmentTrackingService.md` | `dispatchOrder`, `markDelivered`, `reportIssue` |

### Returns & Refunds (OBJ-11)

| Service | File | Operations |
|---|---|---|
| Return request creation | `ReturnRequestCreationService.md` | `createRequest` |
| Return approval | `ReturnApprovalService.md` | `approve`, `reject` |
| Refund processing | `RefundProcessingService.md` | `processRefund` |

### Reporting (OBJ-12)

| Service | File | Operations |
|---|---|---|
| Reporting — orders | `ReportingOrdersService.md` | `listConsolidatedOrders` |
| Reporting — inventory | `ReportingInventoryService.md` | `listConsolidatedInventory` |
| Reporting — general query | `ReportingGeneralQueryService.md` | `queryReport` |

**Total: 30 services**, covering all 46 original operations without loss or unjustified duplication, plus 1 new formalized cross-cutting operation (`AccessControlService.validateAccess`).

---

## Cross-cutting rules applied by all services

- **BR-01 (RG-01):** every write operation must receive the authenticated `User` executing it; services propagate it to the audit output ports (`InventoryMovement.executedBy`).
- **BR-02 (RG-02):** no service allows more than one role to be assigned to the same `User`; the role is fixed by the subclass type, not by a mutable field.
- **BR-03 (RG-03):** each service validates `AccessControlService.validateAccess(user, resource)` before executing the requested operation, so that no participant manages information outside their role.

## Convention for each service file

All files in `domain/services/` follow the same structure:

1. **Responsibility** — what the service solves, and why it is separated from adjacent services.
2. **Operations** — signature, input/output, and precondition of each exposed operation.
3. **Applied business rules** — direct reference to the BR-xx / RG-xx / OBJ-xx / DOMAIN codes from the functional specification.
4. **Dependencies (Output Ports)** — which ports from `Output-ports.md` it consumes, and which other services it internally depends on (for facade services).
