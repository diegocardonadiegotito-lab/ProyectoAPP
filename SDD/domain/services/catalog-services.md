# Catalog Services (Producto)

## 1. Responsabilidad

Administra el catálogo de productos (físicos y digitales) y sus transiciones de estado (DOMINIO 5), garantizando que solo un `Vendedor` gestione sus propios productos.

## 2. Operaciones

| Operación | Entrada | Salida | Precondición |
|---|---|---|---|
| `registrarProducto(vendedor, datos)` | `Vendedor`, datos | `Producto` (`estado = Suspendido` hasta publicar) | vendedor activo |
| `publicar(producto)` | `Producto` | void | delega en `producto.publicarProducto()`; requiere `producto.vendedor` autenticado |
| `suspender(producto)` | `Producto` | void | delega en `producto.suspender()` |
| `descontinuar(producto)` | `Producto` | void | delega en `producto.descontinuar()`; **irreversible** |
| `listarCatalogoPublico()` | — | `List<Producto>` | solo `estado == Publicado` |
| `requiereEnvio(producto)` | `Producto` | Boolean | delega en `producto.esFisico()` |

## 3. Reglas de negocio aplicadas

- DOMINIO 5: `TipoProducto` (Físico/Digital) determina si requiere `Envio`; `EstadoProducto` sigue el catálogo Publicado/Suspendido/Descontinuado.
- `descontinuar()` es irreversible: el servicio no expone ninguna operación de "reactivar" un producto descontinuado.
- RG-03: solo el `Vendedor` propietario puede modificar el estado de su producto.

## 4. Dependencias (Output Ports)

- `ProductoRepositoryPort`: `guardar`, `buscarPorId`, `listarPublicados`, `listarPorVendedor`.
