package com.example.demo.util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.*;
import org.jsoup.Connection.Method;
public class sign {
    public String postinfo(String responsed,String genchuser) throws IOException {
        JSONObject json = JSON.parseObject(responsed);
        JSONObject data = json.getJSONObject("data");
        String userId = data.getString("userId");
        String uuid = data.getString("uuid");
        String username = data.getString("username");
        String phone = data.getString("phone");
        String type= "%E5%AD%A6%E7%94%9F";
        String collegename="%E4%BF%A1%E6%81%AF%E6%8A%80%E6%9C%AF%E5%AD%A6%E9%99%A2";
        String classname="%E8%BD%AF%E4%BB%B6%E5%B7%A5%E7%A8%8BB17-3";
        String slocationcode="310000";
        String slocation="%E4%B8%8A%E6%B5%B7";
        String locationcode="310100";
        String location="%E4%B8%8A%E6%B5%B7%E5%B8%82";
        String fever="0";
        String symptomids="%5B%5D";
        String diagnosis="0";
        String touchquezhen="0";
        //String ruserId=URLEncoder.encode(userId,"UTF-8");
        //String ruuid = URLEncoder.encode(uuid,"UTF-8");
        String rusername = URLEncoder.encode(username,"UTF-8");
        //String rphone = URLEncoder.encode(phone,"UTF-8");
        String response=null;
        response = Jsoup.connect("http://ihealth.hq.gench.edu.cn/api/GDaily/add")
                .method(Method.POST)
                .cookie("gench_hq_user", genchuser)
                .data("type",type)
                .data("uuid",uuid)
                .data("userid",userId)
                .data("username",rusername)
                .data("collegename",collegename)
                .data("classname",classname)
                .data("location",location)
                .data("phone",phone)
                .data("slocationcode",slocationcode)
                .data("slocation",slocation)
                .data("locationcode",locationcode)
                .data("fever",fever)
                .data("symptomids",symptomids)
                .data("diagnosis",diagnosis)
                .data("touchquezhen",touchquezhen)
                .ignoreContentType(true)
                .execute()
                .body();
        return response;
    }
    public void postinfo2(String username, String uuid, String userId, String phone, String cookie) throws IOException {
        String type= "学生";
        String collegename="信息技术学院";
        String classname="软件工程B17-3";
        String slocationcode="310000";
        String slocation="上海";
        String locationcode="310100";
        String location="上海";
        String fever="0";
        String symptomids="[]";
        String diagnosis="0";
        String touchquezhen="0";
        //String rusername = URLEncoder.encode(username,"UTF-8");
        String response=null;
        response = Jsoup.connect("http://ihealth.hq.gench.edu.cn/api/GDaily/add")
                .method(Method.POST)
                .cookie("gench_hq_user", cookie)
                .data("type",type)
                .data("uuid",uuid)
                .data("userid",userId)
                .data("username",username)
                .data("collegename",collegename)
                .data("classname",classname)
                .data("location",location)
                .data("phone",phone)
                .data("slocationcode",slocationcode)
                .data("slocation",slocation)
                .data("locationcode",locationcode)
                .data("fever",fever)
                .data("symptomids",symptomids)
                .data("diagnosis",diagnosis)
                .data("touchquezhen",touchquezhen)
                .ignoreContentType(true)
                .execute()
                .body();
        JSONObject json = JSON.parseObject(response);
        String msg = json.getString("msg");
        System.out.println("\n"+userId+msg);
    }
}
