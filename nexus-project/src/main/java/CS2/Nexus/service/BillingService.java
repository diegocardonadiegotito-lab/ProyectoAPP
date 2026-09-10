package CS2.Nexus.domain.service;

import CS2.Nexus.domain.model.Factura;
import CS2.Nexus.domain.model.Pedido;

public interface BillingService {

    /** Genera la factura de un pedido pagado o en un estado posterior. */
    Factura generateInvoice(Pedido pedido);

    /** Obtiene la factura ya generada para un pedido. */
    Factura getInvoiceForOrder(Pedido pedido);
}
