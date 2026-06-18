package duoc.dto;

public record VendedorResponse(Integer idVendedor , String rutVendedor , String nombreVendedor,String apellidoVendedor,
                               String seccionVendedor,String turnoVendedor, Integer sueldoBaseVendedor ,
                               String emailVendedor) {
}
