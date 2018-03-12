package com.liezh.config;


import com.liezh.security.jwt.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by Administrator on 2017/4/28.
 */
@EnableWebSecurity
public class WebSecurityConfiguration {

    private static final String[] ignoringPaths = {"/", "/index.html", "/asset/**", "/login", "/loginPage", "/webjars/**", "/**/favicon.ico", "/jwt/login", "/jwt/register"};


    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    @Order(1)
    public static class WebApiSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/manage/**")
                    .antMatcher("/api/**")
                    .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                    // 由于使用的是JWT，我们这里不需要csrf
                    .csrf().disable()
                    // 基于token，所以不需要session
//                    .sessionManagement().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
//                    .antMatchers("/api/answer/**").hasAnyRole("USER")
//                      .anyRequest().authenticated()   //所有的请求都需要登录认证
                    .and()
                    .formLogin()
                    .loginPage("/loginPage")
                    .defaultSuccessUrl("/indexPage")
                    .permitAll();
            //禁用缓存
            http.headers().cacheControl();
        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

    }

//    @Bean
//    public UserDetailsService data() throws  Exception {
//        return  new DatabaseUserDetailsServiceImpl();
//    }


    @Configuration
    public static class WebFormSecurityConfigurerAdapter extends org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/**").permitAll()
                    .antMatchers("/security/**/*").hasAnyRole("admin")
//                    .antMatchers("/answer/**").hasAnyRole("USER")
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                    .csrf().disable()
                    .logout()
                    .permitAll() ;
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            //super.configure(web);
            web.ignoring().antMatchers(ignoringPaths);
        }
    }



}
