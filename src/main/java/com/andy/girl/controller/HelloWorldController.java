package com.andy.girl.controller;

import com.andy.girl.domain.User;
import com.andy.girl.domain.UserRepository;
import com.andy.girl.tool.CheckoutUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class HelloWorldController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;



    @RequestMapping("/hello")
    public Page<User> hello(@PageableDefault(size=3,page=0,sort = "id",direction = Sort.Direction.DESC) Pageable pageable){
//        int page=1,size=3;
//        Sort sort = new Sort(Sort.Direction.DESC, "id");
//        Pageable pageable = new PageRequest(page, size, sort);
        Page<User> page=userRepository.findAll(pageable);
        for(User user:page.getContent())
            System.out.println(" "+user);
        return page;
    }
    @RequestMapping("/top")
    public User top(@PageableDefault(size=3,page=0,sort = "id",direction = Sort.Direction.DESC) Pageable pageable){
//        int page=1,size=3;
//        Sort sort = new Sort(Sort.Direction.DESC, "id");
//        Pageable pageable = new PageRequest(page, size, sort);

        return userRepository.findTopByOrderByIdDesc();
    }

    @RequestMapping("/say")
    public String test() throws Exception {
        stringRedisTemplate.opsForValue().set("aaa", "111");
        return stringRedisTemplate.opsForValue().get("aaa");
    }

    @RequestMapping("/redis")
    public void testObj() throws Exception {
        User user=new User("aa@126.com", "aa", "aa123456", "aa","123");
        ValueOperations<String, User> operations=redisTemplate.opsForValue();
        operations.set("com.neox", user);
        operations.set("com.neo.f", user,1, TimeUnit.SECONDS);
        //redisTemplate.delete("com.neo.f");
        boolean exists=redisTemplate.hasKey("com.neo.f");
        if(exists){
            System.out.println("exists is true");
        }else{
            System.out.println("exists is false");
        }
        // Assert.assertEquals("aa", operations.get("com.neo.f").getUserName());
    }


    @RequestMapping("/uid")
    String uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }

    @RequestMapping("/testAJAX")
    public String getTest(){
        return "Hello world";
    }

//提交
    @RequestMapping("/submit")
    public boolean submit(@RequestParam("myid") String city){
        System.out.println(" "+city+" ");

        WebAsyncTask<String> task=new WebAsyncTask<String>(10*1000,()->{
            return "sdfs";
        });

        if (city!=null)
            return true;
        else
            return false;
    }


    @RequestMapping("/wx")
    public void handle(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        PrintWriter print;
        if (isGet) {

//            System.out.println(getRequestData(request));
            // 微信加密签名
            String signature = request.getParameter("signature");
            // 时间戳
            String timestamp = request.getParameter("timestamp");
            // 随机数
            String nonce = request.getParameter("nonce");
            // 随机字符串
            String echostr = request.getParameter("echostr");
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
            System.out.println(signature+"  "+timestamp+" "+nonce+" "+echostr);
            if (signature != null && CheckoutUtil.checkSignature(signature, timestamp, nonce)) {
                try {
                    print = response.getWriter();
                    print.write(echostr);
                    print.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println(" "+e.toString());
                }
            }else{
                System.out.println(" "+"Fail");
            }
        }else{
            System.out.println(" "+"NO  METHOD");
            System.out.println(getRequestData(request));

        }
    }

    /*读取request数据*/
    public static String getRequestData(HttpServletRequest request) throws IOException{
        BufferedReader reader = request.getReader();
        char[] buf = new char[512];
        int len = 0;
        StringBuffer contentBuffer = new StringBuffer();
        while ((len = reader.read(buf)) != -1) {
            contentBuffer.append(buf, 0, len);
        }

        String content = contentBuffer.toString();

        if(content == null){
            content = "";
        }
        return content;
    }

}
