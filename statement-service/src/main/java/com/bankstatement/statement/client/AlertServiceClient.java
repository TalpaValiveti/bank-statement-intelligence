package com.bankstatement.statement.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlertServiceClient {

    private final RestTemplate restTemplate;

    @Value("${services.alert-service.url:http://localhost:8084}")
    private String alertServiceUrl;

    public void sendUploadNotification(Long userId, Long statementId, String fileName) {
        try {
            String url = alertServiceUrl + "/api/alerts/notify/upload";
            log.info("Sending upload notification for statementId: {}", statementId);
            restTemplate.postForEntity(url, null, Void.class);
        } catch (Exception e) {
            log.warn("Failed to send upload notification for statementId: {}. Error: {}", statementId, e.getMessage());
        }
    }
}
