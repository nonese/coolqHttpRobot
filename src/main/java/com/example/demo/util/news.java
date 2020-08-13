package com.example.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

public class news {
    public static String getzhihuString() throws IOException {
        String response=null;
        response = Jsoup.connect("https://v1.alapi.cn/api/tophub/get?type=zhihu")
                .timeout(120000)
                .method(Connection.Method.GET)
                .ignoreContentType(true)
                .execute()
                .body();
        JSONArray json = JSON.parseObject(response).getJSONObject("data").getJSONArray("list");

        int i;
        String msg = "知乎热榜前五：\n";
        for (i=0;i<=4;i++){
            JSONObject data=json.getJSONObject(i);
            String title = data.getString("title");
            String link = data.getString("link");
            msg=msg+title+"\n"+link+"\n";
        }
        //String msg = json.getString("content");
        return msg;
    }
    public static String getxlString() throws IOException {
        String response=null;
        response = Jsoup.connect("https://v1.alapi.cn/api/tophub/get?type=xl")
                .method(Connection.Method.GET)
                .timeout(120000)
                .ignoreContentType(true)
                .execute()
                .body();
        JSONArray json = JSON.parseObject(response).getJSONObject("data").getJSONArray("list");

        int i;
        String msg = "新浪网热词排行榜前五：\n";
        for (i=0;i<=4;i++){
            JSONObject data=json.getJSONObject(i);
            String title = data.getString("title");
            String link = data.getString("link");
            msg=msg+title+"\n"+link+"\n";
        }
        //String msg = json.getString("content");
        return msg;
    }
    public static String getweiboString() throws IOException {
        String response=null;
        response = Jsoup.connect("https://v1.alapi.cn/api/tophub/get?type=weibo")
                .method(Connection.Method.GET)
                .timeout(120000)
                .ignoreContentType(true)
                .execute()
                .body();
        JSONArray json = JSON.parseObject(response).getJSONObject("data").getJSONArray("list");

        int i;
        String msg = "微博热搜排行榜前五：\n";
        for (i=0;i<=4;i++){
            JSONObject data=json.getJSONObject(i);
            String title = data.getString("title");
            String link = data.getString("link");
            msg=msg+title+"\n"+link+"\n";
        }
        //String msg = json.getString("content");
        return msg;
    }
}
