package com.example.uploadingfiles;

import com.example.uploadingfiles.config.FileStorageProperties;
import com.example.uploadingfiles.repository.FileInfoRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication()
@EnableConfigurationProperties({FileStorageProperties.class})
@EnableJpaRepositories(basePackageClasses = FileInfoRepository.class)
public class UploadingFilesApplication {

	public static void main(String[] args) {
		SpringApplication.run(UploadingFilesApplication.class, args);
	}

}
