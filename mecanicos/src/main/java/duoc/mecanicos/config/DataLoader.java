package duoc.mecanicos.config;

import duoc.mecanicos.model.Mecanico;
import duoc.mecanicos.repository.MecanicoRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner cargarDatos (MecanicoRepository mecanicoRepository){
        return args ->{
            if(mecanicoRepository.count() == 0){
                mecanicoRepository.save(Mecanico.builder()
                        .rutMecanico("11.222.333-4")
                        .nombreMecanico("Yooyeon")
                        .apellidoMecanico("Kim")
                        .sueldoBaseMecanico(550000)
                        .build());
                Faker faker = new Faker();
                for (int  i = 0 ; i < 10 ; i++){
                    String nombreMecanico = faker.name().firstName();
                    String apellidoMecanico = faker.name().lastName();
                    mecanicoRepository.save(Mecanico.builder()
                            .rutMecanico(faker.idNumber().valid())
                            .nombreMecanico(nombreMecanico)
                            .apellidoMecanico(apellidoMecanico)
                            .sueldoBaseMecanico(faker.number().numberBetween(520000,1200000))
                            .build());
                }
            }
        };
    }
}
