package duoc.ventas.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class VentaRequest {

    @NotNull(message = "La fecha es obligatoria para registrar una venta")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaVenta;

    @NotBlank(message = "El tipo de pago es obligatorio")
    private String tipoPago;

    @NotNull(message = "El id del cliente es obligatorio")
    private Integer idCliente;
    @NotNull(message = "El id del vehiculo es obligatorio")
    private Integer idVehiculo;
    @NotNull(message = "El id del vendedor es obligatorio")
    private Integer idVendedor;


    public VentaRequest(){

    }

    public Date getFechaVenta(){
        return fechaVenta;
    }
    public void setFechaVenta(Date fechaVenta){
        this.fechaVenta = fechaVenta;

    }

    public String getTipoPago(){
        return tipoPago;
    }

    public void setTipoPago(String tipoPago){
        this.tipoPago = tipoPago;
    }

    public Integer getIdCliente(){
        return  idCliente;
    }

    public void setIdCliente(Integer idCliente){
        this.idCliente = idCliente;
    }

    public Integer getIdVehiculo(){
        return idVehiculo;
    }

    public void setIdVehiculo(Integer idVehiculo){
        this.idVehiculo  = idVehiculo;
    }

    public Integer getIdVendedor(){
        return idVendedor;
    }

    public void setIdVendedor(Integer idVendedor){
        this.idVendedor = idVendedor;
    }




}
