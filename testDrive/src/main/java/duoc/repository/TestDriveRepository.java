package duoc.repository;

import duoc.model.TestDrive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface TestDriveRepository extends JpaRepository<TestDrive, Integer>  {

    boolean existsByIdVehiculoAndFechaTestDrive(Integer idVehiculo, Date fechaTestDrive);
    boolean existsByIdVehiculoAndFechaTestDriveAndIdTestDriveNot(Integer vehiculoId, Date fechaTestDrive, Integer idTestDrive);
}
