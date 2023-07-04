package com.fieldwire.assignment.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class CommonUtil {

    public static String getFileName(MultipartFile file) {
        return StringUtils.cleanPath(file.getOriginalFilename());
    }

    public static Path attachProjectIdtoPath(Path path, UUID projectId) {
        return Paths.get(path + "\\" + projectId.toString());
    }

}
