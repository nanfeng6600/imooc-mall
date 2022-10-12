package com.imooc.mall.config;

import com.imooc.mall.filter.AdminFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AdminFilterConfig {
    //将过滤器加入IOC容器
    @Bean
    public AdminFilter adminFilter() {
        return new AdminFilter();
    }

    //将过滤器放入过滤器链路中
    @Bean("adminFilterConf")
    public FilterRegistrationBean adminFilterConfig() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();

        filterRegistrationBean.setFilter(adminFilter());

        filterRegistrationBean.addUrlPatterns("/admin/category/*");
        filterRegistrationBean.addUrlPatterns("/admin/product/*");
        filterRegistrationBean.addUrlPatterns("/admin/order/*");
        filterRegistrationBean.setName("adminFilterConf");

        return filterRegistrationBean;
    }
}
