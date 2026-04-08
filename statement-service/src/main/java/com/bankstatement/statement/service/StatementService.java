package com.bankstatement.statement.service;

import com.bankstatement.statement.dto.StatementUploadResponse;
import com.bankstatement.statement.entity.Statement;
import com.bankstatement.statement.entity.StatementStatus;
import com.bankstatement.statement.repository.StatementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatementService {

    private final StatementRepository statementRepository;
    private final ObjectStorageService objectStorageService;

    private static final List<String> ALLOWED_TYPES = Arrays.asList(
            "application/pdf",
            "image/jpeg",
            "image/png",
            "image/tiff"
    );

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    public StatementUploadResponse uploadStatement(MultipartFile file,
            Long userId) throws IOException {
        log.info("Uploading statement for userId: {}", userId);

        validateFile(file);

        String objectName = objectStorageService.uploadFile(file, userId);
        String fileUrl = objectStorageService.getObjectUrl(objectName);

        Statement statement = Statement.builder()
                .userId(userId)
                .fileName(StringUtils.cleanPath(file.getOriginalFilename()))
                .filePath(fileUrl)
                .fileSize(file.getSize())
                .fileType(file.getContentType())
                .status(StatementStatus.UPLOADED)
                .build();

        Statement saved = statementRepository.save(statement);
        log.info("Statement saved with id: {}", saved.getId());

        return StatementUploadResponse.builder()
                .id(saved.getId())
                .fileName(saved.getFileName())
                .fileType(saved.getFileType())
                .fileSize(saved.getFileSize())
                .status(saved.getStatus().name())
                .uploadedAt(saved.getUploadedAt())
                .message("Statement uploaded successfully to OCI Object Storage")
                .build();
    }

    public List<Statement> getStatementsByUserId(Long userId) {
        return statementRepository.findByUserId(userId);
    }

    public Statement getStatementById(Long id) {
        return statementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Statement not found with id: " + id));
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty or null");
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException(
                    "File size exceeds maximum allowed size of 10MB");
        }
        if (!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new IllegalArgumentException(
                    "File type not allowed. Allowed types: PDF, JPEG, PNG, TIFF");
        }
    }
}
