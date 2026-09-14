package CS2.Nexus.domain.service;

import CS2.Nexus.domain.model.Producto;
import CS2.Nexus.domain.model.Vendedor;

public interface ProductRegistrationService {

    /** Crea un nuevo producto en estado SUSPENDIDO hasta su publicacion explicita. */
    Producto registerProduct(Vendedor vendedor, Object data);
}
