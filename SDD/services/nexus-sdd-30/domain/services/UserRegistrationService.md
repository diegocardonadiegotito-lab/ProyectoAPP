# UserRegistrationService

## 1. Responsibility

Creates new `User` accounts (of any role/subclass) on the platform, enforcing the identity uniqueness and data-completeness rules from DOMAIN 1. Split out from the former `user-authentication-services` to isolate the write/creation concern from authentication and status management.

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `registerUser(data, roleType)` | basic data + subclass type | `User` created | `identification` and `email` do not already exist; `fullName` is not empty (DOMAIN 1 attribute restriction) |

## 3. Applied business rules

- DOMAIN 1: `identification` and `email` unique across the platform; `fullName` cannot be empty.
- BR-02: the role is fixed at creation time via the subclass (`roleType`), never reassigned later.

## 4. Dependencies (Output Ports)

- `UserRepositoryPort`: `save`, `findByEmail`, `emailExists`.
