package CS2.Nexus.domain.model;

import CS2.Nexus.domain.model.enums.EstadoUsuario;

public class Vendedor extends Usuario {

    private Administrador registradoPor;

    public Vendedor(String identificacion, String nombreCompleto, String correoElectronico, EstadoUsuario estado,
                     Administrador registradoPor) {
        super(identificacion, nombreCompleto, correoElectronico, estado);
        this.registradoPor = registradoPor;
    }

    public Administrador getRegistradoPor() {
        return registradoPor;
    }

    public void setRegistradoPor(Administrador registradoPor) {
        this.registradoPor = registradoPor;
    }

    @Override
    public String describirRol() {
        String quienLoRegistro = (registradoPor != null) ? registradoPor.getNombreCompleto() : "sin asignar";
        return "Vendedor (registrado por: " + quienLoRegistro + ")";
    }
}
