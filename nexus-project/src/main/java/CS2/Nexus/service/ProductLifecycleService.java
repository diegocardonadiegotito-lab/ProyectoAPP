package CS2.Nexus.domain.service;

import CS2.Nexus.domain.model.Producto;

public interface ProductLifecycleService {

    /** Publica un producto (SUSPENDIDO -> PUBLICADO). Solo el vendedor dueño puede ejecutarlo. */
    void publish(Producto producto);

    /** Suspende un producto (PUBLICADO -> SUSPENDIDO). */
    void suspend(Producto producto);

    /** Descontinua un producto. Operacion irreversible: no existe metodo para reactivar. */
    void discontinue(Producto producto);
}
