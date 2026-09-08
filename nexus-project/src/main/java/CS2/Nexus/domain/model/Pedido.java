package CS2.Nexus.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import CS2.Nexus.domain.model.enums.EstadoPedido;

public class Pedido {

    private String idPedido;
    private Comprador comprador;
    private List<ItemPedido> items;
    private EstadoPedido estadoPedido;
    private LocalDateTime fechaCreacion;

    public Pedido(String idPedido, Comprador comprador, List<ItemPedido> items,
                   EstadoPedido estadoPedido, LocalDateTime fechaCreacion) {
        this.idPedido = idPedido;
        this.comprador = comprador;
        this.items = items;
        this.estadoPedido = estadoPedido;
        this.fechaCreacion = fechaCreacion;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public Comprador getComprador() {
        return comprador;
    }

    public void setComprador(Comprador comprador) {
        this.comprador = comprador;
    }

    public List<ItemPedido> getItems() {
        return items;
    }

    public void setItems(List<ItemPedido> items) {
        this.items = items;
    }

    public EstadoPedido getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "Pedido " + idPedido + " (" + estadoPedido + ") de " + comprador;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Pedido)) return false;
        return Objects.equals(idPedido, ((Pedido) obj).idPedido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPedido);
    }
}
