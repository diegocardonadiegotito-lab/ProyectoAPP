# Domain Model — NexusMarket

## Introducción

El Domain Model representa las entidades de negocio centrales de la plataforma marketplace NexusMarket. Estas entidades encapsulan las reglas de negocio, los datos, el comportamiento y las relaciones descritas en la especificación funcional.

El modelo sigue los principios del Diseño Orientado a Objetos y aplica **herencia** para eliminar información duplicada y **polimorfismo** para que cada rol de usuario resuelva sus permisos y comportamiento de forma distinta.

### Cambios respecto a v1

- Se añaden **métodos** a todas las clases (v1 solo tenía atributos — no era posible confirmar polimorfismo sin comportamiento).
- Se corrige `Pedido.items` de `1..*` a `0..*` (un carrito recién creado tiene 0 items).
- Se añade `MovimientoInventario.ejecutadoPor: Usuario` para trazabilidad (RG-01).
- Se añade `SolicitudDevolucion.aprobadoPor: Administrador` (0..1) — la matriz de responsabilidades asigna reembolsos a Comprador *y* Administrador.
- Se documenta explícitamente por qué `Usuario` no tiene un atributo `rol` (la especificación lo lista, pero se sustituye por el tipo de la subclase).
- Se agrega la sección **Enumeraciones**, antes solo referenciadas como tipos sin definir.

---

# Jerarquía de Clases del Dominio

```text
Usuario (Abstracto)
├── Comprador
├── Vendedor
├── OperadorLogistico
├── Administrador
└── Supervisor

Bodega

Producto

ItemInventario

MovimientoInventario

Pedido
├── ItemPedido

Factura

Envio

SolicitudDevolucion
```

---

# Relaciones

| Origen | Relación | Destino | Multiplicidad | Tipo |
|---|---|---|---|---|
| Comprador | hereda de | Usuario | — | Herencia |
| Vendedor | hereda de | Usuario | — | Herencia |
| OperadorLogistico | hereda de | Usuario | — | Herencia |
| Administrador | hereda de | Usuario | — | Herencia |
| Supervisor | hereda de | Usuario | — | Herencia |
| Vendedor | registradoPor | Administrador | 1 vendedor → 1 administrador | Asociación |
| Bodega | propietario | Vendedor | 1 bodega → 0..1 vendedor | Asociación |
| Producto | vendedor | Vendedor | 1 producto → 1 vendedor | Asociación |
| ItemInventario | producto | Producto | 1 item → 1 producto | Asociación |
| ItemInventario | bodega | Bodega | 1 item → 1 bodega | Asociación |
| MovimientoInventario | itemInventario | ItemInventario | 1 movimiento → 1 item | Asociación |
| MovimientoInventario | ejecutadoPor | Usuario | 1 movimiento → 1 usuario | Asociación *(nuevo — trazabilidad RG-01)* |
| Pedido | comprador | Comprador | 1 pedido → 1 comprador | Asociación |
| Pedido | items | ItemPedido | 1 pedido → 0..\* items | Composición *(corregido: antes 1..\*)* |
| ItemPedido | producto | Producto | 1 item → 1 producto | Asociación |
| Factura | pedido | Pedido | 1 factura → 1 pedido | Asociación |
| Envio | pedido | Pedido | 1 envío → 1 pedido | Asociación |
| Envio | bodegaOrigen | Bodega | 1 envío → 1 bodega | Asociación |
| Envio | operadorLogistico | OperadorLogistico | 1 envío → 1 operador | Asociación |
| SolicitudDevolucion | pedido | Pedido | 1 solicitud → 1 pedido | Asociación |
| SolicitudDevolucion | comprador | Comprador | 1 solicitud → 1 comprador | Asociación |
| SolicitudDevolucion | aprobadoPor | Administrador | 1 solicitud → 0..1 administrador | Asociación *(nuevo — matriz de responsabilidades)* |

