package duoc.seguro.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SeguroRequest {

    @NotNull(message = "El seguro debe tener un precio valido")
    private Integer precioSeguro;

    @NotBlank(message = "El tipo de seguro es obligatorio")
    private String tipoSeguro;

    @NotNull(message = "El id del cliente es obligatorio")
    private Integer idCliente;

    @NotNull(message = "El id del vehiculo es obligatorio")
    private Integer idVehiculo;

    @NotNull(message = "El id del corredor de seguros es obligatorio")
    private Integer idCorredorSeguro;

    @NotNull(message = "El valor de la comision es obligatorio")
    private Integer comisionSeguro;

    public SeguroRequest(){

    }

    public Integer getPrecioSeguro() {
        return precioSeguro;
    }

    public void setPrecioSeguro(Integer precioSeguro) {
        this.precioSeguro = precioSeguro;
    }

    public String getTipoSeguro() {
        return tipoSeguro;
    }

    public void setTipoSeguro(String tipoSeguro) {
        this.tipoSeguro = tipoSeguro;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public Integer getIdCorredorSeguro() {
        return idCorredorSeguro;
    }

    public void setIdCorredorSeguro(Integer idCorredorSeguro) {
        this.idCorredorSeguro = idCorredorSeguro;
    }

    public Integer getComisionSeguro() {
        return comisionSeguro;
    }

    public void setComisionSeguro(Integer comisionSeguro) {
        this.comisionSeguro = comisionSeguro;
    }
}