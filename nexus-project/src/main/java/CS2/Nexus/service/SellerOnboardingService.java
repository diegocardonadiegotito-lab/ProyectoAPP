package CS2.Nexus.domain.service;

import CS2.Nexus.domain.model.Administrador;
import CS2.Nexus.domain.model.Vendedor;

public interface SellerOnboardingService {

    /** Incorpora un nuevo Vendedor. Exclusivo del Administrador (DOMINIO 3). */
    Vendedor registerSeller(Administrador administrador, Object data);
}
