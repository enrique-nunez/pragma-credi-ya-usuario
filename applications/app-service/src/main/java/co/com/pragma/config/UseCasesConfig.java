package co.com.pragma.config;

import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.usecase.user.UserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
public class UseCasesConfig {

        @Bean
        public UserUseCase userUseCase(UserRepository userRepository) {
                return new UserUseCase(userRepository);
        }
}