package CS2.Nexus.domain.model;

import java.util.List;

import CS2.Nexus.domain.model.enums.EstadoComprador;
import CS2.Nexus.domain.model.enums.EstadoUsuario;

public class Comprador extends Usuario {

    private String direccionPrincipal;
    private List<String> direccionesAdicionales;
    private EstadoComprador estadoComercial;

    public Comprador(String identificacion, String nombreCompleto, String correoElectronico, EstadoUsuario estado,
                      String direccionPrincipal, List<String> direccionesAdicionales, EstadoComprador estadoComercial) {
        super(identificacion, nombreCompleto, correoElectronico, estado);
        this.direccionPrincipal = direccionPrincipal;
        this.direccionesAdicionales = direccionesAdicionales;
        this.estadoComercial = estadoComercial;
    }

    public String getDireccionPrincipal() {
        return direccionPrincipal;
    }

    public void setDireccionPrincipal(String direccionPrincipal) {
        this.direccionPrincipal = direccionPrincipal;
    }

    public List<String> getDireccionesAdicionales() {
        return direccionesAdicionales;
    }

    public void setDireccionesAdicionales(List<String> direccionesAdicionales) {
        this.direccionesAdicionales = direccionesAdicionales;
    }

    public EstadoComprador getEstadoComercial() {
        return estadoComercial;
    }

    public void setEstadoComercial(EstadoComprador estadoComercial) {
        this.estadoComercial = estadoComercial;
    }

    // Polimorfismo: esta es LA version que se ejecuta cuando el objeto real es un Comprador,
    // aunque se este llamando a traves de una variable declarada como Usuario.
    @Override
    public String describirRol() {
        return "Comprador (estado comercial: " + estadoComercial + ")";
    }
}
