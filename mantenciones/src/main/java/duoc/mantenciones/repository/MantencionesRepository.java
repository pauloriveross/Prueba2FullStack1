package duoc.mantenciones.repository;

import duoc.mantenciones.model.Mantenciones;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MantencionesRepository  extends JpaRepository<Mantenciones,Integer> {
    boolean existsByIdVehiculo(Integer idMantencion);
    boolean existsByIdVehiculoAndIdMantencion(Integer idVehiculo,Integer idMantencion);


}
