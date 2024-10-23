package cc.becurious.framework.config;

import cc.becurious.common.utils.StringUtils;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // 设置全局的 SecurityRequirement，适用于所有路径
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
//                        .addSecuritySchemes("bearerAuth", new SecurityScheme().in(SecurityScheme.In.HEADER)
//                                .type(SecurityScheme.Type.HTTP)
//                                .scheme("bearer")
//                                .bearerFormat("JWT")));
                        .addSecuritySchemes("Authorization", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .name("Authorization")
                                .in(SecurityScheme.In.HEADER)));
    }

    @Bean
    public OpenApiCustomizer securityFilter() {
        return openApi -> {

            openApi.getPaths().values().removeIf(pathItem ->
                    pathItem.readOperations().stream().anyMatch(operation ->
                            StringUtils.isEmpty(operation.getSummary())));

            // 这里是你可以根据路径自定义安全性需求的地方
            openApi.getPaths().forEach((path, pathItem) -> {
                // 对所有匹配 /.* 的路径应用安全配置
                if (path.matches("/.*")) {
                    pathItem.readOperations().forEach(operation ->
                            operation.addSecurityItem(new SecurityRequirement().addList("Authorization")));
                }
            });
        };
    }

}
