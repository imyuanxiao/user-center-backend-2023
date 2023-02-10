package com.xiao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//
//@Configuration
//@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        // 构建文档
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        // 文档信息
        Docket build = docket.apiInfo(apiInfo())
                // 查询
                .select()
                // 注解包的路径
                .apis(RequestHandlerSelectors.basePackage("com.xiao.controller"))
                // 任何路径
                .paths(PathSelectors.any())
                .build();
        return build;

    }
    /* *
     * <p> 文档信息</p>
     * @Param []
     * @Return springfox.documentation.service.ApiInfo
     */
    private ApiInfo apiInfo() {
        // 文档对象构建器
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        // 文档标题
        ApiInfo apiInfo = apiInfoBuilder.title("XIAO-USER-CENTER API doc")
                // 描述信息
                .description("Test OpenAPI")
                // 版本号
                .version("v1")
                // 联系人
                .contact(new Contact("Xiao", "https://github.com/imyuanxiao", "imyuanxiao@126.com"))
                // 声明许可
                .license("XIAO LICENSE")
                // 许可路径，这边是我的github
                .licenseUrl("https://github.com/imyuanxiao")
                .build();

        return apiInfo;

    }
}
