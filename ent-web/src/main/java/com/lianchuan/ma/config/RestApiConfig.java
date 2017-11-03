package com.lianchuan.ma.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableWebMvc
@EnableSwagger2
@Configuration
public class RestApiConfig extends WebMvcConfigurationSupport{

//	@Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowedOrigins("*")
//                .allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")
//                .allowCredentials(false).maxAge(3600);
//    }
//	
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .tags(new Tag("role", "创建角色， 修改角色，获取权限列表， 获取角色权限列表， 获取角色列表"), getTags())
                .select()
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
//              .title("API 列表")
//              .description("api description")
//              .termsOfServiceUrl("http://www.xxx.com")
//              .contact(new Contact("API", "", "shensiping@51lianchuan.com"))
              .version("1.0")
              .build();
    }
    
    private Tag[] getTags() {
        Tag[] tags = {
            new Tag("system", "修改密码，获取菜单列表,获取管理员信息,登陆,登出"),
            new Tag("manager", "添加用户，修改用户角色，获取用户列表，获取所有角色列表， 重置用户密码"),
            new Tag("apply", "业务申请"),
            new Tag("judge", "业务审核"),
            new Tag("push", "业务推送")
        };
        return tags;
    }
    
}
