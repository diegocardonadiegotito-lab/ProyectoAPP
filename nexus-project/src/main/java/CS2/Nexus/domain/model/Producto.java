package CS2.Nexus.domain.model;

import java.util.List;
import java.util.Objects;

import CS2.Nexus.domain.model.enums.EstadoProducto;
import CS2.Nexus.domain.model.enums.TipoProducto;

public class Producto {

    private String idProducto;
    private String nombre;
    private TipoProducto tipoProducto;
    private List<String> variantes;
    private EstadoProducto estado;
    private Vendedor vendedor;

    public Producto(String idProducto, String nombre, TipoProducto tipoProducto,
                     List<String> variantes, EstadoProducto estado, Vendedor vendedor) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.tipoProducto = tipoProducto;
        this.variantes = variantes;
        this.estado = estado;
        this.vendedor = vendedor;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public List<String> getVariantes() {
        return variantes;
    }

    public void setVariantes(List<String> variantes) {
        this.variantes = variantes;
    }

    public EstadoProducto getEstado() {
        return estado;
    }

    public void setEstado(EstadoProducto estado) {
        this.estado = estado;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    @Override
    public String toString() {
        return "Producto " + idProducto + " - " + nombre + " (" + estado + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Producto)) return false;
        return Objects.equals(idProducto, ((Producto) obj).idProducto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProducto);
    }
}
