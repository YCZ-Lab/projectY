package com.ysh.projectY.configuration;

import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.session.HttpSessionEventPublisher;
//import org.springframework.session.web.http.CookieSerializer;
//import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Component
public class PredefinedBeans {

    @Value("${projectY.api-base-path}")
    private String apiBasePath;

    @Value("${projectY.allowed-origins}")
    private String allowedOrigins;

    @Value("${server.ssl.enabled}") //生产环境关闭SSL后, 同时关闭
    private boolean sslEnabled;

    //从tomcat容器向springmvc容器传递session事件
    @Bean
    HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    //解决SameSite跨域问题
    @Bean
    public TomcatContextCustomizer sameSiteCookiesConfig() {
        return context -> {
            final Rfc6265CookieProcessor cookieProcessor = new Rfc6265CookieProcessor();
            // 设置Cookie的SameSite
            if (sslEnabled) {
                cookieProcessor.setSameSiteCookies(SameSiteCookies.NONE.getValue());
            }
            context.setCookieProcessor(cookieProcessor);
        };
    }

    //添加spring-session-data-redis后, 解决SameSite跨域问题
//    @Bean
//    public CookieSerializer httpSessionIdResolver() {
//        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
//        if (sslEnabled) {
//            cookieSerializer.setSameSite(SameSiteCookies.NONE.getValue());
//        }
//        cookieSerializer.setCookieName("PHPSESSID");
//        return cookieSerializer;
//    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin(allowedOrigins);
        configuration.addAllowedMethod("*");//修改为添加而不是设置
        configuration.addAllowedHeader("*");//这里很重要，起码需要允许 Access-Control-Allow-Origin
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(1800L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(apiBasePath + "/**", configuration);
        return source;
    }
}
