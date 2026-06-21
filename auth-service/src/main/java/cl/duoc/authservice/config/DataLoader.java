package cl.duoc.authservice.config;


import cl.duoc.authservice.model.Usuario;
import cl.duoc.authservice.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {
    @Bean
    public CommandLineRunner cargarDatos(UsuarioRepository repository){

        return args -> {
            if(repository.count()==0){
                repository.save(Usuario.builder()
                        .username("admin")
                        .password("admin1234")
                        .rol("Admin")
                        .build()
                );



            }
        };
    }
}
