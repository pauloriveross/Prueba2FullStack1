package duoc.cliente.repository;

import duoc.cliente.model.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientesRepository extends JpaRepository<Clientes, Integer> {
    boolean existsByRutCliente(String rutCliente);

    boolean existsByRutClienteAndIdClienteNot(String rut, Integer idCliente);
}
