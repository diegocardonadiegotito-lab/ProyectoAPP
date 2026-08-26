# User Authentication Services

## 1. Responsabilidad

Gestiona la identificación y el estado operativo de todo `Usuario` de la plataforma, independientemente de su rol. No decide permisos específicos de rol (eso corresponde a cada subclase vía `obtenerPermisos()`), pero sí es el punto único que valida unicidad de identificación/correo y el estado `Activo`/`Bloqueado` antes de permitir cualquier operación (RG-01, RG-02).

## 2. Operaciones

| Operación | Entrada | Salida | Precondición |
|---|---|---|---|
| `registrarUsuario(datos, tipoRol)` | datos básicos + tipo de subclase | `Usuario` creado | `identificacion` y `correoElectronico` no existen previamente |
| `autenticar(correo, credenciales)` | correo, credenciales | `Usuario` autenticado | `estaActivo() == true` |
| `bloquearUsuario(usuario)` | `Usuario` | void | ejecutado por `Administrador` |
| `reactivarUsuario(usuario)` | `Usuario` | void | ejecutado por `Administrador` |
| `obtenerPermisosDe(usuario)` | `Usuario` | `List<Permiso>` | delega en `usuario.obtenerPermisos()` (polimórfico) |

## 3. Reglas de negocio aplicadas

- RG-01: toda operación queda asociada a un usuario autenticado.
- RG-02: un `Usuario` tiene exactamente un rol, determinado por su subclase; el servicio nunca reasigna el "tipo" de un usuario existente.
- RG-03: `validarAcceso(recurso)` se invoca antes de exponer cualquier operación de otro servicio.
- DOMINIO 1: identificación y correo son únicos en toda la plataforma; el estado sigue el catálogo `EstadoUsuario`.

## 4. Dependencias (Output Ports)

- `UsuarioRepositoryPort`: `guardar`, `buscarPorId`, `buscarPorCorreo`, `existeCorreo`.
