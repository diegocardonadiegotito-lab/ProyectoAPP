# User Authentication Services

## 1. Responsibility

Manages the identification and operational status of every `User` on the platform, regardless of role. It does not decide role-specific permissions (that belongs to each subclass via `getPermissions()`), but it is the single point that validates identification/email uniqueness and the `Active`/`Blocked` status before allowing any operation (BR-01, BR-02).

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `registerUser(data, roleType)` | basic data + subclass type | `User` created | `identification` and `email` do not already exist |
| `authenticate(email, credentials)` | email, credentials | authenticated `User` | `isActive() == true` |
| `blockUser(user)` | `User` | void | executed by `Administrator` |
| `reactivateUser(user)` | `User` | void | executed by `Administrator` |
| `getPermissionsOf(user)` | `User` | `List<Permission>` | delegates to `user.getPermissions()` (polymorphic) |

## 3. Applied business rules

- BR-01: every operation is associated with an authenticated user.
- BR-02: a `User` has exactly one role, determined by its subclass; the service never reassigns the "type" of an existing user.
- BR-03: `validateAccess(resource)` is invoked before exposing any operation of another service.
- DOMAIN 1: identification and email are unique across the entire platform; status follows the `UserStatus` catalog.

## 4. Dependencies (Output Ports)

- `UserRepositoryPort`: `save`, `findById`, `findByEmail`, `emailExists`.
