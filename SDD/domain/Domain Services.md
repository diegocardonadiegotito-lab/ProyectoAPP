# Domain Services — NexusMarket

## Introducción

Un Domain Service encapsula lógica de negocio que **no pertenece naturalmente a una sola entidad**, porque coordina varias entidades/agregados, valida reglas transversales (RG-01, RG-02, RG-03) o depende de un output port para completarse. Cuando una operación puede resolverse enteramente dentro de una entidad (por ejemplo `Producto.suspender()`), se deja como método de la entidad y no se duplica aquí como servicio.

Este documento es el índice general; el detalle de cada servicio (responsabilidad, operaciones, reglas de negocio, puertos que consume) vive en su propio archivo dentro de `domain/services/`.

---

## Mapa de Domain Services

| Servicio | Archivo | Dominio funcional (spec) | Entidades que orquesta |
|---|---|---|---|
| Autenticación y gestión de usuarios | [`user-authentication-services.md`](services/user-authentication-services.md) | DOMINIO 1 — Administración de Usuarios | `Usuario` y sus subclases |
| Gestión de compradores | [`buyer-services.md`](services/buyer-services.md) | DOMINIO 2 — Gestión de Compradores | `Comprador`, `Pedido`, `SolicitudDevolucion` |
| Gestión de vendedores | [`seller-services.md`](services/seller-services.md) | DOMINIO 3 — Gestión de Vendedores | `Vendedor`, `Administrador`, `Producto`, `Bodega` |
| Gestión de bodegas | [`warehouse-services.md`](services/warehouse-services.md) | DOMINIO 4 — Gestión de Bodegas | `Bodega`, `ItemInventario` |
| Gestión de catálogo | [`catalog-services.md`](services/catalog-services.md) | DOMINIO 5 — Gestión del Catálogo | `Producto`, `Vendedor` |
| Gestión de inventario | [`inventory-services.md`](services/inventory-services.md) | DOMINIO 6 — Gestión del Inventario | `ItemInventario`, `MovimientoInventario` |
| Gestión de pedidos | [`order-services.md`](services/order-services.md) | DOMINIO 7 — Gestión de Pedidos | `Pedido`, `ItemPedido`, `Comprador`, `Producto` |
| Facturación | [`billing-services.md`](services/billing-services.md) | OBJ-09 — Administrar la facturación | `Factura`, `Pedido` |
| Logística y envíos | [`shipping-services.md`](services/shipping-services.md) | OBJ-10 — Gestionar procesos logísticos | `Envio`, `Bodega`, `OperadorLogistico`, `Pedido` |
| Devoluciones y reembolsos | [`return-services.md`](services/return-services.md) | OBJ-11 — Administrar devoluciones y reembolsos | `SolicitudDevolucion`, `Administrador`, `MovimientoInventario` |
| Reportes administrativos | [`reporting-services.md`](services/reporting-services.md) | OBJ-12 — Consolidar información administrativa | `Supervisor` (consulta transversal, solo lectura) |

---

## Reglas transversales aplicadas por todos los servicios

- **RG-01:** toda operación de escritura debe recibir el `Usuario` autenticado que la ejecuta; los servicios la propagan hacia los output ports de auditoría (`MovimientoInventario.ejecutadoPor`).
- **RG-02:** ningún servicio permite asignar más de un rol a un mismo `Usuario`; el rol queda fijado por el tipo de la subclase, no por un campo mutable.
- **RG-03:** cada servicio valida `usuario.validarAcceso(recurso)` / `usuario.obtenerPermisos()` antes de ejecutar la operación solicitada, para que ningún participante administre información fuera de su rol.

## Convención de cada archivo de servicio

Todos los archivos en `domain/services/` siguen la misma estructura para facilitar su lectura:

1. **Responsabilidad** — qué resuelve el servicio y por qué no vive en una sola entidad.
2. **Operaciones** — firma, entrada/salida y precondición de cada operación expuesta.
3. **Reglas de negocio aplicadas** — referencia directa a los códigos RG-xx / OBJ-xx / DOMINIO de la especificación funcional.
4. **Dependencias (Output Ports)** — qué puertos de `Output-ports.md` consume.
