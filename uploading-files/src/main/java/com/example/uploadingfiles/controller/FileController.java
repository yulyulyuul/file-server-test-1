package com.example.uploadingfiles.controller;

import com.example.uploadingfiles.domain.UploadFileResponse;
import com.example.uploadingfiles.entity.FileInfo;
import com.example.uploadingfiles.repository.FileInfoRepository;
import com.example.uploadingfiles.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile (@RequestParam("file") MultipartFile file) {

        FileInfo fileInfo  = fileStorageService.storeFile(file);

        String fileDownloadUri = fileStorageService.createDownloadUri(fileInfo);

        fileStorageService.saveFileInfo(fileInfo.getOriginalName(), fileInfo.getSavedName(), fileDownloadUri, file.getContentType(), file.getSize());

        return new UploadFileResponse(fileInfo.getSavedName(), fileDownloadUri, file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadImage")
    public UploadFileResponse uploadImageForMessage (@RequestParam("file") MultipartFile file) {

        FileInfo fileInfo = fileStorageService.storeImage(file);

        String fileDownloadUri = fileStorageService.createDownloadUri(fileInfo);

        fileStorageService.saveFileInfo(fileInfo.getOriginalName(), fileInfo.getSavedName(), fileDownloadUri, file.getContentType(), file.getSize());

        return new UploadFileResponse(fileInfo.getSavedName(), fileDownloadUri, file.getContentType(), file.getSize());
    }

    @GetMapping("/downloadFile/{uuid}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String uuid, HttpServletRequest request) {

        log.info(uuid);log.info(uuid);log.info(uuid);log.info(uuid);log.info(uuid);

        Resource resource = fileStorageService.loadFileAsResource(uuid);

        return ResponseEntity.ok()
                .body(resource);

    }
}
