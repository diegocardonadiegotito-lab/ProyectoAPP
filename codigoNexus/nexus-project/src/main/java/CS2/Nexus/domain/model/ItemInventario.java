package CS2.Nexus.domain.model;

import java.util.Objects;

public class ItemInventario {

    private Producto producto;
    private Bodega bodega;
    private Integer cantidad;

    public ItemInventario(Producto producto, Bodega bodega, Integer cantidad) {
        this.producto = producto;
        this.bodega = bodega;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "ItemInventario [" + producto + " en " + bodega + " = " + cantidad + "]";
    }

    // La identidad de un ItemInventario es la combinacion producto + bodega (regla del dominio:
    // "siempre debe estar vinculado a exactamente un producto y una bodega").
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ItemInventario)) return false;
        ItemInventario otro = (ItemInventario) obj;
        return Objects.equals(producto, otro.producto) && Objects.equals(bodega, otro.bodega);
    }

    @Override
    public int hashCode() {
        return Objects.hash(producto, bodega);
    }
}
