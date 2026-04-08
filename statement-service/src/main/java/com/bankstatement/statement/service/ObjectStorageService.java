package com.bankstatement.statement.service;

import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.oracle.bmc.objectstorage.responses.PutObjectResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ObjectStorageService {

    private final ObjectStorage objectStorageClient;

    @Value("${oci.namespace}")
    private String namespace;

    @Value("${oci.bucket-name}")
    private String bucketName;

    public String uploadFile(MultipartFile file, Long userId) throws IOException {
        String objectName = "statements/" + userId + "/" +
                UUID.randomUUID() + "_" + file.getOriginalFilename();

        log.info("Uploading file to OCI Object Storage: {}", objectName);

        PutObjectRequest request = PutObjectRequest.builder()
                .namespaceName(namespace)
                .bucketName(bucketName)
                .objectName(objectName)
                .contentLength(file.getSize())
                .contentType(file.getContentType())
                .putObjectBody(file.getInputStream())
                .build();

        PutObjectResponse response = objectStorageClient.putObject(request);
        log.info("File uploaded successfully. ETag: {}", response.getETag());

        return objectName;
    }

    public String getObjectUrl(String objectName) {
        return String.format(
                "https://objectstorage.us-chicago-1.oraclecloud.com/n/%s/b/%s/o/%s",
                namespace, bucketName, objectName.replace("/", "%2F")
        );
    }
}
