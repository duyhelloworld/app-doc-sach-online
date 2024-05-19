package huce.edu.vn.appdocsach.services.abstracts.file;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ICloudinaryService {
    
    boolean isValidFileName(MultipartFile image);

    String save(byte[] file, String fileName);
    
    String save(MultipartFile file);
    
    void save(List<MultipartFile> files, String folderName);
    
    List<String> getUrls(String folderName);
    
    void delete(String folderName);

    void deleteOne(String url);
}
