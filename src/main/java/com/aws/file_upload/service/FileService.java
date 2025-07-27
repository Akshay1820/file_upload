package com.aws.file_upload.service;

import com.aws.file_upload.model.FileData;
import com.aws.file_upload.repository.FileDataRepository;
import com.aws.file_upload.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service

public class FileService {

    @Autowired
    private FileDataRepository fileDataRepository;

    @Autowired
    private AwsService awsService;


    public String uploadFile(MultipartFile file){
        if(Objects.isNull(file)){
            return null;
        }
        FileUtils.checkFileExtension(file);  // Validating file extensions


        String fileName= file.getOriginalFilename();

        Long fileSize=(file.getSize())/1024;// to get size in KB
        String fileType=file.getContentType();

        FileData fileData=new FileData();
        fileData.setFileName(fileName);
        fileData.setFileType(fileType);
        fileData.setFileSize(fileSize);

        String fileKey=awsService.uploadFile(file);
        fileData.setAwsFileId(fileKey);
        fileDataRepository.save(fileData);
        return fileName;
    }


    public ResponseEntity<List<FileData>> getAllFiles() {
       return ResponseEntity.ok(
               fileDataRepository.findAll());
    }

    public ResponseEntity<?> downloadFile(Long fileId) {
        Optional<FileData> fileDataExist=fileDataRepository.findById(fileId);
        if(fileDataExist.isPresent()){
            FileData fileData=fileDataExist.get();
            String awsFileKey= fileData.getAwsFileId();
            URL presignedURL=awsService.getDownloadUrl(awsFileKey);
            return ResponseEntity.ok(presignedURL);
        }
        else {
            return ResponseEntity.ok("File not found");
        }
    }
}
