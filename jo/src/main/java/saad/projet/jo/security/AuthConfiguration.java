package saad.projet.jo.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public AuthConfiguration(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            AuthenticationProvider authenticationProvider
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests.
                    requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()

                    .requestMatchers(new AntPathRequestMatcher("/")).permitAll()

                                //auth
                    .requestMatchers(new AntPathRequestMatcher("/auth/login")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/auth/signup")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/auth/updatePassword")).hasAnyAuthority("ADMIN","USER")

                            //users
                    .requestMatchers(new AntPathRequestMatcher("/users/me")).hasAnyAuthority("ADMIN","USER")
                    .requestMatchers(new AntPathRequestMatcher("/users/check-connected")).hasAnyAuthority("ADMIN","USER")
                    .requestMatchers(new AntPathRequestMatcher("/users/check-connected-admin")).hasAuthority("ADMIN")
                    .requestMatchers(new AntPathRequestMatcher("/users")).hasAuthority("ADMIN")
                    .requestMatchers(new AntPathRequestMatcher("/users/paginated/**")).hasAuthority("ADMIN")
                    .requestMatchers(new AntPathRequestMatcher("/users/informations/**")).hasAuthority("ADMIN")
                    .requestMatchers(new AntPathRequestMatcher("/users/change-role/**")).hasAuthority("ADMIN")
                    .requestMatchers(new AntPathRequestMatcher("/users/tickets/me/**")).hasAnyAuthority("ADMIN","USER")
                    .requestMatchers(new AntPathRequestMatcher("/users/ticket/me/**")).hasAnyAuthority("ADMIN","USER")
                    .requestMatchers(new AntPathRequestMatcher("/users/ticket/subscription/**")).hasAnyAuthority("ADMIN","USER")
                    .requestMatchers(new AntPathRequestMatcher("/users/ticket/cancel-subscription/**")).hasAuthority("ADMIN")
                    .requestMatchers(new AntPathRequestMatcher("/users/ticket/checkregistration/**")).hasAnyAuthority("ADMIN","USER")

                        //tickets
                    .requestMatchers(new AntPathRequestMatcher("/tickets")).hasAnyAuthority("ADMIN","USER")
                    .requestMatchers(new AntPathRequestMatcher("/delete/{uuid}")).hasAnyAuthority("ADMIN")
                    .requestMatchers(new AntPathRequestMatcher("/tickets/**")).hasAuthority("ADMIN")

                        //evenement

                    .requestMatchers(new AntPathRequestMatcher("/evenements")).hasAuthority("ADMIN")
                    .requestMatchers(new AntPathRequestMatcher("/evenements/paginated/{page}/{size}")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/evenements/paginatedByCategory/{page}/{size}/{id}")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/evenements/checkavailable/{uuid}")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/evenements/update/**")).hasAuthority("ADMIN")
                    .requestMatchers(new AntPathRequestMatcher("/evenements/delete/**")).hasAuthority("ADMIN")
                    .requestMatchers(new AntPathRequestMatcher("/evenements/create/**")).hasAuthority("ADMIN")
                    .requestMatchers(new AntPathRequestMatcher("/evenements/check-category/**")).hasAuthority("ADMIN")
                    .requestMatchers(new AntPathRequestMatcher("/evenements/details/**")).permitAll()

                        // categories
                    .requestMatchers(new AntPathRequestMatcher("/categories")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/categories/{id}")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/categories/paginated/{page}/{size}")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/categories/create/**")).hasAuthority("ADMIN")
                    .requestMatchers(new AntPathRequestMatcher("/categories/update/**")).hasAuthority("ADMIN")
                    .requestMatchers(new AntPathRequestMatcher("/categories/delete/**")).hasAuthority("ADMIN")

                        // operations
                    .requestMatchers(new AntPathRequestMatcher("/operations/**")).hasAuthority("ADMIN")

                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:8080","http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET","POST","DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**",configuration);

        return source;
    }
}