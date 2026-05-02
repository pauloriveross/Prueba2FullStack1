package duoc.vendedor.service;

import duoc.vendedor.dto.VendedorRequest;
import duoc.vendedor.exception.RutDuplicadoVendedor;
import duoc.vendedor.exception.VendedorNoEncontrado;
import duoc.vendedor.model.Vendedor;
import duoc.vendedor.repository.VendedorRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class VendedorService {

    private static final Logger log= LoggerFactory.getLogger(VendedorService.class);

    @Autowired
    private VendedorRepository vendedorRepository;

    public List<Vendedor>listarTodos(){return vendedorRepository.findAll();}

    public Vendedor buscarporId(Integer id){
        log.info("Buscando Vendedor con id {}",id);
        return vendedorRepository.findById(id).orElseThrow(()->
                new VendedorNoEncontrado(
                        "No se encontró vendedor con el id " + id
                ));

    }

    public Vendedor guardarVendedor(VendedorRequest request){
        if(vendedorRepository.existsByRutVendedor(request.getRutVendedor())){
            throw new RutDuplicadoVendedor("No se puede registrar el rut : " + request.getRutVendedor() +
                    " porque esta duplicado");
        }
        Vendedor vendedor = crearDesdeRequest(request);
        return vendedorRepository.save(vendedor);
    }

    private Vendedor crearDesdeRequest(VendedorRequest request){
        log.info("Creando Vendedor Con Run:{}",request.getRutVendedor());
        Vendedor vendedor = new Vendedor();
        vendedor.setRutVendedor(request.getRutVendedor());
        vendedor.setNombreVendedor(request.getNombreVendedor());
        vendedor.setApellidoVendedor(request.getApellidoVendedor());
        vendedor.setSeccionVendedor(request.getSeccionVendedor());
        vendedor.setTurnoVendedor(request.getTurnoVendedor());
        vendedor.setEmailVendedor(request.getEmailVendedor());
        return vendedor;
    }

    public void eliminarVendedor(Integer id ){
        log.info("Eliminando Vendedor con id {}",id);
        Vendedor vendedor = buscarporId(id);
        vendedorRepository.delete(vendedor);
    }

    public Vendedor actualizarVendedor(Integer idVendedor, VendedorRequest request){
        log.info ("Actualizando Vendedor con id {}",idVendedor);
       Vendedor vendedor = buscarporId(idVendedor);
       if(vendedorRepository.existsByRutVendedorAndIdVendedorNot(request.getRutVendedor(),idVendedor)){
           throw new RutDuplicadoVendedor("El Rut ya se encuentra registrado por otro vendedor");
       }
       vendedor.setRutVendedor(request.getRutVendedor());
       vendedor.setNombreVendedor(request.getNombreVendedor());
       vendedor.setApellidoVendedor(request.getApellidoVendedor());
       vendedor.setSeccionVendedor(request.getSeccionVendedor());
       vendedor.setTurnoVendedor(request.getTurnoVendedor());
       vendedor.setEmailVendedor(request.getEmailVendedor());
       return vendedorRepository.save(vendedor);
    }

    public void simularError(){
        log.error("Se ejecuta el metodo para simular un error interno");
                throw new RuntimeException("Error simulado para pruebas ");
    }







}// fin Service
