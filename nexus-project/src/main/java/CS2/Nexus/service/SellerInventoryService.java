package CS2.Nexus.domain.service;

import CS2.Nexus.domain.model.ItemInventario;
import CS2.Nexus.domain.model.MovimientoInventario;
import CS2.Nexus.domain.model.Vendedor;

/** Fachada del Vendedor. Valida propiedad y delega en InventoryStockService.registerInbound(). */
public interface SellerInventoryService {

    MovimientoInventario registerInventoryInbound(Vendedor vendedor, ItemInventario item, int cantidad);
}
