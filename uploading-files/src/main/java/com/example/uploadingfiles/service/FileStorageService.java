package com.example.uploadingfiles.service;

import com.example.uploadingfiles.config.FileStorageProperties;
import com.example.uploadingfiles.entity.FileInfo;
import com.example.uploadingfiles.exception.types.*;
import com.example.uploadingfiles.repository.FileInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;

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
            throw new UnableToCreateDirectoryException("Could not create the directory where the uploaded files willl be stored", ex);
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

        //uuid로 fileInfo에서 savedName 찾는다.
        FileInfo fileInfo = fileInfoRepository.findByUuid(uuid)
                .orElseThrow(() -> new FileInfoNotFoundException("File info not found with uuid"));
        String savedName = fileInfo.getSavedName();

        //서버에서 savedName으로 파일 찾는다.
        Resource resource;

        try {
            Path filePath = this.fileStorageLocation.resolve(savedName).normalize();
            resource = (Resource) new UrlResource(filePath.toUri());
            if (resource.exists()) {

            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        //파일을 resource로 돌려준다: contetnType 알아오자.

        return resource;
    }
}
