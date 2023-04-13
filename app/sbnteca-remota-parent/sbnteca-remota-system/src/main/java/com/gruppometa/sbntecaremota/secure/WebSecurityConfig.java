package com.gruppometa.sbntecaremota.secure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	protected static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
	@Autowired
	protected UserDetailsService userDetailsService;
	
	@Autowired
	protected JwtTokenFilter jwtTokenFilter;

	@Value("${security.enabled:true}")
	private boolean securityEnabled;
		
	/**
	 * In base alle authorities (i ruoli Spring) sono assegnati gli accessi alle funzionalità
	 * 
	 */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
		if(!securityEnabled) {
			http
					.headers()
					.cacheControl().disable()
					.and()

					.csrf()
					.disable()
					.authorizeRequests()
					.antMatchers("/**").permitAll()
			;
			return;
		}
        http
        	.headers()
        		.cacheControl().disable()
        		.and()
        	
        	.csrf()
        		.disable()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
        	.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/rest/jwt/login").permitAll()
				.antMatchers("/rest/getmetsxml").permitAll()
				.antMatchers("/rest/getmagxml").permitAll()
				.antMatchers("/rest/mag").permitAll()
				.antMatchers("/rest/digitalResource/**").permitAll()
				.antMatchers("/rest/editor/**").permitAll()
				.antMatchers("/rest/export/download/**").permitAll()
				.antMatchers("/rest/validate/status").permitAll()
				.antMatchers("/rest/import/status").permitAll()
				.antMatchers("/rest/user/role/all").permitAll()
				.antMatchers("/rest/**").authenticated()
				.antMatchers("/swagger.ui/**", "/openapi.json").permitAll()
		;
		// Add JWT token filter
		http.addFilterBefore(
				jwtTokenFilter,
				UsernamePasswordAuthenticationFilter.class
		);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService)
					.passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
    
}
