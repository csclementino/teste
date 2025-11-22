package com.arc.lince;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
@OpenAPIDefinition(
        info = @Info(
                title = "API Lince",
                version = "1.0",
                description = "Documentação gerada pelo Swagger para o projeto de monitoramento de utilização de EPIs"
        )
)
public class LinceApplication {


    public static void main(String[] args) {
		SpringApplication.run(LinceApplication.class, args);
	}


}
