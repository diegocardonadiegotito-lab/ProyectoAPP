# Return Services (SolicitudDevolucion)

## 1. Responsabilidad

Administra el proceso de devolución y reembolso iniciado por un `Comprador` sobre un pedido entregado (OBJ-11), incluyendo la aprobación/rechazo por parte de un `Administrador` y el efecto resultante en el inventario.

## 2. Operaciones

| Operación | Entrada | Salida | Precondición |
|---|---|---|---|
| `crearSolicitud(comprador, pedido, motivo)` | `Comprador`, `Pedido`, motivo | `SolicitudDevolucion` (`estado = Solicitada`) | `pedido.estadoPedido == Entregado/Finalizado` y `pedido.comprador == comprador` |
| `aprobar(administrador, solicitud)` | `Administrador`, `SolicitudDevolucion` | void | delega en `administrador.aprobarDevolucion()`; fija `aprobadoPor` |
| `rechazar(administrador, solicitud)` | `Administrador`, `SolicitudDevolucion` | void | delega en `solicitud.rechazar()`; fija `aprobadoPor` |
| `procesarReembolso(solicitud)` | `SolicitudDevolucion` | `MovimientoInventario` (`tipo = Devolución`) | `estadoSolicitud == Aprobada` |

## 3. Reglas de negocio aplicadas

- OBJ-11: administración de devoluciones y reembolsos.
- Matriz de responsabilidades: "Gestión Reembolsos" corresponde a Comprador (solicitud) y Administrador (aprobación); el servicio no permite que el propio comprador se autoapruebe.
- `procesarReembolso()` solo genera el movimiento de inventario tras la aprobación explícita del Administrador.

## 4. Dependencias (Output Ports)

- `SolicitudDevolucionRepositoryPort`: `guardar`, `buscarPorId`, `listarPorComprador`.
- `MovimientoInventarioRepositoryPort`: `guardar` (movimiento tipo Devolución).
- `NotificacionPort`: aviso de aprobación/rechazo al comprador.
