package com.example.uploadingfiles.service;

import com.example.uploadingfiles.config.FileStorageProperties;
import com.example.uploadingfiles.entity.FileInfo;
import com.example.uploadingfiles.exception.types.MalformedUrlException;
import com.example.uploadingfiles.exception.types.*;
import com.example.uploadingfiles.repository.FileInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new UnableToCreateDirectoryException("Could not create the directory where the uploaded files willl be stored");
        }
    }

    @Autowired
    private FileInfoRepository fileInfoRepository;

    public FileInfo storeFile(MultipartFile file) {
        FileInfo fileInfo = createSavedName(file);

        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileInfo.getSavedName());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileInfo;
        } catch (IOException ex) {
            throw new CannotStoreFileException();
        }
    }

    private FileInfo createSavedName(MultipartFile file) {
        FileInfo fileInfo = new FileInfo();
        String originalName = StringUtils.cleanPath(file.getOriginalFilename());
        if (originalName.contains("..")) {
            throw new InvalidFilePathException("Sorry! Filename contains invalid path sequence" + originalName);
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String savedName = originalName + timestamp.toString();
        fileInfo.setOriginalName(originalName);
        fileInfo.setSavedName(savedName);
        return fileInfo;
    }

    public void saveFileInfo(String originalName, String savedName, String downloadUri, String contentType, long size) {
        FileInfo fileInfo = new FileInfo();
        if (fileInfoRepository.existsBySavedName(savedName)) {
            fileInfo = fileInfoRepository.findBySavedName(savedName)
                    .orElseThrow(() -> new FileInfoNotFoundException("File info not found with name" + savedName));
        }
        fileInfo.setOriginalName(originalName);
        fileInfo.setSavedName(savedName);
        fileInfo.setDownloadUri(downloadUri);
        fileInfo.setContentType(contentType);
        fileInfo.setSize(size);
        fileInfoRepository.save(fileInfo);
    }

    public Resource loadFileAsResource(String uuid) {
        FileInfo fileInfo = fileInfoRepository.findByUuid(uuid)
                .orElseThrow(() -> new FileInfoNotFoundException());
        String savedName = fileInfo.getSavedName();
        try {
            Path filePath = this.fileStorageLocation.resolve(savedName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException();
            }
        } catch (MalformedURLException e) {
            throw new MalformedUrlException();
        }
    }

    public String createDownloadUri(FileInfo fileInfo) {

        String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/file/downloadFile")
                .path(fileInfo.getSavedName())
                .toUriString();

    return downloadUri;
    }

    public FileInfo storeImage(MultipartFile file) {

        FileInfo fileInfo = createSavedName(file);
        if (isNotImage(file)) {
            throw new FileIsNotImageException();
        }
        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileInfo.getSavedName());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileInfo;
        } catch (IOException ex) {
            throw new CannotStoreFileException();
        }
    }

    private boolean isNotImage(MultipartFile file) {

        String detailedContentType = file.getContentType();
        String[] contentTypeParts = detailedContentType.split("/");
        String contentType = contentTypeParts[0];

        if (contentType.equals("image")) { return false; }
        else return true;

    }
}
