package duoc.vehiculo.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class VehiculoRequest {

    @NotBlank(message = "La marca del vehiculo es obligatoria")
    private String marcaVehiculo;

    @NotBlank(message = "El modelo del vehiculo es obligatorio")
    private String modeloVehiculo;

    @NotNull(message = "El año del vehiculo es obligatorio")
    private Integer annioVehiculo;


    private String tipoVehiculo;

    @NotNull(message = "El vehiculo debe tener un precio valido")
    private Integer precioVehiculo;


    private Integer kilometrajeVehiculo;

    @NotBlank(message = "El vehiculo debe tener una patente valida")
    @Size(min = 6, max = 6, message = "La patente debe tener 6 caracteres")
    private String patenteVehiculo;

    @NotBlank(message = "El estado del vehiculo es obligatorio")
    private String estadoVehiculo;

    public VehiculoRequest(){

    }

    public String getMarcaVehiculo() {
        return marcaVehiculo;
    }

    public void setMarcaVehiculo(String marcaVehiculo) {
        this.marcaVehiculo = marcaVehiculo;
    }

    public String getModeloVehiculo() {
        return modeloVehiculo;
    }

    public void setModeloVehiculo(String modeloVehiculo) {
        this.modeloVehiculo = modeloVehiculo;
    }

    public Integer getAnnioVehiculo() {
        return annioVehiculo;
    }

    public void setAnnioVehiculo(Integer annioVehiculo) {
        this.annioVehiculo = annioVehiculo;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public Integer getPrecioVehiculo() {
        return precioVehiculo;
    }

    public void setPrecioVehiculo(Integer precioVehiculo) {
        this.precioVehiculo = precioVehiculo;
    }

    public Integer getKilometrajeVehiculo() {
        return kilometrajeVehiculo;
    }

    public void setKilometrajeVehiculo(Integer kilometrajeVehiculo) {
        this.kilometrajeVehiculo = kilometrajeVehiculo;
    }

    public String getPatenteVehiculo() {
        return patenteVehiculo;
    }

    public void setPatenteVehiculo(String patenteVehiculo) {
        this.patenteVehiculo = patenteVehiculo;
    }

    public String getEstadoVehiculo() {
        return estadoVehiculo;
    }

    public void setEstadoVehiculo(String estadoVehiculo) {
        this.estadoVehiculo = estadoVehiculo;
    }
}
