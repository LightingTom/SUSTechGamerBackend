package com.example;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Component
public class Util {
    public boolean httpUpload(MultipartFile files[], String uplpadPath, String fileName){
        for (int i = 0;i < files.length;i++){
            File dest = new File(uplpadPath + "/"+fileName);
            if (!dest.getParentFile().exists()){
                dest.getParentFile().mkdirs();
            }
            try {
                files[i].transferTo(dest);
            }catch (Exception e){
                return false;
            }
        }
        return true;
    }

    public String getDes(String pathname) {
        BufferedReader br = null;
        String result = "";
        try {
            br = new BufferedReader(new FileReader(new File(pathname)));
            System.out.println(br.read());
            result = br.readLine();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getEmail(String pathname) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(new File(pathname)));
        String result = br.readLine();
        result = br.readLine();
        return result;
    }
}
