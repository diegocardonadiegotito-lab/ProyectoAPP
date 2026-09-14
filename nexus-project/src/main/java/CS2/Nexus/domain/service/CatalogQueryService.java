package CS2.Nexus.domain.service;

import java.util.List;

import CS2.Nexus.domain.model.Producto;

public interface CatalogQueryService {

    /** Lista el catalogo publico: unicamente productos en estado PUBLICADO. */
    List<Producto> listPublicCatalog();

    /** Indica si el producto requiere despacho fisico (delega en tipoProducto == FISICO). */
    boolean requiresShipping(Producto producto);
}
