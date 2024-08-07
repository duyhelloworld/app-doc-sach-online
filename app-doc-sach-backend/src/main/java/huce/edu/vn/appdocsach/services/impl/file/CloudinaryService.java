package huce.edu.vn.appdocsach.services.impl.file;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;

import huce.edu.vn.appdocsach.constants.PagingConstants;
import huce.edu.vn.appdocsach.enums.ResponseCode;
import huce.edu.vn.appdocsach.exception.AppException;
import huce.edu.vn.appdocsach.services.abstracts.file.ICloudinaryService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class CloudinaryService implements ICloudinaryService {

    Cloudinary cloudinary; 
    
    private Map<String, Object> map = new HashMap<>();

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }
    
    @Override
    public boolean isValidFileName(MultipartFile image) {
        String fileName = image.getOriginalFilename();
        return !StringUtils.containsWhitespace(fileName)
            && PagingConstants.VALID_IMAGE_EXTENSIONS.contains(StringUtils.getFilenameExtension(fileName));
    }

    @Override
    @SuppressWarnings("unchecked")
    public String save(byte[] file, String fileName) {
        Map<String, Object> response;
        fileName = removeExtension(fileName);
        try {
            map.clear();
            map.put("resource_type", "image");
            map.put("return_error", true);
            map.put("use_filename", true);
            map.put("unique_filename", false);
            map.put("public_id", fileName);
            map.put("overwrite", true);
            response = cloudinary.uploader().upload(file, map);
            // logger.info(response);
            if (response.containsKey("error")) {
                log.error("Error when save " + fileName + " : ", response.get("error").toString());
            } else {
                return response.get("url").toString();
            }
        } catch (IOException e) {
            log.error("Error when read file", e);
        }
        return null;
    }

    private void createFolder(String folderName) {
        map.clear();
        map.put("return_error", true);
        try {
            ApiResponse response = cloudinary.api().createFolder(folderName, ObjectUtils.emptyMap());
            if (response.containsKey("error")) {
                log.error("createFolder", response.get("error").toString());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void save(List<MultipartFile> files, String folderName) {
        createFolder(folderName);
        
        map.clear();
        map.put("resource_type", "image");
        map.put("folder", folderName);
        map.put("asset_folder", folderName);
        map.put("use_filename", true);
        
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String fileName = file.getOriginalFilename();
            map.put("public_id", fileName);
            try {
                if (!isValidFileName(file)) {
                    log.error("File {} at index {} has wrong type", fileName, i);
                    continue;
                }
                cloudinary.uploader().upload(file.getBytes(), map);
                log.info("Uploaded image at index " + i + " with name : {}", fileName);
            } catch (IOException e) {
                log.error("Failed upload image at index " + i + ", file name : " + fileName, e);
            }
        }
    }

    @Override
    public List<String> getUrls(String folderName) {
        log.info("Start getUrls with input : ", folderName);
        List<String> fileUrls = new LinkedList<>();
        ApiResponse response;
        map.clear();
        map.put("return_error", true);
        map.put("type", "upload");
        map.put("prefix", folderName);
        map.put("max_results", PagingConstants.MAX_FILE_RETURN_IN_LOAD_A_CHAPTER);
        try {
            response = cloudinary.api().resources(map);
            if (response.containsKey("error")) {
                log.error("Error to get files in folder ".concat(folderName), response.get("error"));
                return fileUrls;
            }
            Object rawResources = response.get("resources");
            if (rawResources instanceof List) {
                for (Object rawResource : (List<?>) rawResources) {
                    fileUrls.add(extracUrl(rawResource.toString()));
                }
            }
        } catch (Exception e) {
            log.error("Error when load file content", e);
        }
        return fileUrls;
    }

    @Override
    public String save(MultipartFile file) {
        try {
            return save(file.getBytes(), file.getOriginalFilename());
        } catch (IOException e) {
            log.error("Error when read file ", e);
            throw new AppException(ResponseCode.FILE_CONTENT_INVALID);
        }
    }

    @Override
    public void delete(String folderName) {
        map.clear();
        map.put("type", "upload");
        map.put("resource_type", "image");
        try {
            ApiResponse apiResponse = cloudinary.api().deleteResourcesByPrefix(folderName, map);
            if (apiResponse.containsKey("error")) {
                log.error("Error while deleting folder {}".concat(folderName), apiResponse.get("error"));
            } else {
                log.info("Success deleted " + folderName);
            }
        } catch (Exception e) {
            log.error("Error when delete ", e);
        }
    }

    @Override
    public void deleteOne(String url) {
        String fileName = extractFilenameFromUrl(url);
        try {
            map.clear();
            cloudinary.uploader().destroy(fileName, map);
        } catch (IOException e) {
            log.error("Error when read file ", e);
        }
    }

    private String removeExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return fileName;
        }
        int lastIndexOfDot = fileName.lastIndexOf('.');
        if (lastIndexOfDot < 0) {
            return fileName;
        }
        return fileName.substring(0, lastIndexOfDot);
    }

    private String extracUrl(String resource) {
        String[] keyPairs = resource.toString().split(",");
        for (String pair : keyPairs) {
            String[] keyValue = pair.trim().split("=");
            if (keyValue.length == 2 && keyValue[0].equalsIgnoreCase("url")) {
                return keyValue[1].trim();
            }
        }
        return null;
    }

    private String extractFilenameFromUrl(String url) {
        return url.substring(61);
    }
}