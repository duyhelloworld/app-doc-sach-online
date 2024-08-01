package huce.edu.vn.appdocsach.services.impl.file;

import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import huce.edu.vn.appdocsach.services.abstracts.file.IResourceService;

@Service
public class ResourceService implements IResourceService {

    @Override
    public byte[] readStatic(String fileName) {
        try {
            return new ClassPathResource("static/" + fileName).getContentAsByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
