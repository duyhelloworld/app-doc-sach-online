package huce.edu.vn.appdocsach.configs;

// @Configuration
public class OpenApiConfig {

    // private SecurityScheme createJWTWithRefreshTokenSecurityScheme() {
    //     return new SecurityScheme()
    //             .type(SecurityScheme.Type.HTTP)
    //             .bearerFormat("JWT")
    //             .name("Authorization")
    //             .scheme("bearer");
    // }

    // @Bean
    // public OpenAPI openAPI() {
    //     return new OpenAPI()
    //             .components(new Components()
    //                 .addSecuritySchemes("Bearer Authentication", createJWTWithRefreshTokenSecurityScheme())
    //             .addParameters("Refresh Token", new Parameter()
    //                 .name(AuthContants.REFRESH_TOKEN_HEADER_NAME)
    //                 .in(ParameterIn.HEADER.toString())
    //                 .description("Refresh Token")
    //                 .schema(new StringSchema())));
    // }
}
