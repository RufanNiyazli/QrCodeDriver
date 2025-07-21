package com.project.qrdrive.controller;

import com.project.qrdrive.entity.DriveFile;
import com.project.qrdrive.repository.DriveFileRepository;
import com.project.qrdrive.service.QrCodeService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/drive")

public class DriveController {
    @Autowired
    private final DriveFileRepository driveFileRepository;

    @Autowired
    private final QrCodeService qrCodeService;

    @Value("${upload.dir}")
    private String uploadDir;

    public DriveController(DriveFileRepository driveFileRepository, QrCodeService qrCodeService) {
        this.driveFileRepository = driveFileRepository;
        this.qrCodeService = qrCodeService;
    }

    @PostMapping("/upload")
    public ResponseEntity<byte[]> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) return ResponseEntity.badRequest().build();
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.copy(file.getInputStream(), filePath);

        DriveFile driveFile = new DriveFile();
        driveFile.setFileName(fileName);
        driveFile.setFilePath(filePath.toString());
        driveFile.setUploadedAt(LocalDateTime.now());
        driveFileRepository.save(driveFile);


        String downloadUrl = "http://localhost:8080/api/drive/download/" + driveFile.getId();
        byte[] qrImage = qrCodeService.generateQRCodeImage(downloadUrl, 300, 300);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(qrImage);


    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Long id) throws IOException {
        return driveFileRepository.findById(id)
                .map(file -> {
                    try {
                        Path path = Paths.get(file.getFilePath());
                        UrlResource resource = new UrlResource(path.toUri());
                        return ResponseEntity.ok()
                                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                                .header(HttpHeaders.CONTENT_DISPOSITION,
                                        "attachment; filename=\"" + file.getFileName() + "\"")
                                .body(resource);
                    } catch (MalformedURLException e) {
                        return ResponseEntity.internalServerError().build();
                    }
                }).orElse(ResponseEntity.notFound().build());
    }


}
