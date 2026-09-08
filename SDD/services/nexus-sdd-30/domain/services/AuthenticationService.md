# AuthenticationService

## 1. Responsibility

Validates credentials and resolves the authenticated `User` for any subsequent operation. Kept separate from registration/status management so that authentication logic (RG-01) has a single, reusable entry point.

## 2. Operations

| Operation | Input | Output | Precondition |
|---|---|---|---|
| `authenticate(email, credentials)` | email, credentials | authenticated `User` | `isActive() == true` |

## 3. Applied business rules

- RG-01: every operation must be executed by an authenticated user — this service is the entry point that produces that authenticated `User`.
- A `Blocked` user cannot authenticate (`isActive()` guard).

## 4. Dependencies (Output Ports)

- `UserRepositoryPort`: `findByEmail`.
