package com.aws.file_upload.controller;

import com.aws.file_upload.model.FileData;
import com.aws.file_upload.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private FileService fileService;


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file){
        String fileName=fileService.uploadFile(file);
        return ResponseEntity.ok(fileName+" file uploaded successfully");
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<?> downloadFile(@PathVariable Long fileId){
        return fileService.downloadFile(fileId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FileData>> getAllFiles(){
        return fileService.getAllFiles();
    }
}
