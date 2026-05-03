package duoc.vehiculo.service;

import duoc.vehiculo.dto.VehiculoRequest;
import duoc.vehiculo.exception.PatenteDuplicadaException;
import duoc.vehiculo.exception.VehiculoNoEncontradoException;
import duoc.vehiculo.model.Vehiculo;
import duoc.vehiculo.repository.VehiculoRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class VehiculoService {
    private static final Logger log =
            LoggerFactory.getLogger(VehiculoService.class);

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public List<Vehiculo> listarVehiculos(){
        log.info("Listando todos los vehiculos");
        return vehiculoRepository.findAll();
    }

    public Vehiculo buscarPorId(Integer idVehiculo){
        log.info("Buscando Vehiculo con id: {}",idVehiculo);
        return vehiculoRepository.findById(idVehiculo).
                orElseThrow(() -> new VehiculoNoEncontradoException("No se encontro vehiculo con id: " + idVehiculo));
    }

    public Vehiculo crearVehiculo(VehiculoRequest vehiculoRequest){
    log.info("Creando Vehiculo con patente: {}",vehiculoRequest.getPatenteVehiculo());
        boolean existe = vehiculoRepository.existsByPatenteVehiculo(vehiculoRequest.getPatenteVehiculo());
        if(existe){
            throw new PatenteDuplicadaException("Ya existe un auto con esa patente");
        }
    Vehiculo vehiculo = new Vehiculo();
    vehiculo.setMarcaVehiculo(vehiculoRequest.getMarcaVehiculo());
    vehiculo.setModeloVehiculo(vehiculoRequest.getModeloVehiculo());
    vehiculo.setAnnioVehiculo(vehiculoRequest.getAnnioVehiculo());
    vehiculo.setTipoVehiculo(vehiculoRequest.getTipoVehiculo());
    vehiculo.setPrecioVehiculo(vehiculoRequest.getPrecioVehiculo());
    vehiculo.setKilometrajeVehiculo(vehiculoRequest.getKilometrajeVehiculo());
    vehiculo.setPatenteVehiculo(vehiculoRequest.getPatenteVehiculo());
    vehiculo.setEstadoVehiculo(vehiculoRequest.getEstadoVehiculo());
    return vehiculoRepository.save(vehiculo);
    }

    public Vehiculo actualizar(Integer idVehiculo, VehiculoRequest vehiculoRequest){
        log.info("Actualizando Vehiculo con id: {}",idVehiculo);
        Vehiculo vehiculo = buscarPorId(idVehiculo);
        boolean existe = vehiculoRepository.existsByPatenteVehiculoAndIdVehiculoNot(vehiculoRequest.getPatenteVehiculo(),idVehiculo);
        if(existe){
            throw new PatenteDuplicadaException("Ya existe un auto con esa patente");
        }
        vehiculo.setMarcaVehiculo(vehiculoRequest.getMarcaVehiculo());
        vehiculo.setModeloVehiculo(vehiculoRequest.getModeloVehiculo());
        vehiculo.setAnnioVehiculo(vehiculoRequest.getAnnioVehiculo());
        vehiculo.setTipoVehiculo(vehiculoRequest.getTipoVehiculo());
        vehiculo.setPrecioVehiculo(vehiculoRequest.getPrecioVehiculo());
        vehiculo.setKilometrajeVehiculo(vehiculoRequest.getKilometrajeVehiculo());
        vehiculo.setPatenteVehiculo(vehiculoRequest.getPatenteVehiculo());
        vehiculo.setEstadoVehiculo(vehiculoRequest.getEstadoVehiculo());
        return vehiculoRepository.save(vehiculo);
    }

    public void eliminar(Integer idVehiculo){
        log.info("Eliminando Vehiculo con id: {}", idVehiculo);
        Vehiculo vehiculo = buscarPorId(idVehiculo);
        vehiculoRepository.delete(vehiculo);
    }
}
