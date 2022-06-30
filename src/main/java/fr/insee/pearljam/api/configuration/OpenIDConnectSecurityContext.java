package fr.insee.pearljam.api.configuration;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.Jwt;

import fr.insee.pearljam.api.configuration.auth.UserProvider;
import fr.insee.pearljam.api.configuration.auth.user.User;
import fr.insee.pearljam.api.constants.Constants;

@Configuration
@EnableWebSecurity
@ConditionalOnProperty(name = "fr.insee.pearljam.application.mode", havingValue = "OIDC")
public class OpenIDConnectSecurityContext extends WebSecurityConfigurerAdapter {

    static final Logger logger = LoggerFactory.getLogger(OpenIDConnectSecurityContext.class);

    @Value("${jwt.stamp-claim}")
    private String stampClaim;

    @Value("${jwt.username-claim}")
    private String nameClaim;

    @Value("${fr.insee.pearljam.application.crosOrigin}")
    private Optional<String> allowedOrigin;

    @Value("${fr.insee.pearljam.interviewer.role:#{null}}")
    private String interviewerRole;

    @Value("${fr.insee.pearljam.admin.role:#{null}}")
    private String adminRole;

    @Value("${fr.insee.pearljam.user.local.role:#{null}}")
    private String userLocalRole;

    @Value("${fr.insee.pearljam.user.national.role:#{null}}")
    private String userNationalRole;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO : variabiliser path /api...
        http.sessionManagement().disable();
        http.cors(withDefaults())
                .authorizeRequests()
                .antMatchers("/swagger-ui/**", "/swagger-ui.html/**", "/v3/api-docs/**", "/csrf", "/", "/webjars/**",
                        "/swagger-resources/**")
                .permitAll()
                .antMatchers("/environnement", "/healthcheck").permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(HttpMethod.GET, Constants.API_SURVEYUNITS)
                .hasAnyRole(adminRole, interviewerRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.GET, Constants.API_SURVEYUNITS_TEMP_ZONE)
                .hasAnyRole(adminRole, interviewerRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.POST, Constants.API_SURVEYUNITS).hasAnyRole(adminRole)
                .antMatchers(HttpMethod.POST, Constants.API_SURVEYUNITS_INTERVIEWERS).hasAnyRole(adminRole)
                .antMatchers(HttpMethod.GET, Constants.API_SURVEYUNITS_CLOSABLE)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.GET, Constants.API_SURVEYUNIT_ID).hasAnyRole(adminRole, interviewerRole)
                .antMatchers(HttpMethod.PUT, Constants.API_SURVEYUNIT_ID).hasAnyRole(adminRole, interviewerRole)
                .antMatchers(HttpMethod.POST, Constants.API_SURVEYUNIT_ID_TEMP_ZONE)
                .hasAnyRole(adminRole, interviewerRole)
                .antMatchers(HttpMethod.DELETE, Constants.API_SURVEYUNIT_ID).hasAnyRole(adminRole)
                .antMatchers(HttpMethod.PUT, Constants.API_SURVEYUNIT_ID_STATE)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.GET, Constants.API_SURVEYUNIT_ID_STATES)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.PUT, Constants.API_SURVEYUNIT_ID_COMMENT)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.PUT, Constants.API_SURVEYUNIT_ID_VIEWED)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.PUT, Constants.API_SURVEYUNIT_ID_CLOSE)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.PUT, Constants.API_SURVEYUNIT_ID_CLOSINGCAUSE)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.GET, Constants.API_ADMIN_CAMPAIGNS).hasAnyRole(adminRole)
                .antMatchers(HttpMethod.GET, Constants.API_CAMPAIGNS)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.GET, Constants.API_INTERVIEWER_CAMPAIGNS).hasAnyRole(interviewerRole)
                .antMatchers(HttpMethod.GET, Constants.API_CAMPAIGNS_SU_STATECOUNT)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.GET, Constants.API_CAMPAIGNS_SU_CONTACTOUTCOMES)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.POST, Constants.API_CAMPAIGN).hasAnyRole(adminRole)
                .antMatchers(HttpMethod.DELETE, Constants.API_CAMPAIGN_ID).hasAnyRole(adminRole)
                .antMatchers(HttpMethod.PUT, Constants.API_CAMPAIGN_ID).hasAnyRole(adminRole)
                .antMatchers(HttpMethod.PUT, Constants.API_CAMPAIGN_COLLECTION_DATES)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.GET, Constants.API_CAMPAIGN_ID_INTERVIEWERS)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.GET, Constants.API_CAMPAIGN_ID_SURVEYUNITS)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.GET, Constants.API_CAMPAIGN_ID_SU_ABANDONED)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.GET, Constants.API_CAMPAIGN_ID_SU_NOTATTRIBUTED)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.GET, Constants.API_CAMPAIGN_ID_SU_STATECOUNT)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.GET, Constants.API_CAMPAIGN_ID_SU_INTERVIEWER_STATECOUNT)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.GET, Constants.API_CAMPAIGN_ID_SU_NOT_ATTRIBUTED_STATECOUNT)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.GET, Constants.API_CAMPAIGN_ID_SU_CONTACTOUTCOMES)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.GET, Constants.API_CAMPAIGN_ID_SU_INTERVIEWER_CONTACTOUTCOMES)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.GET, Constants.API_CAMPAIGN_ID_SU_NOT_ATTRIBUTED_CONTACTOUTCOMES)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.GET, Constants.API_CAMPAIGN_ID_SU_INTERVIEWER_CLOSINGCAUSES)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.PUT, Constants.API_CAMPAIGN_ID_OU_ID_VISIBILITY)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.GET, Constants.API_CAMPAIGN_ID_VISIBILITIES).hasAnyRole(adminRole)
                .antMatchers(HttpMethod.GET, Constants.API_CAMPAIGNS_ID_ON_GOING).hasAnyRole(adminRole)
                .antMatchers(HttpMethod.GET, Constants.API_INTERVIEWERS)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.POST, Constants.API_INTERVIEWERS).hasAnyRole(adminRole)
                .antMatchers(HttpMethod.GET, Constants.API_INTERVIEWERS_SU_STATECOUNT)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.GET, Constants.API_INTERVIEWER_ID_CAMPAIGNS)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.GET, Constants.API_USER).hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.POST, Constants.API_USER).hasAnyRole(adminRole)
                .antMatchers(HttpMethod.GET, Constants.API_USER_ID).hasAnyRole(adminRole)
                .antMatchers(HttpMethod.PUT, Constants.API_USER_ID).hasAnyRole(adminRole)
                .antMatchers(HttpMethod.DELETE, Constants.API_USER_ID).hasAnyRole(adminRole)
                .antMatchers(HttpMethod.PUT, Constants.API_USER_ID_ORGANIZATIONUNIT_ID).hasAnyRole(adminRole)
                .antMatchers(HttpMethod.POST, Constants.API_ORGANIZATIONUNIT).hasAnyRole(adminRole)
                .antMatchers(HttpMethod.POST, Constants.API_ORGANIZATIONUNITS).hasAnyRole(adminRole)
                .antMatchers(HttpMethod.GET, Constants.API_ORGANIZATIONUNITS).hasAnyRole(adminRole)
                .antMatchers(HttpMethod.DELETE, Constants.API_ORGANIZATIONUNIT_ID).hasAnyRole(adminRole)
                .antMatchers(HttpMethod.POST, Constants.API_ORGANIZATIONUNIT_ID_USERS).hasAnyRole(adminRole)
                .antMatchers(HttpMethod.PUT, Constants.API_PREFERENCES)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.POST, Constants.API_MESSAGE)
                .hasAnyRole(adminRole, interviewerRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.GET, Constants.API_MESSAGES_ID)
                .hasAnyRole(adminRole, interviewerRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.POST, Constants.API_VERIFYNAME)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.GET, Constants.API_MESSAGEHISTORY)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.PUT, Constants.API_MESSAGE_MARK_AS_READ)
                .hasAnyRole(adminRole, interviewerRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.PUT, Constants.API_MESSAGE_MARK_AS_DELETED)
                .hasAnyRole(adminRole, interviewerRole, userLocalRole, userNationalRole)
                .antMatchers(HttpMethod.POST, Constants.API_CREATEDATASET).hasAnyRole(adminRole)
                .antMatchers(HttpMethod.DELETE, Constants.API_DELETEDATASET).hasAnyRole(adminRole)
                .antMatchers(HttpMethod.GET, Constants.API_CHECK_HABILITATION)
                .hasAnyRole(adminRole, userLocalRole, userNationalRole, interviewerRole)
                .antMatchers(HttpMethod.POST, Constants.API_MAIL).hasAnyRole(adminRole, interviewerRole)
                .anyRequest().denyAll()
                .and()
                .oauth2ResourceServer()
                .jwt();
    }

    @Bean
    public UserProvider getUserProvider() {
        return auth -> {
            final Jwt jwt = (Jwt) auth.getPrincipal();
            return new User(jwt.getClaimAsString(stampClaim), jwt.getClaimAsString(nameClaim));
        };
    }

    // @Bean
    // public CorsConfigurationSource corsConfigurationSource() {
    // CorsConfiguration configuration = new CorsConfiguration();
    // configuration.setAllowedOrigins(Arrays.asList(allowedOrigin.get()));
    // configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT",
    // "DELETE"));
    // configuration.setAllowedHeaders(Arrays.asList("*"));
    // configuration.addExposedHeader("Content-Disposition");
    // UrlBasedCorsConfigurationSource source = new
    // UrlBasedCorsConfigurationSource();
    // source.registerCorsConfiguration("/**", configuration);
    // return source;
    // }

}