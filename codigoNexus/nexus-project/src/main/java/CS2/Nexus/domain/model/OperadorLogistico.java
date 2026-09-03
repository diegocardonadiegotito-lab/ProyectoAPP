package CS2.Nexus.domain.model;

import CS2.Nexus.domain.model.enums.EstadoUsuario;

public class OperadorLogistico extends Usuario {

    public OperadorLogistico(String identificacion, String nombreCompleto, String correoElectronico, EstadoUsuario estado) {
        super(identificacion, nombreCompleto, correoElectronico, estado);
    }

    @Override
    public String describirRol() {
        return "Operador Logistico";
    }
}
