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

    // ---- Abstraccion: metodo sin cuerpo que CADA subclase esta obligada a implementar ----
    // Este es el metodo que habilita el polimorfismo: cuando se llama sobre una
    // referencia de tipo Usuario, Java ejecuta en tiempo de ejecucion la version
    // de la subclase real del objeto (Comprador, Vendedor, etc.), no una version generica.
    public abstract String describirRol();

    // toString() de la clase padre reutiliza el metodo abstracto: por eso
    // imprimir cualquier Usuario (sin importar el tipo real) ya muestra el rol correcto.
    @Override
    public String toString() {
        return describirRol() + " | " + nombreCompleto + " | " + correoElectronico + " | " + estado;
    }

    // Dos usuarios se consideran el mismo si comparten identificacion (regla de unicidad del dominio).
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
