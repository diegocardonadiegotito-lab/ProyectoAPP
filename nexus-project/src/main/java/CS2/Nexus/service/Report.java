package CS2.Nexus.domain.service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Representa un reporte administrativo generico consultado por Supervisor
 * (OBJ-12). Tipo de apoyo minimo: la especificacion no detalla el formato
 * exacto de un reporte, solo que consolida informacion para consulta.
 */
public class Report {

    private String type;
    private LocalDateTime generatedAt;
    private Map<String, Object> data;

    public Report(String type, LocalDateTime generatedAt, Map<String, Object> data) {
        this.type = type;
        this.generatedAt = generatedAt;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
