package com.example.demo.util;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import java.util.Map;

public class AwsSecretsManagerUtil {

    public static Map<String, String> getSecret(String secretName, String region) {
        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(Region.of(region))
                .build();

        GetSecretValueRequest request = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse response = client.getSecretValue(request);

        try {
            System.out.println("Check Bokadaka secret:"+response.secretString());
            return new ObjectMapper().readValue(response.secretString(), Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing secret JSON", e);
        }
    }
}