**Notas sobre el tipo de relación:**
- **Herencia:** la subclase adquiere todos los atributos y métodos de la superclase (`Usuario`), y redefine el comportamiento marcado como abstracto/polimórfico. Se usa porque los 5 roles comparten identificación, nombre, correo y estado, pero cada uno tiene un comportamiento y unos datos propios distintos.
- **Composición:** un `ItemPedido` no tiene sentido ni existe fuera de un `Pedido`. Si se elimina el pedido, sus items desaparecen con él. Es la única relación de este tipo en el modelo.
- **Asociación:** el resto de relaciones son simples referencias entre entidades independientes — ambas pueden existir aunque la relación cambie (por ejemplo, una `Bodega` puede quedarse sin `Vendedor` propietario si es del Marketplace, pero la bodega no deja de existir).

---

# Enumeraciones

| Enumeración | Valores | Usada en |
|---|---|---|
| `EstadoUsuario` | Activo, Bloqueado | Usuario |
| `EstadoComprador` | Habilitado, Restringido | Comprador |
| `TipoBodega` | Marketplace, Vendedor | Bodega |
| `TipoProducto` | Físico, Digital | Producto |
| `EstadoProducto` | Publicado, Suspendido, Descontinuado | Producto |
| `TipoMovimiento` | Ingreso, Reserva, Salida por venta, Ajuste, Devolución | MovimientoInventario |
| `EstadoPedido` | Carrito, Pendiente de Pago, Pagado, Despachado, Entregado/Finalizado | Pedido |
| `EstadoEnvio` | Preparando, En Tránsito, Entregado, Incidencia | Envio |
| `EstadoDevolucion` | Solicitada, Aprobada, Rechazada, Reembolsada | SolicitudDevolucion |

---

# Entidades

---

# Usuario (Abstracto)

## Descripción

Representa a cualquier participante autenticado del marketplace. Esta clase abstracta centraliza la información de identificación y de cuenta compartida por todos los roles.

Cada usuario tiene exactamente un único rol dentro del sistema (RG-02). En la especificación funcional, "Rol" aparece como atributo de Usuario; en este modelo **se sustituye deliberadamente por el tipo de la subclase** (`Comprador`, `Vendedor`, etc.), ya que la herencia garantiza el rol único de forma estructural en lugar de depender de un valor mutable que podría cambiarse por error.

Esta clase no puede ser instanciada directamente.

## Atributos

| Atributo | Tipo | Descripción |
|-----------|------|-------------|
| identificacion | String | Identificador único del usuario. |
| nombreCompleto | String | Nombre oficial completo del usuario. |
| correoElectronico | String | Medio principal de acceso y comunicación. Único en toda la plataforma. |
| estado | EstadoUsuario | Condición operativa actual (p. ej. Activo, Bloqueado). |

## Métodos

| Método | Retorno | Descripción |
|---|---|---|
| `estaActivo()` | Boolean | Verifica si `estado == Activo`. |
| `obtenerPermisos()` *(abstracto)* | List\<Permiso\> | **Polimórfico.** Cada subclase define qué operaciones puede ejecutar (implementa RG-03: nadie administra información fuera de su rol). |
| `validarAcceso(recurso)` *(abstracto)* | Boolean | **Polimórfico.** Cada subclase decide si puede operar sobre un recurso dado. |

---

# Comprador

## Descripción

Representa a un usuario que adquiere productos publicados en el marketplace.

Un comprador nunca podrá administrar información de otros compradores ni de inventarios (restricción clave del Dominio 2).

## Atributos

| Atributo | Tipo | Descripción |
|-----------|------|-------------|
| direccionPrincipal | String | Ubicación habitual para entregas. |
| direccionesAdicionales | List\<String\> | Ubicaciones secundarias de entrega. |
| estadoComercial | EstadoComprador | Condición que determina si el comprador puede realizar compras. |

## Métodos

| Método | Retorno | Descripción |
|---|---|---|
| `obtenerPermisos()` | List\<Permiso\> | Redefine el método de `Usuario`: solo permisos sobre sus propios pedidos, carrito y devoluciones. |
| `puedeComprar()` | Boolean | Verifica `estadoComercial == Habilitado`. |
| `crearPedido()` | Pedido | Inicia un `Pedido` en estado `Carrito`. |
| `solicitarDevolucion(pedido, motivo)` | SolicitudDevolucion | Crea una solicitud de devolución sobre un pedido entregado. |

