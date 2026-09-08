package CS2.Nexus.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

import CS2.Nexus.domain.model.enums.TipoMovimiento;

public class MovimientoInventario {

    private String idMovimiento;
    private ItemInventario itemInventario;
    private TipoMovimiento tipoMovimiento;
    private Integer cantidad;
    private LocalDateTime fechaMovimiento;

    public MovimientoInventario(String idMovimiento, ItemInventario itemInventario, TipoMovimiento tipoMovimiento,
                                 Integer cantidad, LocalDateTime fechaMovimiento) {
        this.idMovimiento = idMovimiento;
        this.itemInventario = itemInventario;
        this.tipoMovimiento = tipoMovimiento;
        this.cantidad = cantidad;
        this.fechaMovimiento = fechaMovimiento;
    }

    public String getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(String idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public ItemInventario getItemInventario() {
        return itemInventario;
    }

    public void setItemInventario(ItemInventario itemInventario) {
        this.itemInventario = itemInventario;
    }

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(LocalDateTime fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    @Override
    public String toString() {
        return "MovimientoInventario " + idMovimiento + " (" + tipoMovimiento + ", cant=" + cantidad + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof MovimientoInventario)) return false;
        return Objects.equals(idMovimiento, ((MovimientoInventario) obj).idMovimiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMovimiento);
    }
}
