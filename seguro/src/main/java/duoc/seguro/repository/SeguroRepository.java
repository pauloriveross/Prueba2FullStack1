package duoc.seguro.repository;

import duoc.seguro.model.Seguro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeguroRepository extends JpaRepository<Seguro, Integer> {
    boolean existsByIdVehiculo(Integer idVehiculo);
    boolean existsByIdVehiculoAndIdSeguroNot(Integer idVehiculo,Integer idVenta);
}