---

# Vendedor

## Descripción

Representa a un proveedor de productos en el marketplace.

Los vendedores no pueden auto-registrarse; son incorporados exclusivamente por un Administrador (regla de negocio del Dominio 3).

## Atributos

| Atributo | Tipo | Descripción |
|-----------|------|-------------|
| registradoPor | Administrador | Administrador que incorporó al vendedor a la plataforma. |

## Métodos

| Método | Retorno | Descripción |
|---|---|---|
| `obtenerPermisos()` | List\<Permiso\> | Redefine el método de `Usuario`: permisos sobre sus propios productos y bodegas. |
| `publicarProducto(producto)` | void | Cambia `Producto.estado` a `Publicado`. |
| `registrarIngresoInventario(item, cantidad)` | MovimientoInventario | Genera un movimiento de tipo `Ingreso`. |

---

# OperadorLogistico

## Descripción

Representa al usuario encargado de la operación física de bodegas y despachos.

## Atributos

*Sin atributos adicionales más allá de los heredados de `Usuario`.*

## Métodos

| Método | Retorno | Descripción |
|---|---|---|
| `obtenerPermisos()` | List\<Permiso\> | Redefine el método de `Usuario`: permisos sobre envíos e inventario de despacho. |
| `despacharPedido(envio)` | void | Marca `Envio.estadoEnvio` como `En Tránsito` y registra `fechaDespacho`. |

---

# Administrador

## Descripción

Representa al usuario responsable de la administración de vendedores y bodegas.

## Atributos

*Sin atributos adicionales más allá de los heredados de `Usuario`.*

## Métodos

| Método | Retorno | Descripción |
|---|---|---|
| `obtenerPermisos()` | List\<Permiso\> | Redefine el método de `Usuario`: permisos administrativos globales (vendedores, bodegas, reembolsos). |
| `registrarVendedor(datos)` | Vendedor | Crea un nuevo `Vendedor` con `registradoPor = this`. |
| `aprobarDevolucion(solicitud)` | void | Cambia `SolicitudDevolucion.estadoSolicitud` a `Aprobada` y fija `aprobadoPor = this`. |

---

# Supervisor

## Descripción

Representa un perfil de solo consulta utilizado para seguimiento operativo.

## Atributos

*Sin atributos adicionales más allá de los heredados de `Usuario`.*

## Métodos

| Método | Retorno | Descripción |
|---|---|---|
| `obtenerPermisos()` | List\<Permiso\> | Redefine el método de `Usuario`: solo lectura, sin permisos de modificación. |
| `consultarReporte(tipo)` | Reporte | Genera información consolidada de solo consulta (OBJ-12). |

---

# Bodega

## Descripción

Representa un espacio físico donde se administra el inventario. Las bodegas se clasifican como pertenecientes al Marketplace o a un Vendedor específico.

## Atributos

| Atributo | Tipo | Descripción |
|-----------|------|-------------|
| idBodega | String | Identificador único de la bodega. |
| tipoBodega | TipoBodega | Clasificación de la bodega: Marketplace o Vendedor. |
| propietario | Vendedor | Vendedor dueño de la bodega. Nulo cuando `tipoBodega` es Marketplace. |

## Métodos

| Método | Retorno | Descripción |
|---|---|---|
| `esDeMarketplace()` | Boolean | Verifica `tipoBodega == Marketplace`. |
| `obtenerInventario()` | List\<ItemInventario\> | Lista los ítems de inventario asociados a esta bodega. |

---

# Producto

## Descripción

Representa un bien ofrecido en el marketplace, ya sea físico o digital. Los productos físicos requieren inventario y despacho; los productos digitales se entregan de forma inmediata tras el pago.

## Atributos

