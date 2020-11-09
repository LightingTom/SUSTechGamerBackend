package com.example.controller;

import com.example.dao.CommentDao;
import com.example.domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
public class CommentController {
    @Autowired
    private CommentDao dao;

    @ResponseBody
    @GetMapping("/addComment")
    public String addComment(@RequestParam("uid") int uid,
                             @RequestParam("tid") int tid,
                             @RequestParam("pid") int pid,
                             @RequestParam("content") String content,
                             @RequestParam("depth") int depth){
        int cid = dao.findAll().size();
        Timestamp r_time = new Timestamp(System.currentTimeMillis());
        Comment toAdd = new Comment(cid,uid,tid,pid,r_time,content,depth,1,0,0);
        dao.saveAndFlush(toAdd);
        return "Success";
    }

    @ResponseBody
    @GetMapping("/deleteComment")
    public String deleteComment(@RequestParam("cid") int cid){
        dao.hideComment(cid);
        return "success";
    }

    @ResponseBody
    @GetMapping("/getAllbyTid")
    public String getAllByTid(@RequestParam("tid") int tid){
        List<Comment> list = dao.findAllByTiezi(tid);
        List<Comment> result = new ArrayList<>();
        for (Comment item:list) {
            if (item.getDisplay() == 1)
                result.add(item);
        }
        return result.toString();
    }

    @ResponseBody
    @GetMapping("/like")
    public String likeComment(@RequestParam("cid") int cid){
        dao.like(cid);
        return "success";
    }

    @ResponseBody
    @GetMapping("/dislike")
    public String dislike(@RequestParam("cid") int cid){
        dao.dislike(cid);
        return "success";
    }



}
