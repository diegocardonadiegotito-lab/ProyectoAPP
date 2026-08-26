# Domain Value Objects — NexusMarket

## Introducción

Un Value Object (VO) es un tipo del dominio que se define exclusivamente por sus atributos, no tiene identidad propia y es inmutable: dos instancias con los mismos valores son intercambiables. En NexusMarket los Value Objects capturan **estados controlados** (enumeraciones cerradas descritas en la especificación funcional) y **datos compuestos** que viajan siempre acompañando a una entidad, sin ciclo de vida ni repositorio propio.

Se documentan por separado del Domain Model porque, a diferencia de las entidades (`Usuario`, `Pedido`, `Producto`, etc.), no tienen `id` ni se persisten de forma independiente.

---

## Enumeraciones (Value Objects de estado)

| Value Object | Valores permitidos | Entidad que lo usa | Regla que protege |
|---|---|---|---|
| `EstadoUsuario` | Activo, Bloqueado | `Usuario` | Un usuario bloqueado no puede operar (`estaActivo()`). |
| `EstadoComprador` | Habilitado, Restringido | `Comprador` | Determina si `puedeComprar()`. |
| `TipoBodega` | Marketplace, Vendedor | `Bodega` | Define si `propietario` puede ser nulo. |
| `TipoProducto` | Físico, Digital | `Producto` | Determina si el producto requiere `Envio` (`esFisico()`). |
| `EstadoProducto` | Publicado, Suspendido, Descontinuado | `Producto` | El catálogo público solo muestra productos `Publicado`. Descontinuado es irreversible. |
| `TipoMovimiento` | Ingreso, Reserva, Salida por venta, Ajuste, Devolución | `MovimientoInventario` | Define el efecto que `aplicar()` produce sobre la cantidad del ítem. |
| `EstadoPedido` | Carrito, Pendiente de Pago, Pagado, Despachado, Entregado/Finalizado | `Pedido` | Gobierna las transiciones válidas del ciclo de vida del pedido (DOMINIO 7). |
| `EstadoEnvio` | Preparando, En Tránsito, Entregado, Incidencia | `Envio` | `marcarEntregado()` solo es válido desde `En Tránsito`. |
| `EstadoDevolucion` | Solicitada, Aprobada, Rechazada, Reembolsada | `SolicitudDevolucion` | `procesarReembolso()` exige `estadoSolicitud == Aprobada`. |

---

## Value Objects compuestos

| Value Object | Composición | Descripción | Usado por |
|---|---|---|---|
| `Direccion` | `linea`, `ciudad`, `referencia` (agrupados desde `direccionPrincipal` / `direccionesAdicionales`) | Ubicación de entrega. Se trata como valor: si cambia una dirección, se reemplaza el VO completo, no se edita un campo suelto. | `Comprador` |
| `Dinero` | `monto: BigDecimal`, `moneda` (implícita: única moneda de la plataforma) | Envuelve todo monto monetario (`precioUnitario`, `montoTotal`, `montoReembolso`) para evitar aritmética directa sobre `BigDecimal` sin control de escala/redondeo. | `ItemPedido`, `Factura`, `SolicitudDevolucion` |
| `PeriodoAuditoria` *(soporte a RG-01)* | `fechaMovimiento` + `ejecutadoPor` | Par inmutable que se adjunta a cada `MovimientoInventario` para garantizar trazabilidad; no se puede modificar una vez creado el movimiento. | `MovimientoInventario` |

> Nota: `Direccion` y `Dinero` no aparecen como clases separadas en el Domain Model v2 (los atributos están planos en `Comprador`, `ItemPedido`, `Factura` y `SolicitudDevolucion`). Se documentan aquí como candidatos a Value Object explícito para la capa de dominio, dado que ambos son datos sin identidad que siempre acompañan a una entidad. Si el equipo decide no extraerlos como clases propias, esta sección sirve como referencia de las invariantes que de todas formas deben cumplir esos atributos.

---

## Invariantes de los Value Objects

- Todo Value Object es **inmutable**: una actualización crea una nueva instancia, nunca se muta el objeto original.
- Ninguna enumeración admite valores fuera del catálogo definido; validarlo es responsabilidad de la capa de dominio antes de asignar el estado.
- `Dinero` nunca admite montos negativos salvo en el caso explícito de `montoReembolso`, donde el signo lo determina el flujo de negocio, no el VO.
