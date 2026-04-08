package com.bankstatement.ai.config;

import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.SimpleAuthenticationDetailsProvider;
import com.oracle.bmc.auth.StringPrivateKeySupplier;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
@Slf4j
public class OciConfig {

    @Value("${oci.tenancy-id}")
    private String tenancyId;

    @Value("${oci.user-id}")
    private String userId;

    @Value("${oci.fingerprint}")
    private String fingerprint;

    @Value("${oci.private-key-path}")
    private String privateKeyPath;

    @Value("${oci.region}")
    private String region;

    @Bean
    public AuthenticationDetailsProvider authenticationDetailsProvider() throws IOException {
        log.info("Initializing OCI authentication for region: {}", region);
        String privateKey = new String(Files.readAllBytes(Paths.get(privateKeyPath)));
        return SimpleAuthenticationDetailsProvider.builder()
                .tenantId(tenancyId)
                .userId(userId)
                .fingerprint(fingerprint)
                .privateKeySupplier(new StringPrivateKeySupplier(privateKey))
                .region(com.oracle.bmc.Region.fromRegionId(region))
                .build();
    }

    @Bean
    public ObjectStorage objectStorageClient(
            AuthenticationDetailsProvider authProvider) {
        log.info("Initializing OCI Object Storage client");
        return ObjectStorageClient.builder()
                .build(authProvider);
    }
}
