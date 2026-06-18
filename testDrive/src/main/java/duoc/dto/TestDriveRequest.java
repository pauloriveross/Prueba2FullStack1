package duoc.dto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;


public class TestDriveRequest {

    @NotNull(message = "El Id del Test Drive es obligatorio")
    private Integer idTestDrive;

    @Column(nullable = false)
    private String fechaTestDrive;

    @NotNull(message = "El id del cliente es obligatorio")
    private Integer idCliente;

    @NotNull(message = "El id del vehiculo es obligatorio")
    private Integer idVehiculo;

    @NotNull(message = "El id del vendedor es obligatorio")
    private Integer idVendedor;

    public TestDriveRequest(){

    }

    public Integer getIdTestDrive() {
        return idTestDrive;
    }

    public void setIdTestDrive(Integer idTestDrive) {
        this.idTestDrive = idTestDrive;
    }

    public String getFechaTestDrive() {
        return fechaTestDrive;
    }

    public void setFechaTestDrive(String fechaTestDrive) {
        this.fechaTestDrive = fechaTestDrive;
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

    public Integer getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Integer idVendedor) {
        this.idVendedor = idVendedor;
    }
}
