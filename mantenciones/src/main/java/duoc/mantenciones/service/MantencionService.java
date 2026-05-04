package duoc.mantenciones.service;

import duoc.mantenciones.client.ClienteClient;
import duoc.mantenciones.client.MecanicoClient;
import duoc.mantenciones.client.VehiculoClient;
import duoc.mantenciones.dto.MantencionRequest;
import duoc.mantenciones.exception.*;
import duoc.mantenciones.model.Mantencion;
import duoc.mantenciones.repository.MantencionRepository;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MantencionService {

    private static final Logger log =
            LoggerFactory.getLogger(MantencionService.class);


    @Autowired
    private MantencionRepository mantencionRepository;
    @Autowired
    private ClienteClient clienteClient;
    @Autowired
    private VehiculoClient vehiculoClient;
    @Autowired
    private MecanicoClient mecanicoClient;


    public List<Mantencion> listarTodos(){
        log.info("Listando todas las mantenciones");
        return mantencionRepository.findAll();
    }

    public Mantencion guardarMantencion(MantencionRequest request, String token){
        //Cliente
        try {
            clienteClient.obtenerClienteId(request.getIdCliente(),token);
        } catch (Exception e) {
            log.warn("Validacion fallida: Cliente {} no existe ",request.getIdCliente());

            throw new IdClienteNoEncontradoException("No existe el cliente con el id " + request.getIdCliente());
        }
        //Vehiculo
        try {
            Map<String, Object> datosVehiculo = vehiculoClient.obtenerVehiculoId(request.getIdVehiculo(),token);
        } catch (Exception e) {
            log.warn("Error al recuperar vehiculo {}: ID no encontrado o fallo de red", request.getIdVehiculo());

            throw new IdVehiculoNoEncontradoException("No existe vehiculo con el id " + request.getIdVehiculo());
        }
        //Mecanico
        try {
            mecanicoClient.obtenerMecanicoId(request.getIdMecanico(),token);
        } catch (Exception e) {
            log.warn("Validación fallida: Vendedor {} no existe", request.getIdMecanico());
            throw new IdMecanicoNoEncontradoException("El vendedor con ID " + request.getIdMecanico() + " no existe.");
        }

        //Vehiculo duplicado
        if(mantencionRepository.existsByIdVehiculo(request.getIdVehiculo())){
            throw new IdVehiculoDuplicadoException("No se puede crear la venta con id  : " + request.getIdVehiculo() +
                    " porque ya esta asignado a otra venta ");
        }
        Mantencion mantencion = crearDesdeRequest(request);
        return mantencionRepository.save(mantencion);
    }

    public Mantencion buscarPorId(Integer idMantencion){
        log.info("Buscando mantencion con id: {}", idMantencion);
        return mantencionRepository.findById(idMantencion)
                .orElseThrow(() -> new IdMantencionNoEncontradaException("No se ha encontrado la mantencion con id: "+ idMantencion));
    }

    public Mantencion crearDesdeRequest(MantencionRequest mantencionRequest){
        log.info("Creando mantencion con idVehiculo: {}", mantencionRequest.getIdVehiculo());
        Mantencion mantencion = new Mantencion();
        mantencion.setFechaMantencion(mantencionRequest.getFechaMantencion());
        mantencion.setPrecioMantencion(mantencionRequest.getPrecioMantencion());
        mantencion.setTipoMantencion(mantencionRequest.getTipoMantencion());
        mantencion.setIdVehiculo(mantencionRequest.getIdVehiculo());
        mantencion.setIdMecanico(mantencionRequest.getIdMecanico());
        mantencion.setIdCliente(mantencionRequest.getIdCliente());
        return mantencion;
    }


    public Mantencion actualizarMantencion(Integer idMantencion, MantencionRequest request, String token) {
        log.info("Actualizando Mantencion con id {}",idMantencion);
        Mantencion mantencion = buscarPorId(idMantencion);
        if (mantencionRepository.existsByIdVehiculoAndIdMantencion(request.getIdVehiculo(),idMantencion)
        ) {
            throw new IdVehiculoDuplicadoException("El vehiculo ya tiene una mantencion vigente");
        }
        validarCliente(request.getIdCliente(),token);
        validarMecanico(request.getIdMecanico(), token);
        mantencion.setFechaMantencion(request.getFechaMantencion());
        mantencion.setPrecioMantencion(request.getPrecioMantencion());
        mantencion.setTipoMantencion(request.getTipoMantencion());
        mantencion.setIdVehiculo(request.getIdVehiculo());
        mantencion.setIdMecanico(request.getIdMecanico());
        mantencion.setIdCliente(request.getIdCliente());

        return mantencionRepository.save(mantencion);
    }

    public void eliminarMantencion(Integer idMantencion){
        log.info("Eliminando mantencion con id {}",idMantencion);
        Mantencion mantencion = buscarPorId(idMantencion);
        mantencionRepository.delete(mantencion);
    }
    private void validarMecanico(Integer idMecanico,String token){
        try {
            mecanicoClient.obtenerMecanicoId(idMecanico,token);
        } catch (Exception e) {
            throw new IdMecanicoNoEncontradoException("No existe un mecanico con el ID " + idMecanico);
        }
    }


    private void validarCliente(Integer idCLiente,String token){
        try {
            clienteClient.obtenerClienteId(idCLiente,token);
        } catch (Exception e) {
            throw new IdClienteNoEncontradoException("No existe un cliente con el ID "+ idCLiente);
        }
    }
}