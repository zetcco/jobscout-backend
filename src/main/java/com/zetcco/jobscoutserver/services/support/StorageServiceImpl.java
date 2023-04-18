package com.zetcco.jobscoutserver.services.support;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageServiceImpl implements StorageService {

    private final Path root = Paths.get("uploads");

    @Value("${server.url}")
    private String SERVER_URL;

    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    @Nullable
    public String store(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        if (originalFileName != null) {
            String[] fileNameStruct = originalFileName.split("\\.");
            String extension = fileNameStruct[fileNameStruct.length - 1];
            String filename = UUID.randomUUID().toString() + "." + extension;
            try {
                Files.copy(file.getInputStream(), this.root.resolve(filename));
                return filename;
            } catch (Exception e) {
                if (e instanceof FileAlreadyExistsException) {
                    throw new RuntimeException("A file of that name already exists.");
                }
            throw new RuntimeException("Unknown Error: " + e.getMessage());
            }
        }
        return null;
    }

    @Override
    public Stream<Path> loadAll() {
        try {
        return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
        throw new RuntimeException("Could not load the files!");
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public Resource loadAsResource(String filename) {
        return null;
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
        
    }

    public String getResourceURL(String filename) {
        if (filename == null)
            return null;
        return SERVER_URL + "/media/file/" + filename;
    }
    
}
