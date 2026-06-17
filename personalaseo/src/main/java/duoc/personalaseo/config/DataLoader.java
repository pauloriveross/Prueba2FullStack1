package duoc.personalaseo.config;

import duoc.personalaseo.model.PersonalAseo;
import duoc.personalaseo.repository.PersonalAseoRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {
    @Bean
    public CommandLineRunner cargarDatos (PersonalAseoRepository personalAseoRepository){
        return args -> {
            if(personalAseoRepository.count() == 0){
                personalAseoRepository.save(PersonalAseo.builder()
                        .rutPersonalAseo("11.999.100-1")
                        .nombrePersonalAseo("Oliver")
                        .apellidoPersonalAseo("Tree")
                        .sueldoPersonalAseo(553553)
                        .build());

                Faker faker = new Faker();

                for(int i = 0 ; i < 10 ; i++){
                    String nombrePersonalAseo = faker.name().firstName();
                    String apellidoPersonalAseo = faker.name().lastName();
                    personalAseoRepository.save(PersonalAseo.builder()
                            .rutPersonalAseo(faker.idNumber().valid())
                            .nombrePersonalAseo(nombrePersonalAseo)
                            .apellidoPersonalAseo(apellidoPersonalAseo)
                            .sueldoPersonalAseo(faker.number().numberBetween(553553, 800000))
                            .build());
                }
            }
        };
    }
}
