package CS2.Nexus.domain.service;

import CS2.Nexus.domain.model.ItemInventario;

public interface InventoryQueryService {

    /** Consulta de solo lectura de disponibilidad. No genera ningun movimiento. */
    boolean isAvailable(ItemInventario item, int cantidad);
}
