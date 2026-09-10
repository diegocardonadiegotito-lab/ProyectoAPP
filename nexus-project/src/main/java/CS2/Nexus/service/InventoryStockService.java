package CS2.Nexus.domain.service;

import CS2.Nexus.domain.model.ItemInventario;
import CS2.Nexus.domain.model.MovimientoInventario;
import CS2.Nexus.domain.model.Usuario;

public interface InventoryStockService {

    /**
     * Registra ingreso de mercancia.
     * Ejecutor valido: Vendedor u OperadorLogistico (Matriz de Responsabilidades,
     * seccion 12) -- NO Administrador.
     */
    MovimientoInventario registerInbound(Usuario executingUser, ItemInventario item, int cantidad);

    /** Registra salida por venta. Generalmente disparado por OrderLifecycleService. */
    MovimientoInventario registerSaleOutbound(Usuario executingUser, ItemInventario item, int cantidad);
}
