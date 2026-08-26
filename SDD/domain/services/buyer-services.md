# Buyer Services (Comprador)

## 1. Responsabilidad

Coordina las operaciones que un `Comprador` puede iniciar sobre sus propios pedidos, carrito y devoluciones. Garantiza la restricción clave del DOMINIO 2: un comprador nunca administra información de otros compradores ni de inventarios.

## 2. Operaciones

| Operación | Entrada | Salida | Precondición |
|---|---|---|---|
| `registrarDireccion(comprador, direccion, esPrincipal)` | `Comprador`, dirección | void | comprador autenticado |
| `verificarPuedeComprar(comprador)` | `Comprador` | Boolean | delega en `comprador.puedeComprar()` |
| `iniciarCarrito(comprador)` | `Comprador` | `Pedido` (estado `Carrito`) | `estadoComercial == Habilitado` |
| `solicitarDevolucion(comprador, pedido, motivo)` | `Comprador`, `Pedido`, motivo | `SolicitudDevolucion` | `pedido.estadoPedido == Entregado/Finalizado` y `pedido.comprador == comprador` |

## 3. Reglas de negocio aplicadas

- DOMINIO 2: restricción clave — sin acceso a datos de otros compradores ni a inventario.
- RG-03: el servicio verifica que el `Pedido` o `SolicitudDevolucion` referenciado pertenezca al `Comprador` que ejecuta la operación.
- `EstadoComprador.Restringido` bloquea `iniciarCarrito`.

## 4. Dependencias (Output Ports)

- `CompradorRepositoryPort`: `guardar`, `buscarPorId`.
- `PedidoRepositoryPort`: `guardar`, `buscarPorId`, `listarPorComprador`.
- `SolicitudDevolucionRepositoryPort`: `guardar`, `listarPorComprador`.
