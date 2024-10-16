package org.project.movieflix.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImplem implements FileService {


    @Override
//    path in server to store
    public String UploadFile(String path, MultipartFile file) throws IOException {
//        get name of the file
//        client system name
        String fileName = file.getOriginalFilename();
//        separator is like /
        String filePath =path + File.separator+fileName;
//        Create a File Object
        File f = new File(path);
//        if path not there create one
        if (!f.exists()) {
            f.mkdirs();
        }

//        copy the file or upload file to path
//        if same image path exists replace it
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        return fileName;

    }

    @Override
//    get the InputStream from specified path and file name
    public InputStream getResourceFile(String path, String fileName) throws IOException {
        String filePath =path + File.separator+fileName;
        return new FileInputStream(filePath);
    }
}
