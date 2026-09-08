package CS2.Nexus.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

import CS2.Nexus.domain.model.enums.EstadoDevolucion;

public class SolicitudDevolucion {

    private String idDevolucion;
    private Pedido pedido;
    private Comprador comprador;
    private String motivo;
    private EstadoDevolucion estadoSolicitud;
    private BigDecimal montoReembolso;

    public SolicitudDevolucion(String idDevolucion, Pedido pedido, Comprador comprador, String motivo,
                                EstadoDevolucion estadoSolicitud, BigDecimal montoReembolso) {
        this.idDevolucion = idDevolucion;
        this.pedido = pedido;
        this.comprador = comprador;
        this.motivo = motivo;
        this.estadoSolicitud = estadoSolicitud;
        this.montoReembolso = montoReembolso;
    }

    public String getIdDevolucion() {
        return idDevolucion;
    }

    public void setIdDevolucion(String idDevolucion) {
        this.idDevolucion = idDevolucion;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Comprador getComprador() {
        return comprador;
    }

    public void setComprador(Comprador comprador) {
        this.comprador = comprador;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public EstadoDevolucion getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(EstadoDevolucion estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public BigDecimal getMontoReembolso() {
        return montoReembolso;
    }

    public void setMontoReembolso(BigDecimal montoReembolso) {
        this.montoReembolso = montoReembolso;
    }

    @Override
    public String toString() {
        return "SolicitudDevolucion " + idDevolucion + " (" + estadoSolicitud + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof SolicitudDevolucion)) return false;
        return Objects.equals(idDevolucion, ((SolicitudDevolucion) obj).idDevolucion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDevolucion);
    }
}
