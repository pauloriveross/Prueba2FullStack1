package duoc.seguro.dto;

public record CorredorSeguroResponse(
        Integer idCorredor
        , String rutCorredor
        ,String nombreCorredor
        ,String apellidoCorredor
        ,String emailCorredor
        ,Integer sueldoBaseCorredor
) {

}