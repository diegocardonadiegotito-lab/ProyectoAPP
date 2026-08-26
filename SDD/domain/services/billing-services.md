# Billing Services (Factura)

## 1. Responsabilidad

Genera y consulta la información comercial asociada a una venta concluida (OBJ-09), a partir del total calculado por el `Pedido`.

## 2. Operaciones

| Operación | Entrada | Salida | Precondición |
|---|---|---|---|
| `generarFactura(pedido)` | `Pedido` | `Factura` | `pedido.estadoPedido == Pagado` o posterior |
| `consultarFacturaDePedido(pedido)` | `Pedido` | `Factura` | factura previamente generada |

## 3. Reglas de negocio aplicadas

- OBJ-09: administración de la facturación de las compras.
- `Factura.generar()` usa `pedido.calcularTotal()` como fuente única de verdad del monto — el servicio no recalcula el total de forma independiente.

## 4. Dependencias (Output Ports)

- `FacturaRepositoryPort`: `guardar`, `buscarPorPedido`.
- `PedidoRepositoryPort`: `buscarPorId` (lectura del estado y total).
