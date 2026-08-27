# Domain Model — NexusMarket

## Introduction

The Domain Model represents the core business entities of the NexusMarket marketplace platform. These entities encapsulate the business rules, data, behavior, and relationships described in the functional specification.

The model follows Object-Oriented Design principles and applies **inheritance** to eliminate duplicated information, and **polymorphism** so that each user role resolves its permissions and behavior differently.

### Changes from v1

- **Methods** were added to all classes (v1 only had attributes — it was not possible to confirm polymorphism without behavior).
- `Order.items` was corrected from `1..*` to `0..*` (a newly created cart has 0 items).
- `InventoryMovement.executedBy: User` was added for traceability (BR-01).
- `ReturnRequest.approvedBy: Administrator` (0..1) was added — the responsibility matrix assigns refunds to both Buyer and Administrator.
- Explicitly documents why `User` does not have a `role` attribute (the specification lists it, but it is replaced by the subclass type).
- The **Enumerations** section was added; these were previously referenced only as undefined types.

---

# Domain Class Hierarchy

```text
User (Abstract)
├── Buyer
├── Seller
├── LogisticsOperator
├── Administrator
└── Supervisor

Warehouse

Product

InventoryItem

InventoryMovement

Order
├── OrderItem

Invoice

Shipment

ReturnRequest
```

---

# Relationships

| Source | Relationship | Target | Multiplicity | Type |
|---|---|---|---|---|
| Buyer | inherits from | User | — | Inheritance |
| Seller | inherits from | User | — | Inheritance |
| LogisticsOperator | inherits from | User | — | Inheritance |
| Administrator | inherits from | User | — | Inheritance |
| Supervisor | inherits from | User | — | Inheritance |
| Seller | registeredBy | Administrator | 1 seller → 1 administrator | Association |
| Warehouse | owner | Seller | 1 warehouse → 0..1 seller | Association |
| Product | seller | Seller | 1 product → 1 seller | Association |
| InventoryItem | product | Product | 1 item → 1 product | Association |
| InventoryItem | warehouse | Warehouse | 1 item → 1 warehouse | Association |
| InventoryMovement | inventoryItem | InventoryItem | 1 movement → 1 item | Association |
| InventoryMovement | executedBy | User | 1 movement → 1 user | Association *(new — BR-01 traceability)* |
| Order | buyer | Buyer | 1 order → 1 buyer | Association |
| Order | items | OrderItem | 1 order → 0..\* items | Composition *(corrected: previously 1..\*)* |
| OrderItem | product | Product | 1 item → 1 product | Association |
| Invoice | order | Order | 1 invoice → 1 order | Association |
| Shipment | order | Order | 1 shipment → 1 order | Association |
| Shipment | originWarehouse | Warehouse | 1 shipment → 1 warehouse | Association |
| Shipment | logisticsOperator | LogisticsOperator | 1 shipment → 1 operator | Association |
| ReturnRequest | order | Order | 1 request → 1 order | Association |
| ReturnRequest | buyer | Buyer | 1 request → 1 buyer | Association |
| ReturnRequest | approvedBy | Administrator | 1 request → 0..1 administrator | Association *(new — responsibility matrix)* |

**Notes on relationship type:**
- **Inheritance:** the subclass acquires all attributes and methods of the superclass (`User`), and redefines behavior marked as abstract/polymorphic. It is used because the 5 roles share identification, name, email, and status, but each has distinct behavior and its own data.
- **Composition:** an `OrderItem` has no meaning and does not exist outside of an `Order`. If the order is deleted, its items disappear with it. It is the only relationship of this type in the model.
- **Association:** the remaining relationships are simple references between independent entities — both can exist even if the relationship changes (for example, a `Warehouse` can be left without an owning `Seller` if it belongs to the Marketplace, but the warehouse continues to exist).

---

# Enumerations

