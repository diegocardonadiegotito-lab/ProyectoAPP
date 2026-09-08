package CS2.Nexus.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Factura {

    private String idFactura;
    private Pedido pedido;
    private LocalDateTime fechaEmision;
    private BigDecimal montoTotal;

    public Factura(String idFactura, Pedido pedido, LocalDateTime fechaEmision, BigDecimal montoTotal) {
        this.idFactura = idFactura;
        this.pedido = pedido;
        this.fechaEmision = fechaEmision;
        this.montoTotal = montoTotal;
    }

    public String getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(String idFactura) {
        this.idFactura = idFactura;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    @Override
    public String toString() {
        return "Factura " + idFactura + " - " + montoTotal;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Factura)) return false;
        return Objects.equals(idFactura, ((Factura) obj).idFactura);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFactura);
    }
}
