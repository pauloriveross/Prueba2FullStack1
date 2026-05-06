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
       vehiculoClient.obtenerVehiculoId(request.getIdVehiculo(), token);
       mecanicoClient.obtenerMecanicoId(request.getIdMecanico(), token);
       clienteClient.obtenerClienteId(request.getIdCliente(),token);
       if(mantencionRepository.existsByIdVehiculo(request.getIdVehiculo())){
           throw new IdVehiculoDuplicadoException("El vehiculo ya se encuentra registrado en otra mantención");
       }

        Mantencion mantencion = crearDesdeRequest(request);
        return mantencionRepository.save(mantencion);
    }

    public Mantencion buscarPorId(Integer idMantencion){
        log.info("Buscando mantención con id: {}", idMantencion);
        return mantencionRepository.findById(idMantencion)
                .orElseThrow(() -> new IdMantencionNoEncontradaException("No se ha encontrado la mantención con id: "+ idMantencion));
    }

    public Mantencion crearDesdeRequest(MantencionRequest mantencionRequest){
        log.info("Creando mantención con idVehiculo: {}", mantencionRequest.getIdVehiculo());
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
        log.info("Actualizando Mantención con id {}",idMantencion);
        Mantencion mantencion = buscarPorId(idMantencion);

        if(mantencionRepository.existsByIdVehiculoAndIdMantencionNot(request.getIdVehiculo(), idMantencion)){

            throw new IdVehiculoDuplicadoException("El vehiculo ya se encuentra registrado en otra mantención");
        }
        vehiculoClient.obtenerVehiculoId(request.getIdVehiculo(), token);
        mecanicoClient.obtenerMecanicoId(request.getIdMecanico(), token);
        clienteClient.obtenerClienteId(request.getIdCliente(), token);

        mantencion.setFechaMantencion(request.getFechaMantencion());
        mantencion.setPrecioMantencion(request.getPrecioMantencion());
        mantencion.setTipoMantencion(request.getTipoMantencion());
        mantencion.setIdVehiculo(request.getIdVehiculo());
        mantencion.setIdMecanico(request.getIdMecanico());
        mantencion.setIdCliente(request.getIdCliente());

        return mantencionRepository.save(mantencion);
    }

    public void eliminarMantencion(Integer idMantencion){
        log.info("Eliminando mantención con id {}",idMantencion);
        Mantencion mantencion = buscarPorId(idMantencion);
        mantencionRepository.delete(mantencion);
    }



}