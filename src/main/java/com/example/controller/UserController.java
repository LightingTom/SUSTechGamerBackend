package com.example.controller;

import com.example.Util;
import com.example.dao.Dao;
import com.example.dao.UserDao;
import com.example.domain.Test;
import com.example.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/test")
public class UserController {
    @Autowired
    private Dao dao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private Util util;

    @ResponseBody
    @RequestMapping("/findAll")
    public String showAll(){
        StringBuilder sb = new StringBuilder();
        List<Test> result = dao.findAll();
        for(Test item:result){
            sb.append(item.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    @ResponseBody
    @RequestMapping("/findByName")
    public String findByName(String name){
        return dao.findByName(name).toString();
    }

    @ResponseBody
    @GetMapping("/addItem")
    public String addItem(@RequestParam("id") Integer id, @RequestParam("name") String name){
        Test item = new Test(id,name);
        dao.saveAndFlush(item);
        return "Success added: " + item.toString();
    }

    @Transactional
    @ResponseBody
    @RequestMapping("/updatePassword")
    public String changePassword(@RequestParam("id") Integer id, @RequestParam("password") String pass){
        userDao.updatePasswordByID(id,pass);
        return "Success";
    }

    @ResponseBody
    @RequestMapping("/login")
    public String login(@RequestParam("name") String name, @RequestParam("password") String password){
        List<User> result = userDao.findUsersByNameAndPassword(name,password);
        if (result.size() == 1)
            return "Hello, " + name;
        else
            return "Username or password error";
    }

    @ResponseBody
    @RequestMapping("/signup")
    public String signup(@RequestParam("name") String name,
                         @RequestParam("password") String password,
                         @RequestParam("role") int rol, @RequestParam("sex") char sex,
                         @RequestParam("intro") String intro,
                         @RequestParam("avatar") MultipartFile files[],
                         @RequestParam("email") String email) throws IOException {
        List<User> all_user = userDao.findAll();
        int num = all_user.size() + 1;
        String pathname = "/Users/xuchengqi/Desktop/User/" + num + "";
        File file = new File(pathname);
        file.mkdirs();
        BufferedWriter bw = new BufferedWriter(new FileWriter(pathname+"/profile.txt"));
        String f_name = "avatar."+files[0].getOriginalFilename().split("\\.")[1];
        boolean b = util.httpUpload(files,pathname,f_name);
        if (b) System.out.println("Success");
        bw.write(intro);
        bw.write("\n");
        bw.write(email);
        bw.write("\n");
        bw.write(pathname+"/"+f_name);
        bw.close();
        User newUser = new User(num,name,password,pathname+"/profile.txt",0,rol,sex);
        userDao.saveAndFlush(newUser);
        return "success!";
    }

    @ResponseBody
    @RequestMapping("/changeAvatar")
    public String changeAvatar(@RequestParam("id") int id,
                               @RequestParam("avatar") MultipartFile files[]){
        String pathname = "/Users/xuchengqi/Desktop/User/" + id +"";
        String f_name = "avatar."+files[0].getOriginalFilename().split("\\.")[1];
        util.httpUpload(files,pathname,f_name);
        return "Success";
    }

    @ResponseBody
    @RequestMapping("/changeEmail")
    public String changeEmail(@RequestParam("id") int id,
                              @RequestParam("email") String email)throws IOException{
        String filename = "/Users/xuchengqi/Desktop/User/" + id + "/" + "profile.txt";
        System.out.println(filename);
        File file = new File(filename);
        String line = util.getDes(filename);
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(line);
        bw.write("\n");
        bw.write(email);
        bw.close();
        return "success";
    }

    @ResponseBody
    @RequestMapping("/changeDescription")
    public String changeDes(@RequestParam("id") int id,
                            @RequestParam("des") String des) throws IOException{
        String filename = "/Users/xuchengqi/Desktop/User/" + id + "/" + "profile.txt";
        File file = new File(filename);
        String line = util.getEmail(filename);
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(des);
        bw.write("\n");
        bw.write(line);
        bw.close();
        return "success";
    }
}
