package CS2.Nexus.domain.service;

import CS2.Nexus.domain.model.Usuario;

public interface AuthenticationService {

    /**
     * Autentica a un usuario mediante correo y credenciales.
     * Precondicion: el usuario debe estar en estado Activo (isActive()).
     */
    Usuario authenticate(String email, String credentials);
}
