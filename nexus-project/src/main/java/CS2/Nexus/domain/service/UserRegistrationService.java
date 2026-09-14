package CS2.Nexus.domain.service;

import CS2.Nexus.domain.model.Usuario;

public interface UserRegistrationService {

    /**
     * Crea un nuevo Usuario (de cualquier subclase de rol).
     * Precondicion: identificacion y correo no deben existir previamente;
     * nombreCompleto no puede estar vacio (restriccion DOMINIO 1).
     *
     * @param data      datos basicos del usuario (placeholder: la especificacion
     *                  no detalla su estructura exacta).
     * @param roleType  subclase concreta de Usuario a crear (Comprador, Vendedor, etc.).
     * @return el Usuario creado.
     */
    Usuario registerUser(Object data, Class<? extends Usuario> roleType);
}
