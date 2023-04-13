package com.gruppometa.magteca.frontend.authenticate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import com.gruppometa.magteca.frontend.WebAppConfiguration;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	protected AuthenticationService authenticationProvider;
	
	@Autowired
	protected UserDetailsService userDetailsService;
	
	@Autowired
	protected WebAppConfiguration configuration;

	/**
	 * In base alle authorities (i ruoli Spring) sono assegnati gli accessi alle funzionalità
	 * 
	 */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.headers()
        		.cacheControl().disable()
        		.and()
        	.csrf()
        		.disable()
        	.authorizeRequests()
        		.antMatchers("/", "/home").authenticated()
				.antMatchers("/TecaDigitaleSystem/digitalObject").authenticated()
				.antMatchers("/TecaDigitaleSystem/rest/**").authenticated()
				.antMatchers("/metaoaicat/rest/**").authenticated()
        		.antMatchers("/upload").hasRole("UPLOAD_MANAGER")
        		.antMatchers("/uploadUpdate").hasRole("UPLOAD_UPDATE_MANAGER")
				.antMatchers("/uploadResources","/uploadUnimarc").hasRole("UPLOAD_UPDATE_MANAGER")
        		.antMatchers("/import").hasRole("IMPORT_MANAGER")
				.antMatchers("/vfs").hasRole("IMPORT_MANAGER")
				.antMatchers("/vfs-container").hasRole("IMPORT_MANAGER")
				.antMatchers("/vfs-searchs").hasRole("IMPORT_MANAGER")
        		.antMatchers("/update").hasAnyRole("UPDATE_MANAGER", "DRAFT_MANAGER")
        		.antMatchers("/changeUsage").hasRole("CHANGE_USAGE_MANAGER")
        		.antMatchers("/clear").hasRole("DELETE_PROJECT_MANAGER")
        		.antMatchers("/export_panel").hasRole("EXPORT_PANEL_MANAGER")
        		.antMatchers("/editor", "/editorMets", "/displayResource/**",
						"/magDetail","/metsDetail").hasRole("SEARCH_AREA_MANAGER")
        		.antMatchers("/drafts").hasRole("DRAFT_MANAGER")
        		.antMatchers("/search").hasRole("SEARCH_MANAGER")
        		.antMatchers("/public").hasRole("PUBLICATION_MANAGER")
        		.antMatchers("/delete").hasRole("DELETE_MANAGER")
        		.antMatchers("/export").hasRole("EXPORT_MANAGER")
        		.antMatchers("/normalize").hasRole("NORMALIZE_MANAGER")
        		.antMatchers("/stats").hasRole("STATS_MANAGER")
        		.antMatchers("/objectsStats").hasRole("DIGITAL_OBJECT_STATS_MANAGER")
        		.antMatchers("/oaisets", "/oaisetNew", "/oaisetBuildQuery").hasRole("OAISET_MANAGER")
        		.antMatchers("/users", "/userNew").hasRole("USER_MANAGER")
        		.antMatchers("/roles").hasRole("ROLES_MANAGER")
                .and()
				.httpBasic().and()
            .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .permitAll()
                .and()
            .logout()
                .permitAll().and()
				.headers()
				.frameOptions().disable()
				//.contentSecurityPolicy("frame-src http://127.0.0.1/console/mets-editor/index.html")
		;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
    	if(configuration.isInMemory())
    		auth.inMemoryAuthentication().withUser("test").password("test").roles("admin");
    	else {
    		// TODO Password Encoder....
			auth.userDetailsService(userDetailsService)
					.passwordEncoder(NoOpPasswordEncoder.getInstance());
		}
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/application/**", "/config/**", "/js/**", "/css/**", "/font/**", 
        		"/webjars/**", "/images/**", "/img/**", "/libs/**", "/schemas/**");
    }
    
    @Bean
    public SpringSecurityDialect securityDialect() {
    	return new SpringSecurityDialect();
    }

}
