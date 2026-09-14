package CS2.Nexus.domain.service;

import CS2.Nexus.domain.model.Comprador;
import CS2.Nexus.domain.model.Pedido;
import CS2.Nexus.domain.model.SolicitudDevolucion;

/**
 * Fachada orientada al Comprador. Valida que el pedido le pertenezca y
 * delega la creacion real en ReturnRequestCreationService.
 */
public interface BuyerReturnRequestService {

    SolicitudDevolucion requestReturn(Comprador comprador, Pedido pedido, String reason);
}
