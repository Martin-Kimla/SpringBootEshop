package spring.eshop.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class ApplicationSecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                .anyRequest()
                .permitAll() // <-- Všechny stránky povolíme (pravidla budeme definovat přímo v kontroleru)
                .and()
                .formLogin()
                    .loginPage("/uzivatel/prihlasit") // Přihlašovací URL adresa
                    .loginProcessingUrl("/uzivatel/prihlasit") // Přihlašovací URL adresa
                    .defaultSuccessUrl("/produkty", true) // Nastavení přesměrování po úspěšném přihlášení
                    .usernameParameter("email") // Chceme se přihlašovat pomocí emailu
                    .permitAll() // Povolit vstup na `/account/login` i nepřihlášeným uživatelům
                    .and()
                .logout()
                    .logoutUrl("/uzivatel/odhlasit") // Odhlašovací URL adresa
                    .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
