package CS2.Nexus.domain.service;

import CS2.Nexus.domain.model.MovimientoInventario;
import CS2.Nexus.domain.model.SolicitudDevolucion;

public interface RefundProcessingService {

    /** Genera el movimiento de inventario de devolucion. Requiere estadoSolicitud = APROBADA. */
    MovimientoInventario processRefund(SolicitudDevolucion solicitud);
}
