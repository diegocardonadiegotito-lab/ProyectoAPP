# Inventory Services

## 1. Responsabilidad

Gestiona el inventario distribuido: existencias por producto/bodega y los movimientos que las afectan, garantizando trazabilidad completa (RG-01) y la restricción de existencias nunca negativas (DOMINIO 6).

## 2. Operaciones

| Operación | Entrada | Salida | Precondición |
|---|---|---|---|
| `reservar(usuarioEjecutor, item, cantidad)` | `Usuario`, `ItemInventario`, cantidad | `MovimientoInventario` (`tipo = Reserva`) | `cantidad <= item.cantidad` y el ítem no está dañado |
| `liberar(usuarioEjecutor, item, cantidad)` | `Usuario`, `ItemInventario`, cantidad | `MovimientoInventario` (`tipo = Ajuste`) | reserva previa existente |
| `registrarIngreso(usuarioEjecutor, item, cantidad)` | `Usuario`, `ItemInventario`, cantidad | `MovimientoInventario` (`tipo = Ingreso`) | ejecutado por `Vendedor` u `Administrador` |
| `registrarSalidaPorVenta(usuarioEjecutor, item, cantidad)` | `Usuario`, `ItemInventario`, cantidad | `MovimientoInventario` (`tipo = Salida por venta`) | `hayDisponibilidad(cantidad) == true` |
| `hayDisponibilidad(item, cantidad)` | `ItemInventario`, cantidad | Boolean | delega en `item.hayDisponibilidad()`, sin generar movimiento |

## 3. Reglas de negocio aplicadas

- DOMINIO 6: inventario siempre vinculado a un `Producto` y una `Bodega`; **nunca existencias negativas**.
- RG-01: todo `MovimientoInventario` registra `ejecutadoPor: Usuario` para trazabilidad.
- Validación crítica: no se puede reservar inventario inexistente o marcado como "Dañado".

## 4. Dependencias (Output Ports)

- `ItemInventarioRepositoryPort`: `guardar`, `buscarPorProductoYBodega`, `listarPorBodega`.
- `MovimientoInventarioRepositoryPort`: `guardar`, `listarPorItem`.
