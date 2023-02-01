package com.tedu.mysecboot.controller;

import com.tedu.mysecboot.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class UserConcroller {

    private static File userDir;
    static {
        userDir=new File("./users");
        if (!userDir.exists()){
            userDir.mkdirs();
        }
    }
    @RequestMapping("/regUser")
    public void reg(HttpServletRequest request, HttpServletResponse response){
        System.out.println("开始处理用户注册！");
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String nickname=request.getParameter("nickname");
        String ageStr=request.getParameter("age");
        System.out.println(username+","+password+","+nickname+","+ageStr);
        if(username==null||username.isEmpty()||
            password==null||password.isEmpty()||
            nickname==null||nickname.isEmpty()||
            ageStr==null||ageStr.isEmpty()||!ageStr.matches("[0-9]{1,3}")
        ){
            try {
                response.sendRedirect("reg_info_error.html");
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File file=new File(userDir,username+".obj");
        int age=Integer.parseInt(ageStr);

        User user = new User(username,password,nickname,age);
        try(
                FileOutputStream fos=new FileOutputStream(file);
                ObjectOutputStream oos=new ObjectOutputStream(fos);
        ) {
            oos.writeObject(user);
            response.sendRedirect("reg_success.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
