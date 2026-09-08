package CS2.Nexus.domain.model;

import java.util.Objects;

import CS2.Nexus.domain.model.enums.EstadoUsuario;

public abstract class Usuario {

    private String identificacion;
    private String nombreCompleto;
    private String correoElectronico;
    private EstadoUsuario estado;

    public Usuario(String identificacion, String nombreCompleto, String correoElectronico, EstadoUsuario estado) {
        this.identificacion = identificacion;
        this.nombreCompleto = nombreCompleto;
        this.correoElectronico = correoElectronico;
        this.estado = estado;
    }

    // ---- Getters y Setters (encapsulamiento) ----

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }

    public abstract String describirRol();

    @Override
    public String toString() {
        return describirRol() + " | " + nombreCompleto + " | " + correoElectronico + " | " + estado;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Usuario)) return false;
        Usuario otro = (Usuario) obj;
        return Objects.equals(identificacion, otro.identificacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificacion);
    }
}
