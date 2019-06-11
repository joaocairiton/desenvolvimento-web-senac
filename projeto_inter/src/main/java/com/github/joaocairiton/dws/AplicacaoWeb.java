package com.github.joaocairiton.dws;

import com.sun.faces.config.ConfigureListener;
import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.context.ServletContextAware;

/**
 *
 * @author braully
 */
@EnableWebSecurity
@SpringBootApplication
public class AplicacaoWeb extends WebSecurityConfigurerAdapter implements ServletContextAware {

    public static void main(String... args) {
        SpringApplication.run(AplicacaoWeb.class, args);
    }
     @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("usuario")
                .password(passwordEncoder().encode("153624"))
                .roles("Usuario").build();

        UserDetails admin = User.withUsername("gerente")
                .password(passwordEncoder().encode("123456"))
                .roles("gerente").build();
        return new InMemoryUserDetailsManager(user, admin);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/relatorio-vendas.xhtml").hasRole("gerente")
                .anyRequest().permitAll().and()
                .formLogin().permitAll().and()
                .logout().permitAll();
        http.csrf().disable();
    }


    
    @Bean
    public DataSource datasourse(){
        DataSourceBuilder create = DataSourceBuilder.create();
        create.url("jdbc:postgresql://localhost:5432/projeto_ads3");
        create.driverClassName("org.postgresql.Driver");
        create.username("postgres");
        create.password("123456");
        return create.build();
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        FacesServlet servlet = new FacesServlet();
        return new ServletRegistrationBean(servlet, "*.xhtml");
    }

    @Bean
    public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
        return new ServletListenerRegistrationBean<ConfigureListener>(
                new ConfigureListener());
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
    }
    
   
    
    

}
