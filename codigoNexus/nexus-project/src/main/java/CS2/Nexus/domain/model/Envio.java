package CS2.Nexus.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

import CS2.Nexus.domain.model.enums.EstadoEnvio;

public class Envio {

    private String idEnvio;
    private Pedido pedido;
    private Bodega bodegaOrigen;
    private OperadorLogistico operadorLogistico;
    private LocalDateTime fechaDespacho;
    private EstadoEnvio estadoEnvio;

    public Envio(String idEnvio, Pedido pedido, Bodega bodegaOrigen, OperadorLogistico operadorLogistico,
                 LocalDateTime fechaDespacho, EstadoEnvio estadoEnvio) {
        this.idEnvio = idEnvio;
        this.pedido = pedido;
        this.bodegaOrigen = bodegaOrigen;
        this.operadorLogistico = operadorLogistico;
        this.fechaDespacho = fechaDespacho;
        this.estadoEnvio = estadoEnvio;
    }

    public String getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(String idEnvio) {
        this.idEnvio = idEnvio;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Bodega getBodegaOrigen() {
        return bodegaOrigen;
    }

    public void setBodegaOrigen(Bodega bodegaOrigen) {
        this.bodegaOrigen = bodegaOrigen;
    }

    public OperadorLogistico getOperadorLogistico() {
        return operadorLogistico;
    }

    public void setOperadorLogistico(OperadorLogistico operadorLogistico) {
        this.operadorLogistico = operadorLogistico;
    }

    public LocalDateTime getFechaDespacho() {
        return fechaDespacho;
    }

    public void setFechaDespacho(LocalDateTime fechaDespacho) {
        this.fechaDespacho = fechaDespacho;
    }

    public EstadoEnvio getEstadoEnvio() {
        return estadoEnvio;
    }

    public void setEstadoEnvio(EstadoEnvio estadoEnvio) {
        this.estadoEnvio = estadoEnvio;
    }

    @Override
    public String toString() {
        return "Envio " + idEnvio + " (" + estadoEnvio + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Envio)) return false;
        return Objects.equals(idEnvio, ((Envio) obj).idEnvio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEnvio);
    }
}
