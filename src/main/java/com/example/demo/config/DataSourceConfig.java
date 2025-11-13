package com.example.demo.config;

import com.example.demo.util.AwsSecretsManagerUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Value("${aws.secrets.name}")
    private String secretName;

    @Value("${aws.region:us-east-1}")
    private String awsRegion;

    @Bean
    public DataSource dataSource() {
        Map<String, String> secret = AwsSecretsManagerUtil.getSecret(secretName, awsRegion);

        String url = secret.containsKey("url") ?
                secret.get("url") :
                "jdbc:mysql://" + secret.get("host") + ":" + secret.get("port") + "/" + secret.get("dbname");

        return DataSourceBuilder.create()
                .url(url)
                .username(secret.get("username"))
                .password(secret.get("password"))
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
}

