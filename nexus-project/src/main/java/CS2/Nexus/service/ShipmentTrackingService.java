package CS2.Nexus.domain.service;

import CS2.Nexus.domain.model.Envio;
import CS2.Nexus.domain.model.OperadorLogistico;

public interface ShipmentTrackingService {

    /** Despacha el envio. Solo el operador logistico asignado puede ejecutarlo. */
    void dispatchOrder(OperadorLogistico operador, Envio envio);

    /** Marca el envio como entregado. Dispara OrderLifecycleService.finalizeOrder(). */
    void markDelivered(Envio envio);

    /** Reporta una incidencia sobre el envio (estadoEnvio = INCIDENCIA). */
    void reportIssue(Envio envio, String detail);
}
