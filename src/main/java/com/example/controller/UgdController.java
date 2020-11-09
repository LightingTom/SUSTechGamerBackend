package com.example.controller;

import com.example.dao.ugdDao;
import com.example.domain.User_game_dlc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class UgdController {
    @Autowired
    private ugdDao dao;

    @ResponseBody
    @RequestMapping("/addContent")
    public String addContent(@RequestParam("uid") int uid,
                             @RequestParam("gid") int gid,
                             @RequestParam(value = "dlc",required = false,defaultValue = "0") int dlc){
        //TODO:Get the game path and the dlc path
        User_game_dlc toAdd =  new User_game_dlc(uid,gid,dlc,"path");
        dao.saveAndFlush(toAdd);
        return "success";
    }
}
