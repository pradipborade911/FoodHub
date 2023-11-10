package com.foodhub.repository;

import com.foodhub.execptions.FileHandlingException;
import com.foodhub.execptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class ImageService {
    @Autowired
    private Environment environment;
    String RESOURCES_DIR = ImageService.class.getResource("/")
            .getPath();
    public String save(MultipartFile image, String imageName) {
        try{
            String profilePicLocation = environment.getProperty("profile_pic_location");
            Path newImage = Paths.get(profilePicLocation + "-" + imageName);
            Files.createDirectories(newImage.getParent());
            Files.write(newImage, image.getBytes());
            return newImage.toAbsolutePath().toString();
        }catch (Exception ex){
            throw new FileHandlingException("Failed to upload profile picture");
        }
    }

    public byte[] getImageFile(String location){
        try{
            FileSystemResource imageFile =  new FileSystemResource(Paths.get(location));
            return imageFile.getContentAsByteArray();
        }catch (Exception ex){
            return null;
        }
    }

}
