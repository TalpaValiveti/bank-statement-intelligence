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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatementService {

    private final StatementRepository statementRepository;

    private static final List<String> ALLOWED_TYPES = Arrays.asList(
            "application/pdf",
            "image/jpeg",
            "image/png",
            "image/tiff"
    );

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    private static final String UPLOAD_DIR = "./uploads";

    public StatementUploadResponse uploadStatement(MultipartFile file, Long userId) throws IOException {
        log.info("Uploading statement for userId: {}", userId);

        validateFile(file);

        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = getFileExtension(originalFileName);
        String storedFileName = UUID.randomUUID().toString() + "." + fileExtension;

        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(storedFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Statement statement = Statement.builder()
                .userId(userId)
                .fileName(originalFileName)
                .filePath(filePath.toString())
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
                .message("Statement uploaded successfully")
                .build();
    }

    public List<Statement> getStatementsByUserId(Long userId) {
        return statementRepository.findByUserId(userId);
    }

    public Statement getStatementById(Long id) {
        return statementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Statement not found with id: " + id));
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty or null");
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File size exceeds maximum allowed size of 10MB");
        }
        if (!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new IllegalArgumentException("File type not allowed. Allowed types: PDF, JPEG, PNG, TIFF");
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return "bin";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
