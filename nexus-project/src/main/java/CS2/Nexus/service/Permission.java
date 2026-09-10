package CS2.Nexus.domain.service;

/**
 * Representa un permiso individual expuesto por Usuario.getPermissions().
 * Tipo de apoyo minimo: la especificacion no detalla la estructura de un
 * permiso, solo que cada rol expone una lista de ellos de forma polimorfica.
 */
public class Permission {

    private String code;
    private String description;

    public Permission(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return code + " - " + description;
    }
}
