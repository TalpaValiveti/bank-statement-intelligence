package com.bankstatement.statement.controller;

import com.bankstatement.statement.dto.StatementUploadResponse;
import com.bankstatement.statement.entity.Statement;
import com.bankstatement.statement.service.StatementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/statements")
@RequiredArgsConstructor
@Slf4j
public class StatementController {

    private final StatementService statementService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StatementUploadResponse> uploadStatement(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId) throws IOException {
        log.info("Received upload request for userId: {}", userId);
        StatementUploadResponse response = statementService.uploadStatement(file, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Statement>> getStatementsByUser(
            @PathVariable Long userId) {
        List<Statement> statements = statementService.getStatementsByUserId(userId);
        return ResponseEntity.ok(statements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Statement> getStatementById(
            @PathVariable Long id) {
        Statement statement = statementService.getStatementById(id);
        return ResponseEntity.ok(statement);
    }
}
