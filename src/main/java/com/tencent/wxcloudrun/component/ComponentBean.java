package com.tencent.wxcloudrun.component;

import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Created by zhanga/tenderfacepalm@163.com.<br/>
 * @version Version: 1.0
 * @date DateTime: 2018/10/26 12:24:00<br/>
 */
@Component
public class ComponentBean {
    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        //read timeout 600 seconds
        httpRequestFactory.setReadTimeout(600 * 1000);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }
}
