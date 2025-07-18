package com.aless00san.springboot.gunpladb.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.aless00san.springboot.gunpladb.security.filter.JwtAuthenticationFilter;
import com.aless00san.springboot.gunpladb.security.filter.JwtValidationFilter;
import com.aless00san.springboot.gunpladb.services.UserDetailsServiceJpa;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private UserDetailsServiceJpa userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		System.out.println("=== Creating DaoAuthenticationProvider ===");
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		System.out.println("=== Creating AuthenticationManager ===");
		AuthenticationManager manager = config.getAuthenticationManager();
		return manager;
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:5173"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	// TODO: Configure security filter for the application
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authManager)
			throws Exception {
		System.out.println("=== Creating SecurityFilterChain ===");

		JwtAuthenticationFilter jwtAuthFilter = new JwtAuthenticationFilter(authManager);
		JwtValidationFilter jwtValidationFilter = new JwtValidationFilter(authManager);

		http
				.authenticationProvider(authenticationProvider())
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.authorizeHttpRequests(requests -> requests
						.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
						.requestMatchers("/login").permitAll() // This is needed for JWT token generation
						.requestMatchers("/api/gunpla/list").permitAll()
						.requestMatchers("/api/gunpla/list/{page}/{size}").authenticated()
						.requestMatchers("/api/series/list").permitAll()
						.requestMatchers("/api/grade/list").permitAll()
						.requestMatchers("/api/user").permitAll()
						.requestMatchers("/api/user/auth").permitAll()
						.requestMatchers("/api/user/**").permitAll()
						.requestMatchers("/api/gunpla").hasRole("USER").requestMatchers(HttpMethod.POST).authenticated()
						.requestMatchers("/api/gunpla/{id}").hasRole("USER").requestMatchers(HttpMethod.PUT)
						.authenticated()
						.anyRequest().authenticated())
				.formLogin(form -> form.disable())
				.httpBasic(basic -> basic.disable())
				.exceptionHandling(ex -> ex
						.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.BAD_REQUEST)))
				.logout(logout -> logout.permitAll())
				.addFilter(jwtValidationFilter)
				.addFilter(jwtAuthFilter)
				.csrf(config -> config.disable())
				.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}

}