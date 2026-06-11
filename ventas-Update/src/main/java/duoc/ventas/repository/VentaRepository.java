package duoc.ventas.repository;


import duoc.ventas.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository  extends JpaRepository<Venta,Integer> {

    boolean existsByIdVehiculo(Integer idVehiculo);

    boolean existsByIdVehiculoAndIdVentaNot(Integer idVehiculo,Integer idVenta);
}
