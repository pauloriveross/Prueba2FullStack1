package duoc.ventas.service;


import duoc.ventas.client.ClienteClient;
import duoc.ventas.client.VehiculoClient;
import duoc.ventas.client.VendedorClient;
import duoc.ventas.dto.VentaRequest;
import duoc.ventas.exception.*;
import duoc.ventas.model.Venta;
import duoc.ventas.repository.VentaRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class VentaService {

    private static final Logger log= LoggerFactory.getLogger(VentaService.class);

    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private VendedorClient vendedorClient;
    @Autowired
    private ClienteClient clienteClient;
    @Autowired
    private VehiculoClient vehiculoClient;

    //Listar
    public List<Venta>listarVentas(){
        return ventaRepository.findAll();
    }

    public Venta buscarVentaPorId (Integer id){
        log.info("Buscando Venta con id {}",id);
        return ventaRepository.findById(id).orElseThrow(()->
                new VentaNoEncontrada("No se encontró la venta con el id " + id));
    }



    public Venta guardarVenta(VentaRequest request,String token){
        Integer precioOficial=0;
        //Cliente
        try {
            clienteClient.obtenerClienteId(request.getIdCliente(),token);
        } catch (Exception e) {
            log.warn("Validacion fallida: Cliente {} no existe ",request.getIdCliente());

            throw new IdClienteNoEncontrado("No existe el cliente con el id " + request.getIdCliente());
        }
        //Vehiculo
        try {
            Map<String, Object> datosVehiculo = vehiculoClient.obtenerVehiculoId(request.getIdVehiculo(),token);
            precioOficial = (Integer)datosVehiculo.get("precioVehiculo");
        } catch (Exception e) {
            log.warn("Error al recuperar vehiculo {}: ID no encontrado o fallo de red", request.getIdVehiculo());

            throw new IdVehiculoNoEncontrado("No existe vehiculo con el id " + request.getIdVehiculo());
        }
        //Vendedor
        try {
            vendedorClient.obtenerVendedorId(request.getIdVendedor(),token);
        } catch (Exception e) {
            log.warn("Validación fallida: Vendedor {} no existe", request.getIdVendedor());
            throw new IdVendedorNoEncontrado("El vendedor con ID " + request.getIdVendedor() + " no existe.");
        }

    //Vehiculo duplicado
        if(ventaRepository.existsByIdVehiculo(request.getIdVehiculo())){
            throw new IdVehiculoDuplicado("No se puede crear la venta con id  : " + request.getIdVehiculo() +
                    " porque ya esta asignado a otra venta ");
        }
        Venta venta = crearDesdeRequest(request);
        venta.setPrecioVehiculo(precioOficial);
        return ventaRepository.save(venta);
    }

    public Venta crearDesdeRequest(VentaRequest request){
        log.info("Creando Venta con idVehiculo{}",request.getIdVehiculo());
        Venta venta = new Venta();
        venta.setFechaVenta(request.getFechaVenta());
        venta.setTipoPago(request.getTipoPago());
        venta.setIdCliente(request.getIdCliente());
        venta.setIdVehiculo(request.getIdVehiculo());
        venta.setIdVendedor(request.getIdVendedor());
        return venta;
    }

    public void eliminarVenta(Integer id){
        log.info("Eliminando Venta con Id {}",id);
        Venta venta = buscarVentaPorId(id);
        ventaRepository.delete(venta);
    }

    public Venta actualizarVenta(Integer idVenta, VentaRequest request,String token){
        log.info ("Actualizando Venta con id {}",idVenta);
        Venta venta = buscarVentaPorId(idVenta);
        if(ventaRepository.existsByIdVehiculoAndIdVenta(request.getIdVehiculo(),idVenta)){
            throw new IdVehiculoDuplicado("El ID Del Vehiculo se encuentra asignado a otra venta ");
        }
        validarCliente(request.getIdCliente(),token);
        Integer precioNuevo = getPrecioVehiculo(request.getIdVehiculo(), token);
        validarVendedor(request.getIdVendedor(), token);
        venta.setFechaVenta(request.getFechaVenta());
        venta.setPrecioVehiculo(precioNuevo);
        venta.setTipoPago(request.getTipoPago());
        venta.setIdCliente(request.getIdCliente());
        venta.setIdVehiculo(request.getIdVehiculo());
        venta.setIdVendedor(request.getIdVendedor());
        return ventaRepository.save(venta);

    }

    public void simularError(){
        log.error("Se ejecuta el metodo para simular un error interno");
        throw new RuntimeException("Error simulado para pruebas ");
    }



    private void validarCliente(Integer idCLiente,String token){
        try {
            clienteClient.obtenerClienteId(idCLiente,token);
        } catch (Exception e) {
            throw new IdClienteNoEncontrado("No existe un cliente con el ID "+ idCLiente);
        }
    }

    private void validarVendedor(Integer idVendedor,String token){
        try {
            vendedorClient.obtenerVendedorId(idVendedor,token);
        } catch (Exception e) {
            throw new IdVendedorNoEncontrado("No existe un vendedor con el ID " + idVendedor);
        }
    }

    private Integer getPrecioVehiculo(Integer idVehiculo,String token){
        try {
            Map<String,Object>datos=vehiculoClient.obtenerVehiculoId(idVehiculo,token);
            return Integer.parseInt(datos.get("precioVehiculo").toString());


        } catch (Exception e) {
            throw new IdVehiculoNoEncontrado("Vehiculo con el ID " + idVehiculo + " no encontrado");
        }
    }



}
