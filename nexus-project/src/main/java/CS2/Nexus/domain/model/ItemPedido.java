package CS2.Nexus.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public class ItemPedido {

    private Producto producto;
    private Integer cantidad;
    private BigDecimal precioUnitario;

    public ItemPedido(Producto producto, Integer cantidad, BigDecimal precioUnitario) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    @Override
    public String toString() {
        return cantidad + " x " + producto + " @ " + precioUnitario;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ItemPedido)) return false;
        ItemPedido otro = (ItemPedido) obj;
        return Objects.equals(producto, otro.producto) && Objects.equals(cantidad, otro.cantidad)
                && Objects.equals(precioUnitario, otro.precioUnitario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(producto, cantidad, precioUnitario);
    }
}
