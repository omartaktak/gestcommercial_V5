package tn.taktak.GestCommerciale_V1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;

import tn.taktak.GestCommerciale_V1.service.RepresentantService;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired 
	private CustomUserDetailsService customUserDetailsService; 
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("omar").password("omar").roles("USER");
		//auth.userDetailsService(representantService).getUserDetailsService();
		auth.userDetailsService(customUserDetailsService);
    }
	
//	@Override
//	  protected void configure(HttpSecurity http) throws Exception {
//	    http
//	    .logout().logoutSuccessUrl("/login");
//	}
}
