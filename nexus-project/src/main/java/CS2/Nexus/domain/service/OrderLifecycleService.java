package CS2.Nexus.domain.service;

import java.math.BigDecimal;

import CS2.Nexus.domain.model.Bodega;
import CS2.Nexus.domain.model.Envio;
import CS2.Nexus.domain.model.OperadorLogistico;
import CS2.Nexus.domain.model.Pedido;

public interface OrderLifecycleService {

    /** Transiciona el pedido de CARRITO a PENDIENTE_PAGO. Requiere al menos 1 item y reserva de inventario exitosa. */
    void confirmOrder(Pedido pedido);

    /** Transiciona el pedido a PAGADO, via PaymentPort. */
    void confirmPayment(Pedido pedido, BigDecimal amount);

    /** Genera el Envio para un pedido pagado y fisico. Delega la creacion en ShipmentCreationService. */
    Envio dispatchFromWarehouse(Pedido pedido, Bodega bodega, OperadorLogistico operador);

    /** Transiciona el pedido a ENTREGADO_FINALIZADO. A partir de aqui el pedido es inmutable. */
    void finalizeOrder(Pedido pedido);
}