| Enumeration | Values | Used in |
|---|---|---|
| `UserStatus` | Active, Blocked | User |
| `BuyerStatus` | Enabled, Restricted | Buyer |
| `WarehouseType` | Marketplace, Seller | Warehouse |
| `ProductType` | Physical, Digital | Product |
| `ProductStatus` | Published, Suspended, Discontinued | Product |
| `MovementType` | Inbound, Reservation, Sale Outbound, Adjustment, Return | InventoryMovement |
| `OrderStatus` | Cart, Pending Payment, Paid, Shipped, Delivered/Completed | Order |
| `ShipmentStatus` | Preparing, In Transit, Delivered, Issue | Shipment |
| `ReturnStatus` | Requested, Approved, Rejected, Refunded | ReturnRequest |

---

# Entities

---

# User (Abstract)

## Description

Represents any authenticated participant of the marketplace. This abstract class centralizes the identification and account information shared by all roles.

Each user has exactly one role within the system (BR-02). In the functional specification, "Role" appears as an attribute of User; in this model it **is deliberately replaced by the subclass type** (`Buyer`, `Seller`, etc.), since inheritance structurally guarantees a single role instead of relying on a mutable value that could be changed by mistake.

This class cannot be instantiated directly.

## Attributes

| Attribute | Type | Description |
|-----------|------|-------------|
| identification | String | Unique identifier of the user. |
| fullName | String | Official full name of the user. |
| email | String | Primary means of access and communication. Unique across the entire platform. |
| status | UserStatus | Current operational condition (e.g. Active, Blocked). |

## Methods

| Method | Return | Description |
|---|---|---|
| `isActive()` | Boolean | Checks whether `status == Active`. |
| `getPermissions()` *(abstract)* | List\<Permission\> | **Polymorphic.** Each subclass defines which operations it can perform (implements BR-03: no one manages information outside their role). |
| `validateAccess(resource)` *(abstract)* | Boolean | **Polymorphic.** Each subclass decides whether it can operate on a given resource. |

---

# Buyer

## Description

Represents a user who purchases products published on the marketplace.

A buyer can never manage information belonging to other buyers or inventory (key restriction of Domain 2).

## Attributes

| Attribute | Type | Description |
|-----------|------|-------------|
| primaryAddress | String | Usual delivery location. |
| additionalAddresses | List\<String\> | Secondary delivery locations. |
| commercialStatus | BuyerStatus | Condition that determines whether the buyer can make purchases. |

## Methods

| Method | Return | Description |
|---|---|---|
| `getPermissions()` | List\<Permission\> | Overrides the `User` method: permissions limited to their own orders, cart, and returns. |
| `canPurchase()` | Boolean | Checks `commercialStatus == Enabled`. |
| `createOrder()` | Order | Starts an `Order` in `Cart` status. |
| `requestReturn(order, reason)` | ReturnRequest | Creates a return request for a delivered order. |

---

# Seller

## Description

Represents a provider of products on the marketplace.

Sellers cannot self-register; they are onboarded exclusively by an Administrator (business rule of Domain 3).

## Attributes

| Attribute | Type | Description |
|-----------|------|-------------|
| registeredBy | Administrator | Administrator who onboarded the seller onto the platform. |

## Methods

| Method | Return | Description |
|---|---|---|
| `getPermissions()` | List\<Permission\> | Overrides the `User` method: permissions over their own products and warehouses. |
| `publishProduct(product)` | void | Changes `Product.status` to `Published`. |
| `registerInventoryInbound(item, quantity)` | InventoryMovement | Generates a movement of type `Inbound`. |

---

# LogisticsOperator

## Description

Represents the user responsible for the physical operation of warehouses and dispatches.

## Attributes

*No additional attributes beyond those inherited from `User`.*

## Methods

| Method | Return | Description |
|---|---|---|
| `getPermissions()` | List\<Permission\> | Overrides the `User` method: permissions over shipments and dispatch inventory. |
| `dispatchOrder(shipment)` | void | Marks `Shipment.shipmentStatus` as `In Transit` and records `dispatchDate`. |

---

# Administrator

## Description

Represents the user responsible for managing sellers and warehouses.

## Attributes

*No additional attributes beyond those inherited from `User`.*

## Methods

| Method | Return | Description |
|---|---|---|
| `getPermissions()` | List\<Permission\> | Overrides the `User` method: global administrative permissions (sellers, warehouses, refunds). |
| `registerSeller(data)` | Seller | Creates a new `Seller` with `registeredBy = this`. |
| `approveReturn(request)` | void | Changes `ReturnRequest.requestStatus` to `Approved` and sets `approvedBy = this`. |

