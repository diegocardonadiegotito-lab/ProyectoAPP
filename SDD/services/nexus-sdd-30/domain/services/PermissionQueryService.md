# PermissionQueryService

## 1. Responsibility

Read-only query that exposes the permissions of a given `User`, resolved polymorphically by role. Separated as a query-only service so no write operation is ever bundled with a permission check.

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `getPermissionsOf(user)` | `User` | `List<Permission>` | delegates to `user.getPermissions()` (polymorphic) |

## 3. Applied business rules

- RG-02/RG-03: permissions are derived strictly from the user's subclass (role), never from a mutable field.

## 4. Dependencies (Output Ports)

- `UserRepositoryPort`: `findById`.
