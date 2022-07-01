package com.example.uploadingfiles.repository;

import com.example.uploadingfiles.entity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
    Optional<FileInfo> findBySavedName(String savedName);
    Boolean existsBySavedName (String savedName);

    Optional<FileInfo> findByUuid(String uuid);
}
