package huce.edu.vn.appdocsach.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import huce.edu.vn.appdocsach.constants.AppConst;
import huce.edu.vn.appdocsach.utils.AppLogger;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("api/image")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageController {

    AppLogger<ImageController> logger = new AppLogger<>(ImageController.class);

    @Operation(summary = "Lấy ảnh avatar mặc định")
    @GetMapping(AppConst.DEFAULT_AVATAR_FILENAME)
    public ResponseEntity<Resource> getDefaultAvatar() {
        logger.onStart(Thread.currentThread());
        return ResponseEntity
            .ok()
            .contentType(MediaType.valueOf("image/png"))
            .body(new FileSystemResource(AppConst.DEFAULT_AVATAR_FILE_DIR));
    }

    @Operation(summary = "Lấy ảnh bìa sách mặc định")
    @GetMapping(AppConst.DEFAULT_COVER_IMAGE_FILENAME)
    public ResponseEntity<Resource> getDefaultCoverImage() {
        logger.onStart(Thread.currentThread());
        return ResponseEntity
            .ok()
            .contentType(MediaType.valueOf("image/png"))
            .body(new FileSystemResource(AppConst.DEFAULT_COVER_IMAGE_FILE_DIR));
    }
}
