package CS2.Nexus.domain.service;

import CS2.Nexus.domain.model.ItemInventario;
import CS2.Nexus.domain.model.MovimientoInventario;
import CS2.Nexus.domain.model.Usuario;

public interface InventoryReservationService {

    /** Reserva cantidad de un item de inventario. No valido sobre items danados o inexistentes. */
    MovimientoInventario reserve(Usuario executingUser, ItemInventario item, int cantidad);

    /** Libera una reserva previa sobre un item de inventario. */
    MovimientoInventario release(Usuario executingUser, ItemInventario item, int cantidad);
}
