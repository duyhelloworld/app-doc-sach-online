package huce.edu.vn.appdocsach.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import huce.edu.vn.appdocsach.constants.AppConst;
import huce.edu.vn.appdocsach.utils.AppLogger;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api/image")
public class ImageController {

    private AppLogger<ImageController> logger = new AppLogger<>(ImageController.class);

    @Operation(summary = "Lấy ảnh avatar mặc định")
    @GetMapping(AppConst.DEFAULT_AVATAR_FILENAME_WITH_EXTENSION)
    public ResponseEntity<Resource> getDefaultAvatar() {
        logger.onStart(Thread.currentThread());
        return ResponseEntity
            .ok()
            .contentType(AppConst.PNG)
            .body(new FileSystemResource(Path.of(AppConst.DEFAULT_AVATAR_PATH)));
    }

    @Operation(summary = "Lấy ảnh bìa sách mặc định")
    @GetMapping(AppConst.DEFAULT_COVER_IMAGE_FILENAME_WITH_EXTENSION)
    public ResponseEntity<Resource> getDefaultCoverImage() {
        logger.onStart(Thread.currentThread());
        return ResponseEntity
            .ok().contentType(AppConst.PNG)
            .body(new FileSystemResource(Path.of(AppConst.DEFAULT_COVER_IMAGE_PATH)));
    }
}
