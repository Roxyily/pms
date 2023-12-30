package com.pms.common;

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

@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket getDocket(){
        //创建Docket对象
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())//指定API接口文件首页信息
                .select()//初始化并返回一个API选择构造器
                .apis(RequestHandlerSelectors.basePackage("com.pms.controller"))//为任何接口生成API文档
                .paths(PathSelectors.any())//可以根据url路径设置哪些请求加入文档，忽略哪些请求
                .build(); //创建API文档
        return docket;
    }
    private ApiInfo getApiInfo(){
        //创建作者 对象
        Contact contact = new Contact("Jingyin","http://JingYin233,github.io","1545912679@qq.com");
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("《多小区物业管理后端接口文档》") //文档标题
                .description("暂无描述")//文档描述
                .contact(contact)//文档作者
                .version("V1.0")//文档版本
                .build(); //构建
        return apiInfo;
    }
}
