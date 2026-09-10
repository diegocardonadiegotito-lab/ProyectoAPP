package CS2.Nexus.domain.service;

import CS2.Nexus.domain.model.Bodega;
import CS2.Nexus.domain.model.Vendedor;

public interface SellerWarehouseService {

    /** Registra la bodega propia de un vendedor (tipoBodega = VENDEDOR). */
    Bodega registerInitialWarehouse(Vendedor vendedor, Object data);
}
