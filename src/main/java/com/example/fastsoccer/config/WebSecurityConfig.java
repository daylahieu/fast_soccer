package com.example.fastsoccer.config;


import com.example.fastsoccer.entity.UserEntity;
import com.example.fastsoccer.repository.UserRepository;
import com.example.fastsoccer.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
     /*   response.sendRedirect("/load-yard-manager-own");
        response.sendRedirect("/load-form-add-yard");*/
        /*http.csrf().disable();*/
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/loadPage", "/login", "/logout", "/loadFormRegister", "/process_register","/showDetail").permitAll()
                //trang quản lý của chủ sân yêu cầu quyền OWN
                .antMatchers("/load-manager-own", "/loadyardmanagerown", "/loadformaddyard").hasAuthority("OWN")
                .antMatchers("/booking","/loadMatching").hasAuthority("USER")
                // Trang chỉ dành cho ADMIN
                .antMatchers("/admin", "/updateStatus","/update").hasAuthority("ADMIN")
                .and().exceptionHandling().accessDeniedPage("/403")
                // Cấu hình cho Login Form.
                .and().formLogin()//
                // Submit URL của trang login
                .loginProcessingUrl("/login") // Submit URL
                .loginPage("/loadFormLogin")//
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

                        String username = request.getParameter("username");
                        UserEntity user = userRepository.findAllByUsername(username);
                        request.getSession().setAttribute("user", user);
                        if (user.getRole().equals("ADMIN")) {
                            response.sendRedirect("/admin");
                        } else if (user.getRole().equals("OWN")) {
                            response.sendRedirect("/load-manager-own");
                        } else {
                            response.sendRedirect("/loadPage");
                        }
                    }
                })
                .permitAll()
                /*.defaultSuccessUrl("/loadmanagerown")//
                .usernameParameter("username")//
                .passwordParameter("password")*/
                // Cấu hình cho Logout Page.
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/logout-success").permitAll()
        ;
    }
       /* // Cấu hình Remember Me.
        http.authorizeRequests().and() //
                .rememberMe().tokenRepository(this.persistentTokenRepository()) //
                .tokenValiditySeconds(1 * 24 * 60 * 60); // 24h
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }*/
}
