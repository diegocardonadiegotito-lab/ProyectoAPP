# Shipping Services (Envio)

## 1. Responsabilidad

Gestiona el proceso logístico de un pedido físico, desde el despacho hasta la entrega (OBJ-10), coordinando `Bodega`, `OperadorLogistico` y el `Pedido` al que pertenece.

## 2. Operaciones

| Operación | Entrada | Salida | Precondición |
|---|---|---|---|
| `crearEnvio(pedido, bodegaOrigen, operador)` | `Pedido`, `Bodega`, `OperadorLogistico` | `Envio` (`estadoEnvio = Preparando`) | `pedido.estadoPedido == Pagado`; producto físico |
| `despacharPedido(operador, envio)` | `OperadorLogistico`, `Envio` | void | delega en `operador.despacharPedido()`; fija `fechaDespacho` y `estadoEnvio = En Tránsito` |
| `marcarEntregado(envio)` | `Envio` | void | dispara `pedido.finalizar()` (transición a `Entregado/Finalizado`) |
| `reportarIncidencia(envio, detalle)` | `Envio`, detalle | void | `estadoEnvio = Incidencia` |

## 3. Reglas de negocio aplicadas

- OBJ-10: gestión de procesos logísticos para productos físicos únicamente (`Producto.esFisico()`).
- El ciclo `Preparando → En Tránsito → Entregado` está acoplado al ciclo del `Pedido`: `marcarEntregado()` finaliza el pedido automáticamente.
- RG-03: solo el `OperadorLogistico` asignado al envío puede despacharlo.

## 4. Dependencias (Output Ports)

- `EnvioRepositoryPort`: `guardar`, `buscarPorPedido`, `listarPorOperador`.
- `PedidoRepositoryPort`: actualización de estado al finalizar.
- `NotificacionPort`: aviso de despacho/entrega/incidencia.