---

# Supervisor

## Description

Represents a read-only profile used for operational monitoring.

## Attributes

*No additional attributes beyond those inherited from `User`.*

## Methods

| Method | Return | Description |
|---|---|---|
| `getPermissions()` | List\<Permission\> | Overrides the `User` method: read-only, with no modification permissions. |
| `queryReport(type)` | Report | Generates read-only consolidated information (OBJ-12). |

---

# Warehouse

## Description

Represents a physical space where inventory is managed. Warehouses are classified as belonging to the Marketplace or to a specific Seller.

## Attributes

| Attribute | Type | Description |
|-----------|------|-------------|
| warehouseId | String | Unique identifier of the warehouse. |
| warehouseType | WarehouseType | Classification of the warehouse: Marketplace or Seller. |
| owner | Seller | Seller who owns the warehouse. Null when `warehouseType` is Marketplace. |

## Methods

| Method | Return | Description |
|---|---|---|
| `isMarketplace()` | Boolean | Checks `warehouseType == Marketplace`. |
| `getInventory()` | List\<InventoryItem\> | Lists the inventory items associated with this warehouse. |

---

# Product

## Description

Represents a good offered on the marketplace, either physical or digital. Physical products require inventory and dispatch; digital products are delivered immediately after payment.

## Attributes

| Attribute | Type | Description |
|-----------|------|-------------|
| productId | String | Unique identifier of the product. |
| name | String | Descriptive name of the product. |
| productType | ProductType | Physical or Digital. |
| variants | List\<String\> | Variations such as color, size, or model. |
| status | ProductStatus | Published, Suspended, or Discontinued. |
| seller | Seller | Seller responsible for the product. |

## Methods

| Method | Return | Description |
|---|---|---|
| `isPhysical()` | Boolean | Checks `productType == Physical`. Determines whether it requires a `Shipment`. |
| `suspend()` | void | Changes `status` to `Suspended`. |
| `discontinue()` | void | Changes `status` to `Discontinued` (irreversible). |

---

# InventoryItem

## Description

Represents the stock of a specific product in a specific warehouse. Inventory is distributed and must always be linked to exactly one product and one warehouse.

## Attributes

| Attribute | Type | Description |
|-----------|------|-------------|
| product | Product | Product this stock refers to. |
| warehouse | Warehouse | Warehouse where the stock is located. |
| quantity | Integer | Available stock. Must never be negative. |

## Methods

| Method | Return | Description |
|---|---|---|
| `reserve(quantity)` | InventoryMovement | Validates that `quantity <= this.quantity` and that the item is not damaged; generates a movement of type `Reservation`. Throws an error if there is not enough stock. |
| `release(quantity)` | InventoryMovement | Generates a movement of type `Adjustment` that increases the stock (e.g. when canceling a reservation). |
| `isAvailable(quantity)` | Boolean | Checks `quantity <= this.quantity`, without generating a movement. |

---

# InventoryMovement

## Description

Represents a change applied to an `InventoryItem`. Every stock adjustment must be traceable to a specific movement and a responsible user (BR-01).

## Attributes

| Attribute | Type | Description |
|-----------|------|-------------|
| movementId | String | Unique identifier of the movement. |
| inventoryItem | InventoryItem | Inventory item affected by the movement. |
| movementType | MovementType | Inbound, Reservation, Sale Outbound, Adjustment, or Return. |
| quantity | Integer | Quantity involved in the movement. |
| movementDate | LocalDateTime | Date and time the movement occurred. |
| executedBy | User | Authenticated user who originated the movement *(new — BR-01 traceability)*. |

## Methods

| Method | Return | Description |
|---|---|---|
| `apply()` | void | Executes the effect of the movement on `inventoryItem.quantity` according to `movementType`. |

---

# Order

## Description

Represents the formal commercial commitment between a buyer and the marketplace. Its lifecycle is the central process of the system. A finalized order cannot be modified under any circumstances.

