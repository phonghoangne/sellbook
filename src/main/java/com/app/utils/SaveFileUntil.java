package com.app.utils;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class SaveFileUntil {
//    private final Path root1 = Paths.get("src/main/resources/static/DefaultImg");

//    public void save(MultipartFile file) {
//        try {
//            if (!Files.exists(root1)) {
//                Files.createDirectory(root1);
//            }
//            Files.copy(file.getInputStream(), this.root1.resolve(file.getOriginalFilename()),
//                    StandardCopyOption.REPLACE_EXISTING);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    private static final Path root = Paths.get("src/uploads");
    //"D:\\STUDYYYYY\\Java5\\Project_Java5\\An_Phuc\\1_thucHanhLab\\1_thucHanhLab\\lab
    public static void save(MultipartFile file ,Path root ) {
        Path filePath = root;
        System.out.println("filePath : "+filePath.toString());
        try {
            if (!Files.exists(filePath)) {
                Files.createDirectory(filePath);
            }
            Files.copy(file.getInputStream(), filePath.resolve(file.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
