package com.andy.girl.controller;

import com.andy.girl.domain.User;
import com.andy.girl.mapper.Department;
import com.andy.girl.mapper.DepartmentDao;
import com.andy.girl.mapper.music.MusicUserMapper;
import com.andy.girl.mapper.test.TestUserMapper;
import com.andy.girl.tool.MailServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DepartmentController {
//    @Autowired
//    DepartmentDao departmentDao;


    @Autowired
    MusicUserMapper musicUserMapper;

    @Autowired
    TestUserMapper testUserMapper;


    @Autowired
    private MailServiceImpl MailService;
//    @RequestMapping("/getD")
//    public List<Department> getAll(){
//        return departmentDao.findall();
//    }

    @RequestMapping("/getAB")
    public List<List<User>> getUser(){
        List<List<User>> lists=new ArrayList<>();
        lists.add(musicUserMapper.finAll());
        lists.add(testUserMapper.findAll());
        return lists;
    }


    @RequestMapping("/testMainl")
    public void TestMail(){
        String content="<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封Html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";
        MailService.sendSimpleMail("2660889125@qq.com","test Mail",content);
    }


    @RequestMapping("/testMailF")
    public void TestMailF(){
        String filePath="girl.log";
        MailService.sendAttachmentsMail("2660889125@qq.com", "主题：带附件的邮件", "有附件，请查收！", filePath);
    }

}
