package com.example.demo.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import javax.sql.DataSource;

@Configuration
@Profile("prod")   // Only load this when running inside ECS/Fargate
public class DataSourceConfig {

    @Value("${aws.region}")
    private String region;

    @Value("${aws.secret.name}")
    private String secretName;

    private final ObjectMapper mapper = new ObjectMapper();

    @Bean
    public DataSource dataSource() throws Exception {

        // Create Secrets Manager client
        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(Region.of(region))
                .build();

        // Read the secret
        GetSecretValueResponse response = client.getSecretValue(
                GetSecretValueRequest.builder()
                        .secretId(secretName)
                        .build()
        );

        JsonNode secret = mapper.readTree(response.secretString());

        // Required fields
        String username = readRequired(secret, "username");
        String password = readRequired(secret, "password");

        // Determine final JDBC URL
        String url;

        if (secret.has("url")) {
            url = secret.get("url").asText();
        } else {
            // Support AWS rotation-friendly format
            String host = readRequired(secret, "host");
            String port = readRequired(secret, "port");
            String dbname = readRequired(secret, "dbname");
            url = "jdbc:mysql://" + host + ":" + port + "/" + dbname;
        }

        // Create HikariCP datasource
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);

        // Basic Aurora tuning
        ds.setMaximumPoolSize(10);
        ds.setMinimumIdle(2);
        ds.setConnectionTimeout(30000);
        ds.setIdleTimeout(300000);
        ds.setMaxLifetime(1800000);

        return ds;
    }

    /**
     * Helper - ensures required fields exist
     */
    private String readRequired(JsonNode json, String key) throws Exception {
        if (!json.has(key)) {
            throw new Exception("Missing required key in AWS Secret: " + key);
        }
        return json.get(key).asText();
    }
}