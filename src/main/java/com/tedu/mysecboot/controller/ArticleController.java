package com.tedu.mysecboot.controller;

import com.tedu.mysecboot.entity.Article;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class ArticleController {
    private static File articleDir;
    static {
        articleDir=new File(".articles");
        if (!articleDir.exists()){
            articleDir.mkdir();
        }
    }
    @RequestMapping("/writeArticle.html")
    public void writ(HttpServletRequest request, HttpServletResponse response){
        System.out.println("开始处理发表文章");

        String title=request.getParameter("title");
        String author=request.getParameter("author");
        String content=request.getParameter("content");
        System.out.println(title+","+author+","+content);
        if (title==null||title.isEmpty()||
                author==null||author.isEmpty()||
                content==null||content.isEmpty()
        ){
            try {
                response.sendRedirect("article_info_error.html");
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Article article=new Article(title,author,content);
        File file=new File(articleDir,title+".obj");
        try(
                FileOutputStream fos=new FileOutputStream(file);
                ObjectOutputStream oos=new ObjectOutputStream(fos);
                ) {
            oos.writeObject(article);
            response.sendRedirect("/article_success.html;");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
