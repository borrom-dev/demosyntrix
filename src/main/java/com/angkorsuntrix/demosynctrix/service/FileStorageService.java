package com.angkorsuntrix.demosynctrix.service;

import com.angkorsuntrix.demosynctrix.exception.FileStorageException;
import com.angkorsuntrix.demosynctrix.property.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties properties) {
        fileStorageLocation = Paths.get(properties.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw new FileStorageException("Could not create the directory where the uploaded", e);
        }
    }

    public String storeFile(long userId, MultipartFile file) {
        final String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! filename contain invalid path");
            }
            final Path userPath = Paths.get(String.valueOf(userId));
            final Path targetPath = fileStorageLocation.resolve(userPath);
            final File userDir = new File(targetPath.toString());
            if (!userDir.exists()) {
                if (!userDir.mkdirs()) {
                    throw new FileStorageException("Could not crate user directory");
                }
            }
            final Path targetLocation = targetPath.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new FileStorageException("Could not store file: " + fileName);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            final Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            final Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileStorageException("File not found: " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new FileStorageException("File not found: " + fileName, e);
        }
    }

    public List<String> loadFilesResource(long userId) {
        final Path filesPath = this.fileStorageLocation.resolve(String.valueOf(userId)).normalize();
        try {
            return Files.walk(filesPath)
                    .filter(Files::isRegularFile)
                    .map(path -> this.fileStorageLocation.toString().concat(path.getFileName().toString()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
