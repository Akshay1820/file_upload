package com.aws.file_upload.utils;


import com.aws.file_upload.exception.InvalidFileTypeException;
import org.springframework.web.multipart.MultipartFile;


public class FileUtils {

    private FileUtils(){};

    public static void checkFileExtension(MultipartFile file) {
        String fileName= file.getOriginalFilename();
       if(fileName.endsWith(".exe") || fileName.endsWith(".bat")  || fileName.endsWith(".zip")){
           throw new InvalidFileTypeException("This type of file is not acceptable");
       }
    }
}
