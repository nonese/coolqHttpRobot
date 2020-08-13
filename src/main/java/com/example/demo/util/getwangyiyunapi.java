package com.example.demo.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

public class getwangyiyunapi {
    public static String getstring() throws IOException {
        String response=null;
        response = Jsoup.connect("https://api.lo-li.icu/wyy/")
                .method(Connection.Method.GET)
                .ignoreContentType(true)
                .execute()
                .body();
        return response;
    }
}
