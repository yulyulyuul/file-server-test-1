package com.example.uploadingfiles.controller;

import com.example.uploadingfiles.entity.FileInfo;
import com.example.uploadingfiles.exception.types.FileInfoNotFoundException;
import com.example.uploadingfiles.exception.types.FileNotFoundException;
import com.example.uploadingfiles.repository.FileInfoRepository;
import com.example.uploadingfiles.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @PostMapping("/uploadFile")
    public String uploadFile (@RequestParam("file") MultipartFile file) {

        FileInfo fileInfo = fileStorageService.storeFile(file);

        String fileDownloadUri = fileStorageService.createDownloadUri(fileInfo);

        fileStorageService.saveFileInfo(fileInfo.getOriginalName(), fileInfo.getSavedName(), fileDownloadUri, file.getContentType(), file.getSize());

        return fileInfo.getUuid();
    }

    @PostMapping("/uploadImage")
    public String uploadImageForMessage (@RequestParam("file") MultipartFile file) {

        FileInfo fileInfo = fileStorageService.storeImage(file);

        String fileDownloadUri = fileStorageService.createDownloadUri(fileInfo);

        fileStorageService.saveFileInfo(fileInfo.getOriginalName(), fileInfo.getSavedName(), fileDownloadUri, file.getContentType(), file.getSize());

        return fileInfo.getUuid();
    }

    @GetMapping("/downloadFile/{uuid}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String uuid, HttpServletRequest request) throws IOException {

            Resource resource = fileStorageService.loadFileAsResource(uuid);
            FileInfo fileInfo = fileInfoRepository.findByUuid(uuid)
                    .orElseThrow(() -> new FileInfoNotFoundException());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, fileInfo.getOriginalName())
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM.toString())
                    .body(resource);
    }
}
