package CS2.Nexus.domain.service;

import CS2.Nexus.domain.model.Comprador;
import CS2.Nexus.domain.model.Pedido;
import CS2.Nexus.domain.model.SolicitudDevolucion;

public interface ReturnRequestCreationService {

    /** Crea una solicitud de devolucion (estadoSolicitud = SOLICITADA) sobre un pedido entregado. */
    SolicitudDevolucion createRequest(Comprador comprador, Pedido pedido, String reason);
}
