# Seller Services (Vendedor)

## 1. Responsabilidad

Gestiona la incorporación y el mantenimiento de vendedores, y las operaciones que un `Vendedor` ejecuta sobre sus propios productos y bodegas. Un vendedor nunca se auto-registra: siempre es creado por un `Administrador` (DOMINIO 3).

## 2. Operaciones

| Operación | Entrada | Salida | Precondición |
|---|---|---|---|
| `registrarVendedor(administrador, datos)` | `Administrador`, datos del vendedor | `Vendedor` (con `registradoPor = administrador`) | ejecutado por `Administrador` |
| `registrarBodegaInicial(vendedor, datos)` | `Vendedor`, datos | `Bodega` (`tipoBodega = Vendedor`) | vendedor activo |
| `publicarProducto(vendedor, producto)` | `Vendedor`, `Producto` | void | `producto.vendedor == vendedor` |
| `registrarIngresoInventario(vendedor, item, cantidad)` | `Vendedor`, `ItemInventario`, cantidad | `MovimientoInventario` | `item.producto.vendedor == vendedor` |

## 3. Reglas de negocio aplicadas

- DOMINIO 3: el registro de vendedores es exclusivo del `Administrador`.
- RG-03: un vendedor solo opera sobre productos/bodegas propios (`producto.vendedor == this`).
- Matriz de responsabilidades: "Registro Vendedores" → solo Admin; "Registro Productos" y "Administración Inventario" → Vendedor (y Operador Logístico para inventario).

## 4. Dependencias (Output Ports)

- `VendedorRepositoryPort`: `guardar`, `buscarPorId`, `listarPorAdministrador`.
- `BodegaRepositoryPort`: `guardar`, `listarPorVendedor`.
- `ProductoRepositoryPort`: `guardar`, `listarPorVendedor`.
- `MovimientoInventarioRepositoryPort`: `guardar`.
