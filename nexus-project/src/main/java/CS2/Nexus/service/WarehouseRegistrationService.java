package CS2.Nexus.domain.service;

import CS2.Nexus.domain.model.Administrador;
import CS2.Nexus.domain.model.Bodega;

public interface WarehouseRegistrationService {

    /** Registra una bodega del Marketplace (tipoBodega = MARKETPLACE, propietario = null). */
    Bodega registerMarketplaceWarehouse(Administrador administrador, Object data);
}
