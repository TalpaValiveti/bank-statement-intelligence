package com.bankstatement.statement.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class AiServiceClient {

    private final RestTemplate restTemplate;

    @Value("${services.ai-service.url:http://localhost:8083}")
    private String aiServiceUrl;

    public void triggerAnalysis(Long statementId) {
        try {
            String url = aiServiceUrl + "/api/ai/analyse/" + statementId;
            log.info("Triggering AI analysis for statementId: {}", statementId);
            restTemplate.postForEntity(url, null, Void.class);
        } catch (Exception e) {
            log.warn("Failed to trigger AI analysis for statementId: {}. Error: {}", statementId, e.getMessage());
        }
    }
}
