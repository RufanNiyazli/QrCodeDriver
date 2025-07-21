package com.project.qrdrive.repository;

import com.project.qrdrive.entity.DriveFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriveFileRepository extends JpaRepository<DriveFile, Long> {
}
