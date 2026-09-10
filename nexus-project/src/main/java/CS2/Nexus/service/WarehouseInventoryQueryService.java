package CS2.Nexus.domain.service;

import java.util.List;

import CS2.Nexus.domain.model.Bodega;
import CS2.Nexus.domain.model.ItemInventario;

public interface WarehouseInventoryQueryService {

    /** Consulta de solo lectura del inventario de una bodega. */
    List<ItemInventario> queryInventory(Bodega bodega);

    /** Indica si la bodega pertenece al Marketplace (sin vendedor propietario). */
    boolean isMarketplaceWarehouse(Bodega bodega);
}
