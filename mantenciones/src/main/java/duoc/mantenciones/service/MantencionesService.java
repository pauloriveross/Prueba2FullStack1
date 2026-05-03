package duoc.mantenciones.service;

import duoc.mantenciones.client.ClienteClient;
import duoc.mantenciones.client.MecanicoClient;
import duoc.mantenciones.client.VehiculoClient;
import duoc.mantenciones.dto.MantencionesRequest;
import duoc.mantenciones.exception.IdMantencionNoEncontradaException;
import duoc.mantenciones.exception.IdVehiculoDuplicadoException;
import duoc.mantenciones.model.Mantenciones;
import duoc.mantenciones.repository.MantencionesRepository;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import java.util.List;

@Service
@Transactional
public class MantencionesService {

    private static final Logger log =
            LoggerFactory.getLogger(MantencionesService.class);


    @Autowired
    private MantencionesRepository mantencionesRepository;
    @Autowired
    private ClienteClient clienteClient;
    @Autowired
    private VehiculoClient vehiculoClient;
    @Autowired
    private MecanicoClient mecanicoClient;


    public List<Mantenciones> listarTodos(){
        log.info("Listando todas las mantenciones");
        return mantencionesRepository.findAll();
    }

    public Mantenciones guardarMantencion(MantencionesRequest request){
        if(mantencionesRepository.existsByIdVehiculo(request.getIdVehiculo())){
            throw new IdVehiculoDuplicadoException("No se puede realizar dos mantenciones al mismo tiempo al mismo vehiculo");
        }
        Mantenciones mantenciones = crearDesdeRequest(request);
        return mantencionesRepository.save(mantenciones);
    }

    public Mantenciones buscarPorId(Integer idMantencion){
        log.info("Buscando mantencion con id: {}", idMantencion);
        return mantencionesRepository.findById(idMantencion)
                .orElseThrow(() -> new IdMantencionNoEncontradaException("No se ha encontrado la mantencion con id: "+ idMantencion));
    }

    public Mantenciones crearDesdeRequest(MantencionesRequest mantencionesRequest){
        log.info("Creando mantencion con idVehiculo: {}", mantencionesRequest.getIdVehiculo());
        Mantenciones mantencion = new Mantenciones();
        mantencion.setFechaMantencion(mantencionesRequest.getFechaMantencion());
        mantencion.setPrecioMantencion(mantencionesRequest.getPrecioMantencion());
        mantencion.setTipoMantencion(mantencionesRequest.getTipoMantencion());
        mantencion.setIdVehiculo(mantencionesRequest.getIdVehiculo());
        mantencion.setIdMecanico(mantencionesRequest.getIdMecanico());
        mantencion.setIdCliente(mantencionesRequest.getIdCliente());
        return mantencion;
    }


    public Mantenciones actualizarMantencion(Integer idMantencion, MantencionesRequest request) {
        log.info("Actualizando Mantencion con id {}",idMantencion);
        Mantenciones mantenciones = buscarPorId(idMantencion);
        if (mantencionesRepository.existsByIdVehiculo(request.getIdVehiculo())
        ) {
            throw new IdVehiculoDuplicadoException("El vehiculo ya tiene una mantencion vigente");
        }

        mantenciones.setFechaMantencion(request.getFechaMantencion());
        mantenciones.setPrecioMantencion(request.getPrecioMantencion());
        mantenciones.setTipoMantencion(request.getTipoMantencion());
        mantenciones.setIdVehiculo(request.getIdVehiculo());
        mantenciones.setIdMecanico(request.getIdMecanico());
        mantenciones.setIdCliente(request.getIdCliente());

        return mantencionesRepository.save(mantenciones);
    }

    public void eliminarMantencion(Integer idMantencion){
        log.info("Eliminando mantencion con id {}",idMantencion);
        Mantenciones mantenciones = buscarPorId(idMantencion);
        mantencionesRepository.delete(mantenciones);
    }

}