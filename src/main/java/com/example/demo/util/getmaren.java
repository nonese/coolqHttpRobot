package com.example.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

public class getmaren {
    public static String getstring() throws IOException {
        String response=null;
        response = Jsoup.connect("https://nmsl.shadiao.app/api.php?level=min&lang=zh_cn")
                .method(Connection.Method.GET)
                .ignoreContentType(true)
                .execute()
                .body();
        return response;
    }
}
