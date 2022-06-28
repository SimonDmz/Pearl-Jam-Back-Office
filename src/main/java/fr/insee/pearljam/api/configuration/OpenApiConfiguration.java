package fr.insee.pearljam.api.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

/**
 * SwaggerConfiguration is the class using to configure swagger 3 ways to
 * authenticated : - without authentication, - basic authentication - and
 * keycloak authentication
 * 
 * @author Claudel Benjamin
 * 
 */
@Configuration
public class OpenApiConfiguration {

	@Value("${fr.insee.pearljam.api.scheme}")
	private String apiScheme;

	@Value("${fr.insee.pearljam.api.host}")
	private String apiHost;

	@Value("${keycloak.realm:my-realm}")
	private String realm;

	@Value("${keycloak.auth-server-url:http://my-auth-server/auth}")
	private String authUrl;

	@Value("${fr.insee.pearljam.application.version}")
	private String version;

	public final String SCHEME_KEYCLOAK = "oauth2";

	public OpenAPI createOpenAPI() {
		System.out.println("Building an OpenApi");
		OpenAPI openAPI = new OpenAPI()
				.info(new Info().title("Pearl-Jam API")
						.description("Back-office services for PearlJam")
						.version(version)
						.contact(new Contact().name("Pearl-Jam API")
								.url("https://github.com/InseeFr/Pearl-Jam-Back-Office"))
						.license(new License().name("LICENSEE")
								.url("https://github.com/InseeFr/Pearl-Jam-Back-Office/blob/master/LICENSE")));
		final List<Server> servers = new ArrayList<>();
		servers.add(new Server().url(apiScheme + "://" + apiHost));
		openAPI.setServers(servers);
		return openAPI;
	}

	@Bean
	@ConditionalOnProperty(name = "fr.insee.pearljam.application.mode", havingValue = "keycloak", matchIfMissing = true)
	public OpenAPI keycloakOpenAPI() {
		final OpenAPI openapi = createOpenAPI();
		System.out.println("Building an OpenApi for keycloak");
		openapi.components(new Components().addSecuritySchemes(SCHEME_KEYCLOAK, new SecurityScheme()
				.type(SecurityScheme.Type.OAUTH2).in(SecurityScheme.In.HEADER).description("Authentification keycloak")
				.flows(new OAuthFlows().authorizationCode(new OAuthFlow()
						.authorizationUrl(authUrl + "/realms/" + realm + "/protocol/openid-connect/auth")
						.tokenUrl(authUrl + "/realms/" + realm + "/protocol/openid-connect/token")
						.refreshUrl(authUrl + "/realms/" + realm + "/protocol/openid-connect/token")))));
		return openapi;
	}

	@ConditionalOnProperty(name = "fr.insee.pearljam.application.mode", havingValue = "keycloak", matchIfMissing = false)
	@Bean
	public OperationCustomizer addKeycloak() {
		return (operation, handlerMethod) -> {
			if (StringUtils.equalsIgnoreCase("NomDuEndpoint",
					handlerMethod.getMethod().getName())) {
				return operation;
			}
			return operation.addSecurityItem(new SecurityRequirement().addList(SCHEME_KEYCLOAK));
		};
	}

	@Bean
	@ConditionalOnProperty(name = "fr.insee.pearljam.application.mode", havingValue = "noauth", matchIfMissing = true)
	public OpenAPI customOpenAPI() {
		final OpenAPI openapi = createOpenAPI();
		System.out.println("Building an OpenApi for noauth!!");
		return openapi;
	}

	// TODO handle basic auth-mode

}