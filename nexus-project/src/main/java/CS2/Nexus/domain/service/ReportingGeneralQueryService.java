package CS2.Nexus.domain.service;

import CS2.Nexus.domain.model.Supervisor;

public interface ReportingGeneralQueryService {

    /** Consulta general de reportes para el Supervisor (solo lectura). */
    Report queryReport(Supervisor supervisor, String type);
}
