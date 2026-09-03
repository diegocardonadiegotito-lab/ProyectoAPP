package CS2.Nexus.domain.model;

import java.util.Objects;

import CS2.Nexus.domain.model.enums.TipoBodega;

public class Bodega {

    private String idBodega;
    private TipoBodega tipoBodega;
    private Vendedor propietario;

    public Bodega(String idBodega, TipoBodega tipoBodega, Vendedor propietario) {
        this.idBodega = idBodega;
        this.tipoBodega = tipoBodega;
        this.propietario = propietario;
    }

    public String getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(String idBodega) {
        this.idBodega = idBodega;
    }

    public TipoBodega getTipoBodega() {
        return tipoBodega;
    }

    public void setTipoBodega(TipoBodega tipoBodega) {
        this.tipoBodega = tipoBodega;
    }

    public Vendedor getPropietario() {
        return propietario;
    }

    public void setPropietario(Vendedor propietario) {
        this.propietario = propietario;
    }

    @Override
    public String toString() {
        return "Bodega " + idBodega + " (" + tipoBodega + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Bodega)) return false;
        return Objects.equals(idBodega, ((Bodega) obj).idBodega);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBodega);
    }
}
