package CS2.Nexus.domain.service;

import CS2.Nexus.domain.model.Producto;
import CS2.Nexus.domain.model.Vendedor;

/** Fachada del Vendedor. Valida propiedad y delega en ProductLifecycleService.publish(). */
public interface SellerProductService {

    void publishProduct(Vendedor vendedor, Producto producto);
}
