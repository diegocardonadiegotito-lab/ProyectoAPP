package CS2.Nexus.domain.service;

import CS2.Nexus.domain.model.Comprador;

public interface BuyerProfileService {

    /** Registra una direccion (principal o adicional) para el comprador. */
    void registerAddress(Comprador comprador, String address, boolean isPrimary);

    /** Verifica si el comprador puede realizar compras (delega en su estadoComercial). */
    boolean verifyCanPurchase(Comprador comprador);
}
