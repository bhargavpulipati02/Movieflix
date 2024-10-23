package org.project.movieflix.Controller;

import jakarta.servlet.http.HttpServletResponse;
import org.project.movieflix.Service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/file/")
public class FileController {

    private final FileService fileService;
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }
//    in application.yml
    @Value("${project.poster}")
    private String path;

    @PostMapping("/upload")
    public ResponseEntity<String>  uploadFileHandler(@RequestPart MultipartFile file) throws IOException {
        return new ResponseEntity<>("File Uploaded: "+ fileService.UploadFile(path, file), HttpStatus.OK);
    }
//give file name we have to get back the image/file
    @GetMapping("/{FileName}")
    public void serveFileHandler(@PathVariable String FileName, HttpServletResponse response) throws IOException {
        InputStream resourcefile =  fileService.getResourceFile(path,FileName);
//        setting response for client as PNG image
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
//        sends image back to the client
//         StreamUtils.copy() to write the contents of the InputStream (resourcefile) directly to the HTTP response output stream
        StreamUtils.copy(resourcefile,response.getOutputStream());
    }


}
