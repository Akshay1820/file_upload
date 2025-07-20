package com.aws.file_upload.repository;

import com.aws.file_upload.model.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDataRepository extends JpaRepository<FileData,Long> {
}
