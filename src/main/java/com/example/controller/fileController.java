package com.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("file")
@Slf4j
public class fileController {
    @Value("${file.upload.url}")
    private String uplpadPath;

    @Value("${file.download.url}")
    private String downloadPath;
    @RequestMapping("/upload")
    public String httpUpload(@RequestParam("files")MultipartFile files[]){
        JSONObject j = new JSONObject();
        for (int i = 0;i < files.length;i++){
            String fileName = files[i].getOriginalFilename();
            File dest = new File(uplpadPath + "/"+fileName);
            if (!dest.getParentFile().exists()){
                dest.getParentFile().mkdirs();
            }
            try {
                files[i].transferTo(dest);
            }catch (Exception e){
                j.put("success",2);
                j.put("result","try again");
                return j.toString();
            }
        }
        j.put("success",1);
        j.put("result","upload success");
        return j.toString();
    }

    @RequestMapping("/download")
    public String fileDownload(HttpServletResponse response, @RequestParam("fileName") String fileName){
        File file = new File(downloadPath + "/"+fileName);
        if (!file.exists())
            return "No such file";
        response.reset();
        response.setContentType("application/octst-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            return "下载失败";
        }
        return "下载成功";
    }
}
