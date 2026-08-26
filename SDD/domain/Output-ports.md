# Output Ports — NexusMarket

## Introducción

Los *output ports* son las interfaces que el dominio define para comunicarse hacia afuera (persistencia, notificaciones, pasarelas externas) siguiendo el patrón de arquitectura hexagonal (Puertos y Adaptadores). El dominio **depende de estas interfaces, no de su implementación**: la capa de infraestructura (base de datos, mensajería, proveedores externos) implementa los adaptadores concretos en una etapa posterior a este documento, que es de negocio/dominio y no de tecnología (ver sección 3.2 de la especificación funcional: fuera de alcance la tecnología de implementación).

Cada puerto se agrupa por la entidad o agregado que gobierna, y expone únicamente las operaciones que los servicios de dominio (ver `Domain Services.md`) necesitan invocar.

---

## Puertos de persistencia (Repository Ports)

| Puerto | Entidad / Agregado | Operaciones principales | Usado por (servicios) |
|---|---|---|---|
| `UsuarioRepositoryPort` | `Usuario` (y subclases) | `guardar(usuario)`, `buscarPorId(id)`, `buscarPorCorreo(correo)`, `existeCorreo(correo)` | user-authentication-services, buyer-services, seller-services |
| `CompradorRepositoryPort` | `Comprador` | `guardar(comprador)`, `buscarPorId(id)` | buyer-services |
| `VendedorRepositoryPort` | `Vendedor` | `guardar(vendedor)`, `buscarPorId(id)`, `listarPorAdministrador(adminId)` | seller-services |
| `BodegaRepositoryPort` | `Bodega` | `guardar(bodega)`, `buscarPorId(id)`, `listarPorVendedor(vendedorId)` | warehouse-services |
| `ProductoRepositoryPort` | `Producto` | `guardar(producto)`, `buscarPorId(id)`, `listarPublicados()`, `listarPorVendedor(vendedorId)` | catalog-services |
| `ItemInventarioRepositoryPort` | `ItemInventario` | `guardar(item)`, `buscarPorProductoYBodega(idProducto, idBodega)`, `listarPorBodega(idBodega)` | inventory-services |
| `MovimientoInventarioRepositoryPort` | `MovimientoInventario` | `guardar(movimiento)`, `listarPorItem(idItem)` | inventory-services |
| `PedidoRepositoryPort` | `Pedido` (agregado raíz, incluye `ItemPedido`) | `guardar(pedido)`, `buscarPorId(id)`, `listarPorComprador(idComprador)` | order-services |
| `FacturaRepositoryPort` | `Factura` | `guardar(factura)`, `buscarPorPedido(idPedido)` | billing-services |
| `EnvioRepositoryPort` | `Envio` | `guardar(envio)`, `buscarPorPedido(idPedido)`, `listarPorOperador(idOperador)` | shipping-services |
| `SolicitudDevolucionRepositoryPort` | `SolicitudDevolucion` | `guardar(solicitud)`, `buscarPorId(id)`, `listarPorComprador(idComprador)` | return-services |

---

## Puertos de servicios externos / transversales

| Puerto | Propósito | Usado por |
|---|---|---|
| `ReporteQueryPort` | Consulta agregada de solo lectura para `Supervisor.consultarReporte()` (OBJ-12); no modifica estado, opera sobre vistas consolidadas de los repositorios anteriores. | reporting-services |
| `NotificacionPort` | Envío de notificaciones al comprador/vendedor ante cambios de estado relevantes (pedido despachado, devolución aprobada). No forma parte del alcance funcional descrito en la especificación (sección 3.2 la excluye explícitamente); se declara aquí como punto de extensión. | order-services, shipping-services, return-services |
| `PagoPort` | Validación/confirmación de pago para la transición `Pendiente de Pago → Pagado`. La especificación no detalla el mecanismo de pago; el puerto se limita a `confirmarPago(pedidoId, monto)`. | order-services |

---

## Reglas de diseño de los puertos

- Ningún puerto expone tipos de infraestructura (SQL, DTOs de framework); solo entidades y Value Objects del dominio.
- Toda operación que registre un cambio de estado relevante para auditoría (RG-01) recibe explícitamente el `Usuario` ejecutor, para que el adaptador pueda persistir la trazabilidad exigida por `MovimientoInventario.ejecutadoPor`.
- Los puertos de solo consulta (`ReporteQueryPort`) están separados de los puertos de escritura para respetar la restricción del Supervisor: "solo lectura, sin permisos de modificación".
