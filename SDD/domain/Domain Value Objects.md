# Domain Value Objects — NexusMarket

## Introduction

A Value Object (VO) is a domain type defined exclusively by its attributes, has no identity of its own, and is immutable: two instances with the same values are interchangeable. In NexusMarket, Value Objects capture **controlled states** (closed enumerations described in the functional specification) and **composite data** that always travels attached to an entity, without a lifecycle or its own repository.

They are documented separately from the Domain Model because, unlike entities (`User`, `Order`, `Product`, etc.), they have no `id` and are not persisted independently.

---

## Enumerations (state Value Objects)

| Value Object | Allowed values | Entity that uses it | Rule it protects |
|---|---|---|---|
| `UserStatus` | Active, Blocked | `User` | A blocked user cannot operate (`isActive()`). |
| `BuyerStatus` | Enabled, Restricted | `Buyer` | Determines whether `canPurchase()`. |
| `WarehouseType` | Marketplace, Seller | `Warehouse` | Defines whether `owner` can be null. |
| `ProductType` | Physical, Digital | `Product` | Determines whether the product requires `Shipment` (`isPhysical()`). |
| `ProductStatus` | Published, Suspended, Discontinued | `Product` | The public catalog only shows `Published` products. Discontinued is irreversible. |
| `MovementType` | Inbound, Reservation, Sale Outbound, Adjustment, Return | `InventoryMovement` | Defines the effect that `apply()` produces on the item's quantity. |
| `OrderStatus` | Cart, Pending Payment, Paid, Shipped, Delivered/Completed | `Order` | Governs the valid transitions of the order lifecycle (DOMAIN 7). |
| `ShipmentStatus` | Preparing, In Transit, Delivered, Issue | `Shipment` | `markDelivered()` is only valid from `In Transit`. |
| `ReturnStatus` | Requested, Approved, Rejected, Refunded | `ReturnRequest` | `processRefund()` requires `requestStatus == Approved`. |

---

## Composite Value Objects

| Value Object | Composition | Description | Used by |
|---|---|---|---|
| `Address` | `line`, `city`, `reference` (grouped from `primaryAddress` / `additionalAddresses`) | Delivery location. Treated as a value: if an address changes, the entire VO is replaced, not a loose field edited. | `Buyer` |
| `Money` | `amount: BigDecimal`, `currency` (implicit: the platform's single currency) | Wraps every monetary amount (`unitPrice`, `totalAmount`, `refundAmount`) to avoid direct arithmetic on `BigDecimal` without scale/rounding control. | `OrderItem`, `Invoice`, `ReturnRequest` |
| `AuditPeriod` *(supports BR-01)* | `movementDate` + `executedBy` | Immutable pair attached to each `InventoryMovement` to guarantee traceability; cannot be modified once the movement is created. | `InventoryMovement` |

## Value Object invariants

- Every Value Object is **immutable**: an update creates a new instance, the original object is never mutated.
- No enumeration accepts values outside the defined catalog; validating this is the responsibility of the domain layer before assigning the state.
- `Money` never accepts negative amounts except in the explicit case of `refundAmount`, where the sign is determined by the business flow, not by the VO.
