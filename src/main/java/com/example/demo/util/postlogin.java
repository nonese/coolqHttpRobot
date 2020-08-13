package com.example.demo.util;

import java.io.IOException;
import java.util.Map;
import org.jsoup.*;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
public class postlogin {
    public String login(String userid,String password,String loginticket){
        String iPlanetDirectoryPro=null;

        try {
            Response response;
            response = Jsoup.connect("http://mids.gench.edu.cn/_customize/passLogin")
                    .method(Method.POST)
                    .cookie("JSESSIONID", "D24A65A89F92CA9764C4906596BF897C.MIDS-Server1")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36")
                    .header("Referer", "http://mids.gench.edu.cn/_customize/passLogin")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "en,zh-CN;q=0.9,zh;q=0.8")
                    .header("Connection", "close")
                    .data("username", userid)
                    .data("password", password)
                    .data("loginTicket", loginticket)
                    .execute();
            Map<String, String> cookies = response.cookies();
            //String str ="";
            iPlanetDirectoryPro = cookies.get("iPlanetDirectoryPro");
            //for(String key : cookies.keySet()) {str += key +":"+cookies.get(key)+"\n";}
            //System.out.println(str);
            //System.out.println("\n"+iPlanetDirectoryPro);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //System.out.println("\n"+iPlanetDirectoryPro);
        return iPlanetDirectoryPro;
    }
    public String login2(String iPlanetDirectoryPro){
        String genchuser="";
        try {
            Response response;
            response = Jsoup.connect("http://ihealth.hq.gench.edu.cn/api/login/student")
                    .method(Method.POST)
                    .cookie("iPlanetDirectoryPro", iPlanetDirectoryPro)
                    .method(Method.GET)
                    .ignoreContentType(true)
                    .execute();
            Map<String,String> cookies = response.cookies();
            //String str ="";
            genchuser=cookies.get("gench_hq_user");
            //for(String key : cookies.keySet()) {str += key +":"+cookies.get(key)+"\n";}
            //System.out.println(str);
            //System.out.println(iPlanetDirectoryPro);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //System.out.println(iPlanetDirectoryPro);
        return genchuser;
    }
}
