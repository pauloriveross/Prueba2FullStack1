package duoc.cliente.service;

import duoc.cliente.dto.ClienteRequest;
import duoc.cliente.exception.ClienteNoEncontradoException;
import duoc.cliente.exception.RutDuplicadoException;
import duoc.cliente.model.Clientes;
import duoc.cliente.repository.ClientesRepository;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@Transactional
public class ClienteService {
    private static final Logger log =
            LoggerFactory.getLogger(ClienteService.class);

    @Autowired
    private ClientesRepository clientesRepository;

    public List<Clientes> listarTodos(){
        log.info("Listando todos los clientes");
        return clientesRepository.findAll();
    }

    public Clientes guardarCliente(ClienteRequest request){
        if(clientesRepository.existsByRutCliente(request.getRutCliente())){
            throw new RutDuplicadoException(("No se puede registrar el rut: " + request.getRutCliente() + " porque esta duplicado"));
        }

        Clientes clientes = crearDesdeRequest(request);
        return clientesRepository.save(clientes);
    }

    public Clientes buscarPorId(Integer idCliente){
        log.info("Buscando cliente con id: {}", idCliente);
        return clientesRepository.findById(idCliente)
                .orElseThrow(() -> new ClienteNoEncontradoException("No se encontro el cliente con id: " + idCliente));
    }

    public Clientes crearDesdeRequest(ClienteRequest request){
        log.info("Creando cliente con rut: {}", request.getRutCliente());
        Clientes clientes = new Clientes();
        clientes.setRutCliente(request.getRutCliente());
        clientes.setNombreCliente(request.getNombreCliente());
        clientes.setApellidoCliente(request.getApellidoCliente());
        clientes.setDireccionCliente(request.getDireccionCliente());

        return clientes;
    }

    public Clientes actualizar(Integer idCliente, ClienteRequest request){
        log.info("Actualizando cliente con id: {}", idCliente);

        Clientes clientes = buscarPorId(idCliente);
        if (clientesRepository.existsByRutClienteAndIdClienteNot(request.getRutCliente(), idCliente)){
            throw new RutDuplicadoException("El rut ya se encuentra registrado por otro cliente");
        }

        clientes.setRutCliente(request.getRutCliente());
        clientes.setNombreCliente(request.getNombreCliente());
        clientes.setApellidoCliente(request.getApellidoCliente());
        clientes.setDireccionCliente(request.getDireccionCliente());

        return clientesRepository.save(clientes);
    }

    public void eliminarCliente(Integer idCliente ){
        log.info("Eliminando Vendedor con id {}",idCliente);
        Clientes clientes = buscarPorId(idCliente);
        clientesRepository.delete(clientes);
    }

    public void simularError(){
        log.error("Se ejecuto el metodo para simular un error interno");
        throw new RuntimeException("Error simulando para pruebas");
    }
}
