package duoc.ventas.service;


import duoc.ventas.dto.ClienteResponse;
import duoc.ventas.dto.VehiculoResponse;
import duoc.ventas.dto.VendedorResponse;
import duoc.ventas.dto.VentaRequest;
import duoc.ventas.exception.*;
import duoc.ventas.model.Venta;
import duoc.ventas.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class VentaService {


    private final VentaRepository ventaRepository;
    private final WebClient.Builder webClientBuilder;

    @Value("${services.cliente.url:http://CLIENTE}")
    private String clienteServiceUrl;
    @Value("${services.vehiculo.url:http://VEHICULO}")
    private String vehiculoServiceUrl;

    @Value("${services.vendedor.url:http://VENDEDOR}")
    private String vendedorServiceUrl;



    ///////////////////////////////////////////////////////////////////////////
    /// Métodos
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

        ClienteResponse cliente = webClientBuilder.build().get()
                        .uri(clienteServiceUrl + "/api/v1/clientes/{idCliente}",request.getIdCliente())
                         .header("Authorization",token)
                         .retrieve().bodyToMono(ClienteResponse.class).block();
        VehiculoResponse vehiculo = webClientBuilder.build().get()
                .uri(vehiculoServiceUrl + "/api/v1/vehiculos/{idVehiculo}",request.getIdVehiculo())
                .header("Authorization",token)
                .retrieve().bodyToMono(VehiculoResponse.class).block();
        VendedorResponse vendedor = webClientBuilder.build().get()
                        .uri(vendedorServiceUrl + "/api/v1/vendedores/{id}",request.getIdVendedor())
                         .header("Authorization",token)
                                 .retrieve().bodyToMono(VendedorResponse.class).block();


        // Validar que no sea nula la comunicacion
        if(cliente == null){
            throw new IllegalArgumentException("El cliente con el id " + request.getIdCliente()+ " no existe");
        }

        if (vehiculo == null) {
            throw new IdVehiculoNoEncontradoException("El vehiculo con el id " + request.getIdVehiculo() + "no existe");
        }

        if(vendedor == null ){
            throw new IdVendedorNoEncontradoException("El vendedor con el id " + request.getIdVendedor() + " no existe");
        }


        Integer precio =  vehiculo.precioVehiculo();

        if(ventaRepository.existsByIdVehiculo(request.getIdVehiculo())){
            throw new IdVehiculoDuplicadoException("El vehiculo ya esta registrado en otra venta");

        }
        // Operación para obtener la comision de la venta
        double porcentajeComision= 0.03;
        Integer comisionCalculada =  (int) (precio * porcentajeComision);

        Venta venta = Venta.builder()
                .fechaVenta(request.getFechaVenta())
                .tipoPago(request.getTipoPago())
                .idCliente(cliente.idCliente())
                .idVehiculo(vehiculo.idVehiculo())
                .idVendedor(vendedor.idVendedor())
                .precioVehiculo(precio)
                .comisionVenta(comisionCalculada)
                .build();
        log.info("Registrando Venta ....");
        return ventaRepository.save(venta);
    }

   // public Venta crearDesdeRequest(VentaRequest request){
        //log.info("Creando Venta con idVehiculo{}",request.getIdVehiculo());
       // Venta venta = new Venta();
        //venta.setFechaVenta(request.getFechaVenta());
        //venta.setTipoPago(request.getTipoPago());
       // venta.setIdCliente(request.getIdCliente());
        //venta.setIdVehiculo(request.getIdVehiculo());
        //venta.setIdVendedor(request.getIdVendedor());
        //return venta;
    //}

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



        double porcentajeComision= 0.03;


        venta.setFechaVenta(request.getFechaVenta());

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
