package CS2.Nexus.domain.service;

import CS2.Nexus.domain.model.Usuario;

public interface AccessControlService {

    /**
     * Valida si un usuario tiene acceso a un recurso determinado (RG-03).
     * Invocado por el resto de servicios antes de ejecutar cualquier operacion protegida.
     */
    boolean validateAccess(Usuario usuario, String resource);
}
