# UserStatusService

## 1. Responsibility

Manages the operational status (`Active`/`Blocked`) of an existing `User`, exclusively executed by an `Administrator`.

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `blockUser(user)` | `User` | void | executed by `Administrator` |
| `reactivateUser(user)` | `User` | void | executed by `Administrator` |

## 3. Applied business rules

- DOMAIN 1: status follows the closed `UserStatus` catalog (Active/Blocked).
- RG-03: only `Administrator` may change another user's status.

## 4. Dependencies (Output Ports)

- `UserRepositoryPort`: `save`, `findById`.
