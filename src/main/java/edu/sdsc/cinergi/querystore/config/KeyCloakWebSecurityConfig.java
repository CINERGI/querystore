package edu.sdsc.cinergi.querystore.config;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.spi.HttpFacade;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import java.io.InputStream;

/**
 * @author <a href="mailto:valentin@sdsc.edu">Dacid Valentine</a>
 */

@KeycloakConfiguration
@EnableWebSecurity
public class KeyCloakWebSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    /**
     * Registers the KeycloakAuthenticationProvider with the authentication manager.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(keycloakAuthenticationProvider());
    }

    /**
     * Defines the session authentication strategy.
     */
    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
//        http.logout().logoutSuccessUrl("/home")
//                .and()
//                .authorizeRequests()
//                .antMatchers("/**").hasAuthority("user");
/* examples
https://www.programcreek.com/java-api-examples/?api=org.springframework.security.config.annotation.web.builders.HttpSecurity
 */
http
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout().logoutSuccessUrl("/home")
                .permitAll()
                .and()
                .authorizeRequests()
        .antMatchers("/", "/api/profile/**").permitAll()
       // .antMatchers("/api/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/item/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/item/**").permitAll()
                .antMatchers(HttpMethod.DELETE,"/api/item/**")
                .hasAuthority("admin")
             //   .antMatchers("/**").hasAuthority("user")
;
    }

    /**
     * Overrides default keycloak config resolver behaviour (/WEB-INF/keycloak.json) by a simple mechanism.
     * <p>
     * This example loads other-keycloak.json when the parameter use.other is set to true, e.g.:
     * {@code ./gradlew bootRun -Duse.other=true}
     *
     * @return keycloak config resolver
     */
    @Bean
    public KeycloakConfigResolver keycloakConfigResolver() {
        return new KeycloakConfigResolver() {

            private KeycloakDeployment keycloakDeployment;

            @Override
            public KeycloakDeployment resolve(HttpFacade.Request facade) {
                if (keycloakDeployment != null) {
                    return keycloakDeployment;
                }

                String path = "/keycloak.json";
                InputStream configInputStream = getClass().getResourceAsStream(path);

                if (configInputStream == null) {
                    throw new RuntimeException("Could not load Keycloak deployment info: " + path);
                } else {
                    keycloakDeployment = KeycloakDeploymentBuilder.build(configInputStream);
                }

                return keycloakDeployment;
            }
        };
    }
}
