package com.zetcco.jobscoutserver.services.support;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    String store(MultipartFile file);

    Stream<Path> loadAll();

    Resource load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();

    String getResourceURL(String filename);

}