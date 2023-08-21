package sapo.vn.ex7restfulapispring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sapo.vn.ex7restfulapispring.config.filter.CustomAuthenticationProvider;
import sapo.vn.ex7restfulapispring.config.filter.JwtAuthenticationFilter;
import sapo.vn.ex7restfulapispring.config.filter.JwtUsernamePasswordAuthenticationFilter;
import sapo.vn.ex7restfulapispring.exception.CustomAccessDeniedHandler;
import sapo.vn.ex7restfulapispring.jwt.JwtConfig;
import sapo.vn.ex7restfulapispring.jwt.JwtService;
import sapo.vn.ex7restfulapispring.service.security.UserDetailsServiceCustom;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class AppConfig {
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;
    @Autowired
    JwtConfig jwtConfig;
    @Autowired
    private JwtService jwtService;

    @Autowired
    public void configGlobal(final AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Bean
    public JwtConfig jwtConfig() {
        return new JwtConfig();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceCustom();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        AuthenticationManager manager = builder.build();

        http
                .cors().disable()
                .csrf().disable()
                .formLogin().disable()
                .authorizeHttpRequests()
                .antMatchers("/account/**").permitAll()
                .antMatchers(HttpMethod.GET, "/admin/product/get-all/**").permitAll()
                .antMatchers(HttpMethod.GET, "/category/get-all/**").permitAll()
                .antMatchers(HttpMethod.GET, "/warehouse/get-all/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/inventory/statistics**").permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/user/**").hasAuthority("USER")
                .anyRequest().authenticated()
                .and()
                .authenticationManager(manager)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(
                        ((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                )
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .addFilterBefore(new JwtUsernamePasswordAuthenticationFilter(manager, jwtConfig, jwtService), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtAuthenticationFilter(jwtConfig, jwtService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
