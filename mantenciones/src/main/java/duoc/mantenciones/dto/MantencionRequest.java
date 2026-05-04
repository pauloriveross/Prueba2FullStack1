package duoc.mantenciones.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

public class MantencionRequest {

    @NotNull(message = "La fecha es obligatoria para registrar una mantención")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaMantencion;

    @NotNull(message = "El precio de la mantención es obligatorio")
    private Integer precioMantencion;

    @NotBlank(message = "El tipo de mantención es obligatorio")
    private String tipoMantencion;

    @NotNull(message = "El id del vehiculo es obligatorio")
    private Integer idVehiculo;

    @NotNull(message = "El id del mecánico es obligatorio")
    private Integer idMecanico;

    @NotNull(message = "El id del cliente es obligatorio")
    private Integer idCliente;

    public MantencionRequest(){}

    public Date getFechaMantencion() {
        return fechaMantencion;
    }

    public void setFechaMantencion(Date fechaMantencion) {
        this.fechaMantencion = fechaMantencion;
    }

    public Integer getPrecioMantencion() {
        return precioMantencion;
    }

    public void setPrecioMantencion(Integer precioMantencion) {
        this.precioMantencion = precioMantencion;
    }

    public String getTipoMantencion() {
        return tipoMantencion;
    }

    public void setTipoMantencion(String tipoMantencion) {
        this.tipoMantencion = tipoMantencion;
    }

    public Integer getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public Integer getIdMecanico() {
        return idMecanico;
    }

    public void setIdMecanico(Integer idMecanico) {
        this.idMecanico = idMecanico;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }
}




