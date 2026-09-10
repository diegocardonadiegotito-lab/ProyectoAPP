package CS2.Nexus.domain.service;

import java.util.List;

import CS2.Nexus.domain.model.ItemInventario;
import CS2.Nexus.domain.model.Supervisor;

public interface ReportingInventoryService {

    /** Vista consolidada de solo lectura de todo el inventario. */
    List<ItemInventario> listConsolidatedInventory(Supervisor supervisor);
}
