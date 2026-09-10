package CS2.Nexus.domain.service;

import CS2.Nexus.domain.model.Administrador;
import CS2.Nexus.domain.model.SolicitudDevolucion;

public interface ReturnApprovalService {

    /** Aprueba una solicitud de devolucion. Exclusivo del Administrador. */
    void approve(Administrador administrador, SolicitudDevolucion solicitud);

    /** Rechaza una solicitud de devolucion. Exclusivo del Administrador. */
    void reject(Administrador administrador, SolicitudDevolucion solicitud);
}
