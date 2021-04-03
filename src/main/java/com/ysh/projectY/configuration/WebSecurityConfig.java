package com.ysh.projectY.configuration;

import com.ysh.projectY.filter.UsernamePasswordLoginFilter;
import com.ysh.projectY.handler.*;
import com.ysh.projectY.provider.CustomAuthenticationProvider;
import com.ysh.projectY.service.RememberMeTokenRepositoryImpl;
import com.ysh.projectY.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${server.port}")
    private int httpsPort;

    @Value("${server.ssl.enabled}") //生产环境关闭SSL后, 同时关闭
    private boolean sslEnabled;

    @Value("${projectY.api-base-path}")
    private String apiBasePath;

    final AuthenticationSuccessHandler authenticationSuccessHandler;
    final AuthenticationFailureHandler authenticationFailureHandler;
    final RememberMeTokenRepositoryImpl rememberMeTokenRepositoryImpl;
    final MobilePhoneAuthenticationConfig mobilePhoneAuthenticationConfig;
    final SuccessLogoutHandler successLogoutHandler;

    @Autowired
    CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    @Autowired
    CustomAccessDeniedHandler customAccessDeniedHandler;

    public WebSecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler, AuthenticationFailureHandler authenticationFailureHandler, RememberMeTokenRepositoryImpl rememberMeTokenRepositoryImpl, MobilePhoneAuthenticationConfig mobilePhoneAuthenticationConfig, SuccessLogoutHandler successLogoutHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.rememberMeTokenRepositoryImpl = rememberMeTokenRepositoryImpl;
        this.mobilePhoneAuthenticationConfig = mobilePhoneAuthenticationConfig;
        this.successLogoutHandler = successLogoutHandler;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        // 由负载均衡NGINX提供HTTPS
        // httpSecurity.portMapper().http(httpPort).mapsTo(httpsPort);
        // httpSecurity.requiresChannel(channel -> channel.anyRequest().requiresSecure());

        httpSecurity.addFilterBefore(new UsernamePasswordLoginFilter(authenticationFailureHandler), UsernamePasswordAuthenticationFilter.class);

        httpSecurity.authorizeRequests()
                .antMatchers(apiBasePath + "/admin/users/**")
                .hasRole("userAdmin")
                .antMatchers(apiBasePath + "/admin/roles/**")
                .hasRole("authAdmin")
                .antMatchers(apiBasePath + "/admin/auths/**")
                .hasRole("authAdmin")
                .antMatchers(apiBasePath + "/admin/menus/**")
                .hasRole("authAdmin")
                .antMatchers(apiBasePath + "/admin/**")
                .hasRole("admin")
                .antMatchers(apiBasePath + "/users/**")
                .hasRole("user")
                .antMatchers(apiBasePath + "/submit/**")
                .hasRole("user")
                .anyRequest()
                .permitAll();

        httpSecurity.exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler);

        httpSecurity.formLogin()
                .loginPage(apiBasePath + "/login")
                .loginProcessingUrl(apiBasePath + "/doLogin")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler);
//        httpSecurity.rememberMe()
////                .rememberMeCookieDomain("xclass.highspeed.vip")
//                .rememberMeCookieName("projectY")
//                .rememberMeParameter("autoLogin")
//                .key("projectY")
//                .tokenRepository(rememberMeTokenRepositoryImpl)
//                .tokenValiditySeconds(60 * 60 * 24 * 30)
//                .useSecureCookie(true);

//        System.out.println("sslEnabled: " + sslEnabled);
        if (sslEnabled) {
            httpSecurity.rememberMe()
//                .rememberMeCookieDomain("xclass.highspeed.vip")
                    .rememberMeCookieName("projectY")
                    .rememberMeParameter("autoLogin")
                    .key("projectY")
                    .tokenRepository(rememberMeTokenRepositoryImpl)
                    .tokenValiditySeconds(60 * 60 * 24 * 30)
                    .useSecureCookie(true);
        } else {
            httpSecurity.rememberMe()
//                .rememberMeCookieDomain("xclass.highspeed.vip")
                    .rememberMeCookieName("projectY")
                    .rememberMeParameter("autoLogin")
                    .key("projectY")
                    .tokenRepository(rememberMeTokenRepositoryImpl)
                    .tokenValiditySeconds(60 * 60 * 24 * 30)
                    .useSecureCookie(false);
        }

        //前台还没有实现Logout
        httpSecurity.logout()
                .logoutUrl(apiBasePath + "/logout")
                .logoutSuccessHandler(successLogoutHandler)
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("PHPSESSID");  // ??????????????

        httpSecurity.csrf().disable();

        httpSecurity.sessionManagement()
                .sessionAuthenticationFailureHandler(authenticationFailureHandler)
//                .invalidSessionUrl(apiBasePath + "/invalidSession")
                .maximumSessions(1)
//                .expiredUrl(apiBasePath + "/expiredSession")
                .maxSessionsPreventsLogin(false);

        httpSecurity.apply(mobilePhoneAuthenticationConfig);

        // 设置跨域请求, 配合预定义的Bean: CorsConfigurationSource
        httpSecurity.cors(Customizer.withDefaults());

        // 解决跨域问题。cors 预检请求放行,让Spring security 放行所有preflight request（cors 预检请求）
//        httpSecurity.authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll();
    }

    // 自定义customAuthenticationProvider, 验证码认证逻辑太靠后, 用Filter代替 ????? 不对
    CustomAuthenticationProvider customAuthenticationProvider() {
        CustomAuthenticationProvider customAuthenticationProvider = new CustomAuthenticationProvider();
        customAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        customAuthenticationProvider.setUserDetailsService(userDetailsService());
        return customAuthenticationProvider;
    }

    @Bean
    @Override
    protected UserDetailService userDetailsService() {
        return new UserDetailService();
    }

    // 添加自定义的AuthenticationProvider
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(customAuthenticationProvider()));
//        try {
//            return super.authenticationManagerBean();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_admin > ROLE_user \n ROLE_admin > ROLE_userAdmin \n ROLE_admin > ROLE_authAdmin";
        roleHierarchy.setHierarchy(hierarchy);

        return roleHierarchy;
    }
}
