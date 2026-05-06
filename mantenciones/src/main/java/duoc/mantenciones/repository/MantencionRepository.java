package duoc.mantenciones.repository;

import duoc.mantenciones.model.Mantencion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MantencionRepository extends JpaRepository<Mantencion,Integer> {
    boolean existsByIdVehiculo(Integer idMantencion);
    boolean existsByIdVehiculoAndIdMantencionNot(Integer idVehiculo,Integer idMantencion);


}
