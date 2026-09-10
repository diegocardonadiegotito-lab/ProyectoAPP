package CS2.Nexus.domain.service;

import CS2.Nexus.domain.model.Usuario;

public interface UserStatusService {

    /** Bloquea un usuario existente. Solo puede ejecutarlo un Administrador. */
    void blockUser(Usuario usuario);

    /** Reactiva un usuario bloqueado. Solo puede ejecutarlo un Administrador. */
    void reactivateUser(Usuario usuario);
}