| Atributo | Tipo | Descripción |
|-----------|------|-------------|
| idProducto | String | Identificador único del producto. |
| nombre | String | Nombre descriptivo del producto. |
| tipoProducto | TipoProducto | Físico o Digital. |
| variantes | List\<String\> | Variaciones como color, talla o modelo. |
| estado | EstadoProducto | Publicado, Suspendido o Descontinuado. |
| vendedor | Vendedor | Vendedor responsable del producto. |

## Métodos

| Método | Retorno | Descripción |
|---|---|---|
| `esFisico()` | Boolean | Verifica `tipoProducto == Físico`. Determina si requiere `Envio`. |
| `suspender()` | void | Cambia `estado` a `Suspendido`. |
| `descontinuar()` | void | Cambia `estado` a `Descontinuado` (irreversible). |

---

# ItemInventario

## Descripción

Representa la existencia de un producto específico en una bodega específica. El inventario es distribuido y siempre debe estar vinculado a exactamente un producto y una bodega.

## Atributos

| Atributo | Tipo | Descripción |
|-----------|------|-------------|
| producto | Producto | Producto al que se refiere esta existencia. |
| bodega | Bodega | Bodega donde se encuentra la existencia. |
| cantidad | Integer | Existencia disponible. Nunca debe ser negativa. |

## Métodos

| Método | Retorno | Descripción |
|---|---|---|
| `reservar(cantidad)` | MovimientoInventario | Valida que `cantidad <= this.cantidad` y que el ítem no esté dañado; genera un movimiento tipo `Reserva`. Lanza error si no hay existencia suficiente. |
| `liberar(cantidad)` | MovimientoInventario | Genera un movimiento tipo `Ajuste` que incrementa la existencia (p. ej. al cancelar una reserva). |
| `hayDisponibilidad(cantidad)` | Boolean | Verifica `cantidad <= this.cantidad`, sin generar movimiento. |

---

# MovimientoInventario

## Descripción

Representa un cambio aplicado a un `ItemInventario`. Todo ajuste de existencias debe poder rastrearse a un movimiento específico y a un usuario responsable (RG-01).

## Atributos

| Atributo | Tipo | Descripción |
|-----------|------|-------------|
| idMovimiento | String | Identificador único del movimiento. |
| itemInventario | ItemInventario | Ítem de inventario afectado por el movimiento. |
| tipoMovimiento | TipoMovimiento | Ingreso, Reserva, Salida por venta, Ajuste o Devolución. |
| cantidad | Integer | Cantidad involucrada en el movimiento. |
| fechaMovimiento | LocalDateTime | Fecha y hora en que ocurrió el movimiento. |
| ejecutadoPor | Usuario | Usuario autenticado que originó el movimiento *(nuevo — trazabilidad RG-01)*. |

## Métodos

| Método | Retorno | Descripción |
|---|---|---|
| `aplicar()` | void | Ejecuta el efecto del movimiento sobre `itemInventario.cantidad` según `tipoMovimiento`. |

---

# Pedido

## Descripción

Representa el compromiso comercial formal entre un comprador y el marketplace. Su ciclo de vida es el proceso central del sistema. Un pedido finalizado no podrá ser modificado bajo ninguna circunstancia.

Un `Pedido` recién creado se encuentra en estado `Carrito` y puede no tener items todavía (selección provisional).

## Atributos

| Atributo | Tipo | Descripción |
|-----------|------|-------------|
| idPedido | String | Identificador único del pedido. |
| comprador | Comprador | Comprador que realizó el pedido. |
| items | List\<ItemPedido\> | Productos y cantidades incluidos en el pedido (0..\*). |
| estadoPedido | EstadoPedido | Carrito, Pendiente de Pago, Pagado, Despachado o Entregado/Finalizado. |
| fechaCreacion | LocalDateTime | Fecha y hora en que se creó el pedido. |

## Métodos

