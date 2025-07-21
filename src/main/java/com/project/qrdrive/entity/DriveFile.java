package com.project.qrdrive.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "driveFile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriveFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String fileName;
    private String filePath;
    private LocalDateTime uploadedAt;


}
