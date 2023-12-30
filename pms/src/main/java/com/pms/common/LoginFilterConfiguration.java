package com.pms.common;

import com.pms.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * ClassName: LoginFilterConfiguration
 * Package: com.pms.common
 * Description:
 * Author: JingYin233
 * Create: 2023/11/26 - 23:44
 */
@Configuration
public class LoginFilterConfiguration {
    @Bean
    public RegistrationBean myFilter(){
        LoginFilter loginFilter = new LoginFilter();
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(loginFilter);
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
        return filterRegistrationBean;
    }
}
