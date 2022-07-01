package com.example.uploadingfiles.controller;

import com.example.uploadingfiles.domain.UploadFileResponse;
import com.example.uploadingfiles.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@Slf4j
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile (@RequestParam("file") MultipartFile file) {

        String savedName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/file/downloadFile")
                .path(savedName)
                .toUriString();

        return new UploadFileResponse(savedName, fileDownloadUri, file.getContentType(), file.getSize());


    }

}
