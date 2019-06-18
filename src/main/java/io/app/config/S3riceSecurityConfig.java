package io.app.config;

import io.app.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class S3riceSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;


	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Autowired
	private DaoAuthenticationProvider daoAuthenticationProvider;

	/*
	 * @Override protected void configure(HttpSecurity http) throws Exception {
	 * 
	 * http.formLogin().loginPage("/login.html").defaultSuccessUrl("/").failureUrl(
	 * "/login-error.html") .and() .logout() .logoutUrl("/logout")
	 * .logoutSuccessUrl("/login")
	 * .and().authorizeRequests().anyRequest().hasRole("ADMIN");
	 * 
	 * http .formLogin() .loginPage("/login.html") .failureUrl("/login-error.html")
	 * .defaultSuccessUrl("/dashboard") .and() .logout()
	 * .logoutUrl("/perform-logout") .logoutSuccessUrl("/index.html");
	 * .and().authorizeRequests().antMatchers("/dashboard").hasRole("ADMIN");
	 * 
	 * }
	 * 
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception {
	 * auth.inMemoryAuthentication().passwordEncoder(PasswordEncoder).withUser(
	 * "shaiksha").password(PasswordEncoder.encode("shaiksha")).authorities(
	 * "ADMIN_ROLE"); } protected void configure(HttpSecurity http) throws Exception
	 * { http .authorizeRequests() .antMatchers("/dashboard").hasRole("ADMIN")
	 * .anyRequest().permitAll() .and().formLogin() .loginPage("/login.html")
	 * .loginProcessingUrl("/perform-login") .failureUrl("/login-error.html")
	 * .defaultSuccessUrl("/dashboard", true) .and().httpBasic();
	 * 
	 * 
	 * }
	 * 
	 * @SuppressWarnings("deprecation")
	 * 
	 * @Bean public UserDetailsService userDetailsService() {
	 * InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
	 * manager.createUser(User.withDefaultPasswordEncoder().username("user").
	 * password("password").roles("ADMIN").build()); return manager; }
	 * 
	 */
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) 
      throws Exception {
        auth
          .inMemoryAuthentication()
          .withUser("user").password(passwordEncoder.encode("password")).roles("USER")
          .and()
          .withUser("shaiksha").password(passwordEncoder.encode("shaiksha")).roles("ADMIN");

		//auth.authenticationProvider(daoAuthenticationProvider);
    }


	@Bean
	public DaoAuthenticationProvider authProvider() {
		final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println(passwordEncoder().encode("shaiksha"));

		/*
		 * http.authorizeRequests()
		 * .antMatchers("/","index.html","/login","/js/**","/images/**","/css/**").
		 * permitAll() .anyRequest().hasRole("ADMIN").and().formLogin();
		 */
		
		http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/","index.html","/login","/js/**","/images/**","/css/**","/checkTheDue","/due-details").permitAll()
        .anyRequest().hasRole("ADMIN")
        .and()
        .formLogin()
        .loginPage("/login")
        .defaultSuccessUrl("/dashboard")
        .permitAll()
        .and()
        .logout()
        .permitAll()
        .and();
        //.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler();
        
		
		
	}
}