A newly created `Order` starts in `Cart` status and may not have any items yet (provisional selection).

## Attributes

| Attribute | Type | Description |
|-----------|------|-------------|
| orderId | String | Unique identifier of the order. |
| buyer | Buyer | Buyer who placed the order. |
| items | List\<OrderItem\> | Products and quantities included in the order (0..\*). |
| orderStatus | OrderStatus | Cart, Pending Payment, Paid, Shipped, or Delivered/Completed. |
| creationDate | LocalDateTime | Date and time the order was created. |

## Methods

| Method | Return | Description |
|---|---|---|
| `addItem(product, quantity)` | void | Only valid if `orderStatus == Cart`. Creates/updates an `OrderItem`. |
| `removeItem(orderItem)` | void | Only valid if `orderStatus == Cart`. |
| `calculateTotal()` | BigDecimal | Sums `quantity * unitPrice` for all items. |
| `confirm()` | void | Transition `Cart → Pending Payment`. Requires at least 1 item. |
| `finalize()` | void | Transition to `Delivered/Completed`. From this point on the order is immutable. |
| `isFinalized()` | Boolean | Checks `orderStatus == Delivered/Completed`. Used as a guard before any modification. |

---

# OrderItem

## Description

Represents a product line within an order, capturing the quantity and price at the time of purchase.

## Attributes

| Attribute | Type | Description |
|-----------|------|-------------|
| product | Product | Product included in the order. |
| quantity | Integer | Quantity of the product requested. |
| unitPrice | BigDecimal | Price of the product at the time of the order. |

## Methods

| Method | Return | Description |
|---|---|---|
| `subtotal()` | BigDecimal | Returns `quantity * unitPrice`. |

---

# Invoice

## Description

Represents the commercial information associated with a completed sale.

## Attributes

| Attribute | Type | Description |
|-----------|------|-------------|
| invoiceId | String | Unique identifier of the invoice. |
| order | Order | Order for which this invoice was generated. |
| issueDate | LocalDateTime | Date and time the invoice was issued. |
| totalAmount | BigDecimal | Total invoiced amount. |

## Methods

| Method | Return | Description |
|---|---|---|
| `generate(order)` | Invoice | Creates the invoice from `order.calculateTotal()`. Only valid if `order.orderStatus == Paid` or later. |

---

# Shipment

## Description

Represents the logistics process of a physical order, from dispatch to delivery.

## Attributes

| Attribute | Type | Description |
|-----------|------|-------------|
| shipmentId | String | Unique identifier of the shipment. |
| order | Order | Order being shipped. |
| originWarehouse | Warehouse | Warehouse from which the order is dispatched. |
| logisticsOperator | LogisticsOperator | Operator responsible for the dispatch. |
| dispatchDate | LocalDateTime | Date and time the order left the warehouse. |
| shipmentStatus | ShipmentStatus | Current status of the shipment. |

## Methods

| Method | Return | Description |
|---|---|---|
| `markInTransit()` | void | Changes `shipmentStatus` and sets `dispatchDate`. |
| `markDelivered()` | void | Changes `shipmentStatus` to `Delivered`; triggers `order.finalize()`. |

---

# ReturnRequest

## Description

Represents a return and/or refund process initiated by a buyer for a delivered order.

## Attributes

| Attribute | Type | Description |
|-----------|------|-------------|
| returnId | String | Unique identifier of the return request. |
| order | Order | Order being returned. |
| buyer | Buyer | Buyer who requested the return. |
| reason | String | Reason given for the return. |
| requestStatus | ReturnStatus | Current status of the return/refund process. |
| refundAmount | BigDecimal | Amount to be refunded, once approved. |
| approvedBy | Administrator | Administrator who approved/rejected the request *(new — responsibility matrix)*. |

## Methods

| Method | Return | Description |
|---|---|---|
| `approve(administrator)` | void | Sets `approvedBy`, changes `requestStatus` to `Approved`. |
| `reject(administrator)` | void | Sets `approvedBy`, changes `requestStatus` to `Rejected`. |
| `processRefund()` | InventoryMovement | Only valid if `requestStatus == Approved`. Generates a movement of type `Return` on the corresponding inventory. |
