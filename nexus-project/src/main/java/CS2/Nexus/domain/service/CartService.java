package CS2.Nexus.domain.service;

import CS2.Nexus.domain.model.Comprador;
import CS2.Nexus.domain.model.ItemPedido;
import CS2.Nexus.domain.model.Pedido;
import CS2.Nexus.domain.model.Producto;

public interface CartService {

    /** Inicia un carrito (Pedido en estado CARRITO). Requiere estadoComercial habilitado. */
    Pedido startCart(Comprador comprador);

    /** Agrega un producto al carrito. Solo valido si el pedido esta en estado CARRITO. */
    void addItem(Pedido pedido, Producto producto, int cantidad);

    /** Elimina un item del carrito. Solo valido si el pedido esta en estado CARRITO. */
    void removeItem(Pedido pedido, ItemPedido item);
}
