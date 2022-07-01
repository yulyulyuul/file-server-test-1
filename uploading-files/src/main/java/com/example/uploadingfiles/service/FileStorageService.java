package com.example.uploadingfiles.service;

import com.example.uploadingfiles.config.FileStorageProperties;
import com.example.uploadingfiles.entity.FileInfo;
import com.example.uploadingfiles.exception.FileInfoNotFoundWithNameException;
import com.example.uploadingfiles.exception.FileStorageException;
import com.example.uploadingfiles.exception.InvalidFilePathException;
import com.example.uploadingfiles.exception.UnableToCreateDirectoryException;
import com.example.uploadingfiles.repository.FileInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
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

    public String storeFile(MultipartFile file) {

        String originalName = StringUtils.cleanPath(file.getOriginalFilename());
        log.info("originalName은 이거다"+originalName);

        try {
            if (originalName.contains("..")) {
                throw new InvalidFilePathException("Sorry! Filename contains invalid path sequence" + originalName);
            }

//            while (fileInfoRepository.existsByName(fileName)) {
//                int number = 1;
//                fileName = fileName + "(" + Integer.toString(number) + ")";
//                number +=1;
//            }   fileName이 겹칠 때 (1), (2) 이렇게 주는 부분. hashMap으로 구현 가능할 듯.

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String savedName = originalName + timestamp.toString();
            Path targetLocation = this.fileStorageLocation.resolve(savedName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/file/downloadFile")
                    .path(savedName)
                    .toUriString();

            saveFileInfo(originalName, savedName, fileDownloadUri, file.getContentType(), file.getSize());

            return savedName;

        } catch (IOException ex) {
            throw new FileStorageException("Could not store file" + originalName + "Please try again!", ex);
        }

    }

    public void saveFileInfo(String originalName, String savedName, String downloadUri, String contentType, long size) {

        FileInfo fileInfo = new FileInfo();

        if (fileInfoRepository.existsBySavedName(savedName)) {
            fileInfo = fileInfoRepository.findBySavedName(savedName)
                    .orElseThrow(() -> new FileInfoNotFoundWithNameException("File info not found with name" + savedName));
        }

        fileInfo.setOriginalName(originalName);
        fileInfo.setSavedName(savedName);
        fileInfo.setDownloadUri(downloadUri);
        fileInfo.setContentType(contentType);
        fileInfo.setSize(size);
        log.info("파일인포는 이거다" + fileInfo.toString());

        fileInfoRepository.save(fileInfo);

    }
}
