package CS2.Nexus.domain.service;

import java.util.List;

import CS2.Nexus.domain.model.Usuario;

public interface PermissionQueryService {

    /** Consulta de solo lectura de los permisos de un usuario (polimorfico por rol). */
    List<Permission> getPermissionsOf(Usuario usuario);
}
