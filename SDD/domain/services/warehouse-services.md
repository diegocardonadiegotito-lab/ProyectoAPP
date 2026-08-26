# Warehouse Services (Bodega)

## 1. Responsabilidad

Controla el ciclo de vida de las bodegas (Marketplace o Vendedor) y expone la consulta de su inventario asociado (DOMINIO 4).

## 2. Operaciones

| Operación | Entrada | Salida | Precondición |
|---|---|---|---|
| `registrarBodegaMarketplace(administrador, datos)` | `Administrador`, datos | `Bodega` (`tipoBodega = Marketplace`, `propietario = null`) | ejecutado por `Administrador` |
| `consultarInventario(bodega)` | `Bodega` | `List<ItemInventario>` | delega en `bodega.obtenerInventario()` |
| `esBodegaMarketplace(bodega)` | `Bodega` | Boolean | delega en `bodega.esDeMarketplace()` |

## 3. Reglas de negocio aplicadas

- DOMINIO 4: se distinguen bodegas del Marketplace (sin `Vendedor` propietario) y bodegas de Vendedor.
- Una bodega puede quedar sin `Vendedor` propietario si es del Marketplace, pero nunca deja de existir por ese cambio (asociación, no composición).

## 4. Dependencias (Output Ports)

- `BodegaRepositoryPort`: `guardar`, `buscarPorId`, `listarPorVendedor`.
- `ItemInventarioRepositoryPort`: `listarPorBodega`.
