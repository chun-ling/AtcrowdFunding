package com.atguigu.crowd.mvc.config;

import com.atguigu.crowd.constant.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author YUChangcan
 * @date 2023/1/4 - 18:02
 */
@Configuration
// 启用Web环境下权限控制功能
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
//	@Bean
//	public BCryptPasswordEncoder getBCryptPasswordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
//		builder.inMemoryAuthentication()
//			.withUser("admin")
//			.password("123123")
//			.roles("ADMIN");
        builder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                .authorizeRequests()
                .antMatchers("/admin/to/login/page.html", "/bootstrap/**", "/crowd/**", "/css/**", "/fonts/**", "/ztree/**","/img/**","/jquery/**","/layer/**","/script/**")
                .permitAll()
                .antMatchers("/admin/get/page.html")
//                .hasRole("经理")
                .access("hasRole('经理') or hasAuthority('user:get')")
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler(){
                    @Override
                    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                        httpServletRequest.setAttribute("exception",new Exception(CrowdConstant.MESSAGE_ACCESS_DENIED));
                        httpServletRequest.getRequestDispatcher("/WEB-INF/system-error.jsp").forward(httpServletRequest,httpServletResponse);
                    }
                })
                .and()
                .csrf()// 放跨站请求伪造功能
                .disable()// 禁用
                .formLogin()
                .loginPage("/admin/to/login/page.html")
                .loginProcessingUrl("/security/do/login.html")
                .defaultSuccessUrl("/admin/to/main/page.html")
                .permitAll()
                .usernameParameter("loginAcct")
                .passwordParameter("userPswd")
                .and()
                .logout()
                .logoutUrl("/security/do/logout.html")
                .logoutSuccessUrl("/admin/to/login/page.html")
                ;

    }
}
