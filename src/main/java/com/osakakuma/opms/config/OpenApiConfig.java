package com.osakakuma.opms.config;

import com.osakakuma.opms.config.model.CognitoUser;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SecurityScheme(
        name = "authorization",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,
        description = """
                JWT authorization header using token returned from cognito login page redirection. \n
                Important, the Bearer token is used directly from cognito without "Bearer" prefix. \n
                Example, "eyJraWQiOiI2VVwvQ1djOU1xTmQ4U1dFeFQzUDdG..."
                """
)
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openApi() {
        var config = SpringDocUtils.getConfig();
        config.addRequestWrapperToIgnore(CognitoUser.class);

        var openApi = new OpenAPI();
        openApi.addSecurityItem(new SecurityRequirement().addList("authorization"));

        var authorization = new HeaderParameter()
                .name("authorization")
                .schema(new StringSchema())
                .description("The id token from cognito login without Bearer prefix");

        var contentType = new HeaderParameter()
                .name("Content-Type")
                .schema(new StringSchema())
                .description("Content type of the header");

        var components = new Components().addParameters(authorization.getName(), authorization);
        components.addParameters(contentType.getName(), contentType);
        openApi.setComponents(components);

        return openApi;
    }
}
