# AccessControlService

## 1. Responsibility

Formalizes the cross-cutting access-control check (previously described only as a shared rule in `Domain_Services.md`) as its own callable service, invoked by every other service before executing a protected operation.

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `validateAccess(user, resource)` | `User`, resource identifier | Boolean / throws AccessDenied | `user.isActive() == true` |

## 3. Applied business rules

- RG-03: no participant may manage information outside their role — this is the single choke point all other services call before proceeding.
- BR-03 (as declared in `Domain_Services.md`): invoked before exposing any operation of another service.

## 4. Dependencies (Output Ports)

- `UserRepositoryPort`: `findById` (to resolve current role/status).
