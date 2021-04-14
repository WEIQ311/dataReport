package com.report.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author weiQiang
 * @date 2018/5/21
 */
@Configuration
public class DataReportonfig {

    @Value("${aliyun.sms.accessKey:''}")
    private String accessKey;

    @Value("${aliyun.sms.accessKeySecret:''}")
    private String secertKey;

    @Bean(name = "IAcsClient")
    public IAcsClient iAcsClient() throws ClientException {
        // 设置超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKey, secertKey);
        String product = "Dysmsapi";
        String domain = "dysmsapi.aliyuncs.com";
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        return acsClient;
    }
}
