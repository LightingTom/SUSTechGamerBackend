package com.example.controller;

import com.example.dao.FriendDao;
import com.example.domain.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/test")
public class FriendController {
    @Autowired
    private FriendDao dao;

    @ResponseBody
    @RequestMapping("/addFriend")
    public String addFriend(@RequestParam("user1") int u1,
                            @RequestParam("user2") int u2){
        int small = (u1<u2)?u1:u2;
        int big = (u1<u2)?u2:u1;
        System.out.println(small);
        System.out.println(big);
        Friend toAdd = new Friend(small,big,new Timestamp(System.currentTimeMillis()),0,0);
        dao.saveAndFlush(toAdd);
        return "success";
    }

    @ResponseBody
    @GetMapping("/deleteFriend")
    public String deleteFriend(@RequestParam("user1") int u1,
                               @RequestParam("user2") int u2){
        int small = (u1<u2)?u1:u2;
        int big = (u1<u2)?u2:u1;
        dao.deleteByUser1AndUser2(small,big);
        return "success";
    }

    @ResponseBody
    @GetMapping("/changeIntimacy")
    public String changeIntimacy(@RequestParam("value") int val,
                                 @RequestParam("user1") int u1,
                                 @RequestParam("user2") int u2){
        int small = (u1<u2)?u1:u2;
        int big = (u1<u2)?u2:u1;
        dao.changeIntimacy(val,small,big);
        //TODO: add some boundaries for relationship
        return "success";
    }

    @ResponseBody
    @GetMapping("/getFriends")
    public String getFriends(@RequestParam("user1") int u1){
        List<Friend> friends = dao.findAllByUser1OrUser2(u1,u1);
        String result = "Friends: ";
        for (int i = 0; i < friends.size(); i++) {
            Friend tar = friends.get(i);
            int id = (tar.getUser1() == u1)?tar.getUser2():tar.getUser1();
            result += id;
        }
        return result;
    }
}
