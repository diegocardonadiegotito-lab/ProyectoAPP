package CS2.Nexus.domain.service;

import CS2.Nexus.domain.model.Bodega;
import CS2.Nexus.domain.model.Envio;
import CS2.Nexus.domain.model.OperadorLogistico;
import CS2.Nexus.domain.model.Pedido;

public interface ShipmentCreationService {

    /** Crea el envio de un pedido pagado y fisico (estadoEnvio inicial: PREPARANDO). */
    Envio createShipment(Pedido pedido, Bodega bodegaOrigen, OperadorLogistico operador);
}
