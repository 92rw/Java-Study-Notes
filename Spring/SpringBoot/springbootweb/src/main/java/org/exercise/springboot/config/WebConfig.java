package org.exercise.springboot.config;

import org.exercise.springboot.bean.Railine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration(proxyBeanMethods = false)//使用Lite模式
public class WebConfig {
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addFormatters(FormatterRegistry registry) {
                //增加一个自定义转换器，将String转成Railine类型
                //该转换器会注入到converts容器中，底层结构是 ConcurrentHashMap
                registry.addConverter(new Converter<String, Railine>() {
                    @Override
                    public Railine convert(String s) {//s就是传入的字符串
                        if (!ObjectUtils.isEmpty(s)) {
                            String[] split = s.split(",");
                            Railine railine = new Railine();
                            railine.setName(split[0]);
                            railine.setDistance(Double.parseDouble(split[1]));
                            return railine;
                        }
                        return null;
                    }
                });
            }
        };
    }
}
