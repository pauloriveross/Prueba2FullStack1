package duoc.corredorSeguro.config;


import duoc.corredorSeguro.model.CorredorSeguro;
import duoc.corredorSeguro.repository.CorredorSeguroRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class DataLoader {


    @Bean
    CommandLineRunner cargarDatos (CorredorSeguroRepository repository){
        return args -> {
            if(repository.count() == 0){
                repository.save(CorredorSeguro.builder()
                        .rutCorredor("22.333.444-K")
                        .nombreCorredor("Juana")
                        .apellidoCorredor("De Arcos")
                        .emailCorredor("juanadearcos@seguros.cl")
                        .sueldoBaseCorredor(550000)
                        .build());

                        Faker faker = new Faker();
                        for (int i = 0; i < 10 ; i ++){
                            String nombre = faker.name().fullName();
                            String apellido = faker.name().lastName();
                            String correo = nombre.toLowerCase()+"."+apellido.toLowerCase()+"@seguros.cl";
                            repository.save(CorredorSeguro.builder()
                                    .rutCorredor(faker.idNumber().valid())
                                    .nombreCorredor(nombre)
                                    .apellidoCorredor(apellido)
                                    .emailCorredor(correo)
                                    .sueldoBaseCorredor(faker.number().numberBetween(520000,1200000))
                                    .build());



                        }



            }



        };


    }
}
