/**
 * SecurityConfiguration class to secure endpoints and make login end point public
 * @author Michelle
 * @version 1
 *
 */
package com.calendar.login.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;



@EnableWebSecurity
@Configuration
public class SecurityConfiguration {


    /**
     * securityFilterChain to configure security filter chain for login feature
     * @param http instance of HttpSecurity
     * @return http.build()
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.csrf(AbstractHttpConfigurer::disable) //disable csrf for end point
                .sessionManagement(customizer-> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //In stateless application. Don't have to handle session
                .authorizeHttpRequests((requests)->
                        requests.requestMatchers(HttpMethod.POST,"/login").permitAll() //make login endpoint public
                                .anyRequest().authenticated()); //require authentication( for secure endpoint access)

        return http.build(); //finalize http configuration


    }
}
