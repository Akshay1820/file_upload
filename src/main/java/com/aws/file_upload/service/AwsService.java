package com.aws.file_upload.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;


import java.net.URL;
import java.time.Duration;
import java.util.UUID;

@Service
public class AwsService {

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Autowired
    private S3Client s3Client;

    @Autowired
    private S3Presigner s3Presigner;


    public String uploadFile(MultipartFile file)  {
        String fileKey= UUID.randomUUID()+"_"+file.getOriginalFilename();
        String contentType=file.getContentType();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(fileKey)
                .contentType(contentType)
                .build();
        try{
             s3Client.putObject(putObjectRequest,
                    RequestBody.fromBytes(file.getBytes()));
             return fileKey;
        }
        catch (Exception e){
            System.out.println("Failed to upload file");
            return null;
        }
    }

    public URL getDownloadUrl(String fileKey){
        GetObjectRequest getObjectRequest= GetObjectRequest.builder()
                .bucket(bucket)
                .key(fileKey)
                .build();

        GetObjectPresignRequest getObjectPresignRequest=GetObjectPresignRequest.builder()
                .getObjectRequest(getObjectRequest)
                .signatureDuration(Duration.ofMinutes(10))
                .build();
      return s3Presigner.presignGetObject(getObjectPresignRequest).url();

    }

}
