package com.example.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

public class tiangou {
    public static String getString() throws IOException {
        String response=null;
        response = Jsoup.connect("https://v1.alapi.cn/api/dog")
                .method(Connection.Method.GET)
                .ignoreContentType(true)
                .execute()
                .body();
        JSONObject json = JSON.parseObject(response).getJSONObject("data");
        String msg = json.getString("content");
        return msg;
    }
}