| Método | Retorno | Descripción |
|---|---|---|
| `agregarItem(producto, cantidad)` | void | Válido solo si `estadoPedido == Carrito`. Crea/actualiza un `ItemPedido`. |
| `quitarItem(itemPedido)` | void | Válido solo si `estadoPedido == Carrito`. |
| `calcularTotal()` | BigDecimal | Suma `cantidad * precioUnitario` de todos los items. |
| `confirmar()` | void | Transición `Carrito → Pendiente de Pago`. Requiere al menos 1 item. |
| `finalizar()` | void | Transición a `Entregado/Finalizado`. A partir de aquí el pedido es inmutable. |
| `estaFinalizado()` | Boolean | Verifica `estadoPedido == Entregado/Finalizado`. Usado como guarda antes de cualquier modificación. |

---

# ItemPedido

## Descripción

Representa una línea de producto dentro de un pedido, capturando la cantidad y el precio en el momento de la compra.

## Atributos

| Atributo | Tipo | Descripción |
|-----------|------|-------------|
| producto | Producto | Producto incluido en el pedido. |
| cantidad | Integer | Cantidad del producto solicitada. |
| precioUnitario | BigDecimal | Precio del producto en el momento del pedido. |

## Métodos

| Método | Retorno | Descripción |
|---|---|---|
| `subtotal()` | BigDecimal | Retorna `cantidad * precioUnitario`. |

---

# Factura

## Descripción

Representa la información comercial asociada a una venta concluida.

## Atributos

| Atributo | Tipo | Descripción |
|-----------|------|-------------|
| idFactura | String | Identificador único de la factura. |
| pedido | Pedido | Pedido para el cual se generó esta factura. |
| fechaEmision | LocalDateTime | Fecha y hora en que se emitió la factura. |
| montoTotal | BigDecimal | Monto total facturado. |

## Métodos

| Método | Retorno | Descripción |
|---|---|---|
| `generar(pedido)` | Factura | Crea la factura a partir de `pedido.calcularTotal()`. Válido solo si `pedido.estadoPedido == Pagado` o posterior. |

---

# Envio

## Descripción

Representa el proceso logístico de un pedido físico, desde el despacho hasta la entrega.

## Atributos

| Atributo | Tipo | Descripción |
|-----------|------|-------------|
| idEnvio | String | Identificador único del envío. |
| pedido | Pedido | Pedido que está siendo enviado. |
| bodegaOrigen | Bodega | Bodega desde la cual se despacha el pedido. |
| operadorLogistico | OperadorLogistico | Operador responsable del despacho. |
| fechaDespacho | LocalDateTime | Fecha y hora en que el pedido salió de la bodega. |
| estadoEnvio | EstadoEnvio | Estado actual del envío. |

## Métodos

| Método | Retorno | Descripción |
|---|---|---|
| `marcarEnTransito()` | void | Cambia `estadoEnvio` y fija `fechaDespacho`. |
| `marcarEntregado()` | void | Cambia `estadoEnvio` a `Entregado`; dispara `pedido.finalizar()`. |

---

# SolicitudDevolucion

## Descripción

Representa un proceso de devolución y/o reembolso iniciado por un comprador sobre un pedido entregado.

## Atributos

| Atributo | Tipo | Descripción |
|-----------|------|-------------|
| idDevolucion | String | Identificador único de la solicitud de devolución. |
| pedido | Pedido | Pedido que está siendo devuelto. |
| comprador | Comprador | Comprador que solicitó la devolución. |
| motivo | String | Motivo indicado para la devolución. |
| estadoSolicitud | EstadoDevolucion | Estado actual del proceso de devolución/reembolso. |
| montoReembolso | BigDecimal | Monto a reembolsar, una vez aprobado. |
| aprobadoPor | Administrador | Administrador que aprobó/rechazó la solicitud *(nuevo — matriz de responsabilidades)*. |

## Métodos

| Método | Retorno | Descripción |
|---|---|---|
| `aprobar(administrador)` | void | Fija `aprobadoPor`, cambia `estadoSolicitud` a `Aprobada`. |
| `rechazar(administrador)` | void | Fija `aprobadoPor`, cambia `estadoSolicitud` a `Rechazada`. |
| `procesarReembolso()` | MovimientoInventario | Válido solo si `estadoSolicitud == Aprobada`. Genera un movimiento tipo `Devolución` sobre el inventario correspondiente. |
