package fr.insee.pearljam.api.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    /**
     * The url to allowed
     * Generate with the application property applcation.cros_origin
     */
    @Value("${fr.insee.pearljam.application.crosOrigin}")
    private String crosOrigin;

    // @Bean
    // public FilterRegistrationBean<CorsFilter> corsFilter() {
    // UrlBasedCorsConfigurationSource source = new
    // UrlBasedCorsConfigurationSource();
    // CorsConfiguration config = new CorsConfiguration();
    // config.setAllowCredentials(true);
    // config.addAllowedOrigin(crosOrigin);
    // config.addAllowedHeader("*");
    // config.addAllowedMethod("*");
    // config.setMaxAge(3600L);
    // source.registerCorsConfiguration("/**", config);
    // FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new
    // CorsFilter(source));
    // bean.setOrder(0);
    // return bean;
    // }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(crosOrigin));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.addExposedHeader("Content-Disposition");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}