# Order Services (Pedido)

## 1. Responsabilidad

Controla el ciclo de vida completo del pedido — el proceso central del sistema (DOMINIO 7) — desde `Carrito` hasta `Entregado/Finalizado`, coordinando `Comprador`, `Producto` e `Inventory Services` para las reservas asociadas.

## 2. Operaciones

| Operación | Entrada | Salida | Precondición |
|---|---|---|---|
| `agregarItem(pedido, producto, cantidad)` | `Pedido`, `Producto`, cantidad | void | `pedido.estadoPedido == Carrito` |
| `quitarItem(pedido, itemPedido)` | `Pedido`, `ItemPedido` | void | `pedido.estadoPedido == Carrito` |
| `confirmarPedido(pedido)` | `Pedido` | void (transición a `Pendiente de Pago`) | al menos 1 item; reserva de inventario exitosa para cada item |
| `confirmarPago(pedido, monto)` | `Pedido`, monto | void (transición a `Pagado`) | vía `PagoPort.confirmarPago` |
| `despacharDesdeBodega(pedido, bodega, operador)` | `Pedido`, `Bodega`, `OperadorLogistico` | `Envio` | `pedido.estadoPedido == Pagado`; solo si `Producto.esFisico()` |
| `finalizarPedido(pedido)` | `Pedido` | void (transición a `Entregado/Finalizado`) | disparado por `Envio.marcarEntregado()`; a partir de aquí el pedido es inmutable |

## 3. Reglas de negocio aplicadas

- DOMINIO 7: ciclo de estados `Carrito → Pendiente de Pago → Pagado → Despachado → Entregado/Finalizado`.
- Validación crítica: un pedido finalizado no puede modificarse bajo ninguna circunstancia (`estaFinalizado()` como guarda).
- `Pedido.items` es `0..*`: un carrito recién creado puede no tener items todavía.
- Matriz de responsabilidades: "Gestión de Pedidos" es compartida entre Comprador, Vendedor y Operador Logístico, cada uno sobre su porción del flujo.

## 4. Dependencias (Output Ports)

- `PedidoRepositoryPort`: `guardar`, `buscarPorId`, `listarPorComprador`.
- `PagoPort`: `confirmarPago`.
- `ItemInventarioRepositoryPort` (vía inventory-services): reservas al confirmar.
- `NotificacionPort`: aviso de cambios de estado relevantes.
