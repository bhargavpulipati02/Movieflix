package org.project.movieflix.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface FileService {
// we get the string path anf file from user/client
    String UploadFile(String path, MultipartFile file) throws IOException;
//    surf the file
    InputStream getResourceFile(String path,String fileName) throws IOException;
}
