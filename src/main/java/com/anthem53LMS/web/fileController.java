package com.anthem53LMS.web;


import com.anthem53LMS.config.auth.LoginUser;
import com.anthem53LMS.config.auth.dto.SessionUser;
import com.anthem53LMS.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class fileController {

    @Autowired
    private ResourceLoader resourceLoader ;
    private final FileService fileService;


    @PostMapping("/api/v1/file-save/{assignment_id}")
    public String fileSave(MultipartFile[] uploadFile , @PathVariable Long assignment_id, @LoginUser SessionUser sessionUser) {


        for(MultipartFile file :uploadFile){
            System.out.println(file.getOriginalFilename());
            try{
                fileService.fileSave(file, assignment_id, sessionUser);
            }
            catch (IOException e) {
                e.printStackTrace(); //오류 출력(방법은 여러가지)

            }
        }

        return "redirect:/";
    }

    @DeleteMapping("/api/v1/file-delete/{file_id}")
    public String fileDelete(@PathVariable Long file_id){
        System.out.println("fileDelete");
        fileService.fileDelete(file_id);

        return "redirect:/";
    }

    @GetMapping("/api/v1/file-download/{file_id}")
    public void fileDownload(@PathVariable Long file_id,  HttpServletResponse response){

        System.out.println("fileDownload call");

        try {
            fileService.fileDownload(file_id, response);
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

}
