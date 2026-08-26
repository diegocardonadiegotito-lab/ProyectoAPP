# Reporting Services (Supervisor)

## 1. Responsabilidad

Consolida información administrativa transversal para consulta (OBJ-12), a través del perfil de solo lectura `Supervisor`. No modifica estado en ninguna entidad del dominio.

## 2. Operaciones

| Operación | Entrada | Salida | Precondición |
|---|---|---|---|
| `consultarReporte(supervisor, tipo)` | `Supervisor`, tipo de reporte | `Reporte` | delega en `supervisor.consultarReporte()` |
| `listarPedidosConsolidado(supervisor)` | `Supervisor` | `List<Pedido>` (vista de solo lectura) | — |
| `listarInventarioConsolidado(supervisor)` | `Supervisor` | `List<ItemInventario>` (vista de solo lectura) | — |

## 3. Reglas de negocio aplicadas

- OBJ-12: consolidar información administrativa para consulta.
- El `Supervisor` redefine `obtenerPermisos()` para exponer únicamente lectura, sin permisos de modificación — el servicio nunca expone operaciones de escritura a este rol.

## 4. Dependencias (Output Ports)

- `ReporteQueryPort`: consulta agregada de solo lectura sobre los demás repositorios.
