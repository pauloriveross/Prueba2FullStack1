package duoc.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI apiInfor(){
        return new OpenAPI().info(new Info()
                .title("Test Drive API")
                .version("1.0.0")
                .description("Microservicio de TestDrives.")
        );
    }
}
