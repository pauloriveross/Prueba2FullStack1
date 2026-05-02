package duoc.vendedor.repository;


import duoc.vendedor.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendedorRepository  extends JpaRepository<Vendedor,Integer> {

    boolean existsByRutVendedor(String rutVendedor);

    boolean existsByRutVendedorAndIdVendedorNot(String rut, Integer idVendedor);


}

