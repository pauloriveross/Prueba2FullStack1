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
                new VentaNoEncontradaException("No se encontró la venta con el id " + id));
    }

    public Venta guardarVenta(VentaRequest request,String token){

        clienteClient.obtenerClienteId(request.getIdCliente(),token);
        Map<String, Object> datosVehiculo = vehiculoClient.obtenerVehiculoId(request.getIdVehiculo(), token);
        Integer precioOficial = (Integer) datosVehiculo.get("precioVehiculo");
        vendedorClient.obtenerVendedorId(request.getIdVendedor(),token);

        if(ventaRepository.existsByIdVehiculo(request.getIdVehiculo())){
            throw new IdVehiculoDuplicadoException("El vehiculo ya esta registrado en otra venta");

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

    public Venta actualizarVenta(Integer idVenta, VentaRequest request,String token) {
        log.info("Actualizando Venta con id {}", idVenta);
        Venta venta = buscarVentaPorId(idVenta);
        if (ventaRepository.existsByIdVehiculoAndIdVentaNot(request.getIdVehiculo(), idVenta)) {
            throw new IdVehiculoDuplicadoException("El ID Del Vehiculo se encuentra asignado a otra venta ");
        }
        clienteClient.obtenerClienteId(request.getIdCliente(), token);
        Map<String, Object> datos = vehiculoClient.obtenerVehiculoId(request.getIdVehiculo(), token);
        Integer precioUpdate = Integer.parseInt(datos.get("precioVehiculo").toString());
        vendedorClient.obtenerVendedorId(request.getIdVendedor(), token);

        venta.setFechaVenta(request.getFechaVenta());
        venta.setPrecioVehiculo(precioUpdate);
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






}
