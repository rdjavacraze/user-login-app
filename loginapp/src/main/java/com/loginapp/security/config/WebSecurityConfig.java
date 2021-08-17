package com.loginapp.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.loginapp.security.auth.filter.AuthenticationFilter;
import com.loginapp.security.auth.provider.AuthenticationProviderImpl;
import com.loginapp.security.user.service.SimpleUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	

	@Autowired
	private AuthenticationProviderImpl customProvider;
	@Autowired
	private SimpleUserDetailsService userDetailService;
	
	@Autowired
	private AuthenticationFilter filter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		
		
		http.csrf().disable().authorizeRequests().antMatchers("/authenticate/generateToken")
        .permitAll().
        antMatchers("/login").permitAll()
        .anyRequest().authenticated().
        
        
        and().exceptionHandling().and().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        http.addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class);
       
	
	}

	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**")
		.and().ignoring().antMatchers("/h2-console**");
	}


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService);
		
		//auth.authenticationProvider(customProvider);
		/*auth.inMemoryAuthentication().withUser("admin").password(getPasswordEnCoder().encode("admin")).roles("ADMIN")
		.and().withUser("user").password(getPasswordEnCoder().encode("user")).roles("USER");*/
	}
	

	@Bean(name=BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		
		return super.authenticationManagerBean();
	}


	@Bean
	public PasswordEncoder getPasswordEnCoder(){
		return NoOpPasswordEncoder.getInstance();
	}

	
	
}
