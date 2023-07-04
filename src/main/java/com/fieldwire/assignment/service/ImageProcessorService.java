package com.fieldwire.assignment.service;

import java.awt.image.BufferedImage;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.multipart.MultipartFile;

import com.fieldwire.assignment.exception.FileNotFoundException;
import com.fieldwire.assignment.exception.FileStorageException;

public interface ImageProcessorService {

        public static enum ImageType {
                THUMB,
                LARGE,
                ORIGINAL
        }

        public void createSubDirectory(UUID projectId) throws FileStorageException;

        public CompletableFuture<String> createAndStoreThumbImage(MultipartFile file, BufferedImage image)
                        throws FileStorageException, FileNotFoundException, InterruptedException;

        public CompletableFuture<String> createAndStoreLargeImage(MultipartFile file, BufferedImage image)
                        throws FileStorageException, FileNotFoundException, InterruptedException;;

        public CompletableFuture<String> storeFile(MultipartFile file)
                        throws FileStorageException, FileNotFoundException, InterruptedException;;

        public void deleteFiles(String name, UUID projectId);

        public void deleteSubDirectory() throws InternalError;

}
