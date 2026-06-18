package duoc.repository;

import duoc.model.TestDrive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestDriveRepository extends JpaRepository<TestDrive, Integer>  {

    boolean existsByIdVehiculo(Integer idVehiculo);
}
