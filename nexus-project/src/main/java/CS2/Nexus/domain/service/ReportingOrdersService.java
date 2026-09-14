package CS2.Nexus.domain.service;

import java.util.List;

import CS2.Nexus.domain.model.Pedido;
import CS2.Nexus.domain.model.Supervisor;

public interface ReportingOrdersService {

    /** Vista consolidada de solo lectura de todos los pedidos. */
    List<Pedido> listConsolidatedOrders(Supervisor supervisor);
}
