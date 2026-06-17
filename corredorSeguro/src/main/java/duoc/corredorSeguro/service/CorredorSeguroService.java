package duoc.corredorSeguro.service;


import duoc.corredorSeguro.dto.CorredorSeguroRequest;
import duoc.corredorSeguro.exception.CorredorNoEncontrado;
import duoc.corredorSeguro.exception.RutDuplicadoCorredor;
import duoc.corredorSeguro.model.CorredorSeguro;
import duoc.corredorSeguro.repository.CorredorSeguroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
@Transactional
public class CorredorSeguroService {

    private static  final Logger log = LoggerFactory.getLogger(CorredorSeguroService.class);

    @Autowired
    private CorredorSeguroRepository corredorSeguroRepository;

    public List<CorredorSeguro>listarTodos(){return  corredorSeguroRepository.findAll();}

    public CorredorSeguro buscarPorId(Integer id){
        log.info("Buscando Corredor con id {}",id);
        return corredorSeguroRepository.findById(id).orElseThrow(()->
                new CorredorNoEncontrado(
                        "No se encontró corredor con ese id " + id
                ));
    }


    public CorredorSeguro guardarCorredor(CorredorSeguroRequest request){
        if(corredorSeguroRepository.existsByRutCorredor(request.getRutCorredor())){
            throw new RutDuplicadoCorredor("No se puede registar el rut : " + request.getRutCorredor() +
                    "porque esta duplicado");
        }

        CorredorSeguro corredorSeguro = crearDesdeRequest(request);
        return corredorSeguroRepository.save(corredorSeguro);
    }


    private CorredorSeguro crearDesdeRequest(CorredorSeguroRequest request){
        log.info("Creando Corredor Con Run:{}",request.getRutCorredor());
        CorredorSeguro corredorSeguro = new CorredorSeguro();
        corredorSeguro.setRutCorredor(request.getRutCorredor());
        corredorSeguro.setNombreCorredor(request.getNombreCorredor());
        corredorSeguro.setApellidoCorredor(request.getApellidoCorredor());
        corredorSeguro.setEmailCorredor(request.getEmailCorredor());
        corredorSeguro.setSueldoBaseCorredor(request.getSueldoCorredor());
        return corredorSeguro;
    }

    public void eliminarCorredor(Integer id ){
        log.info("Eliminando Corredor con id {}",id);
        CorredorSeguro corredorSeguro = buscarPorId(id);
        corredorSeguroRepository.delete(corredorSeguro);
    }



    public CorredorSeguro actualizarCorredor(Integer id,CorredorSeguroRequest request){
        log.info("Actualizando Corredor con id {}",id);
        CorredorSeguro corredorUpdate = buscarPorId(id);

        if(corredorSeguroRepository.existsByRutCorredorAndIdCorredor(request.getRutCorredor(), id)){
            throw new RutDuplicadoCorredor("El rut ya se encuentra registrado por otro corredor");
        }

        corredorUpdate.setRutCorredor(request.getRutCorredor());
        corredorUpdate.setNombreCorredor(request.getNombreCorredor());
        corredorUpdate.setApellidoCorredor(request.getApellidoCorredor());
        corredorUpdate.setEmailCorredor(request.getEmailCorredor());
        corredorUpdate.setSueldoBaseCorredor(request.getSueldoCorredor());
        return corredorSeguroRepository.save(corredorUpdate);

    }







}
