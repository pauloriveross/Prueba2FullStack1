package duoc.vehiculo.repository;

import duoc.vehiculo.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoRepository extends JpaRepository<Vehiculo,Integer> {
    boolean existsByPatenteVehiculo(String patente);
    boolean existsByPatenteVehiculoAndIdVehiculoNot(String patenteVehiculo, Integer idVehiculo);
}
