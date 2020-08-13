package com.example.demo.util;

import java.io.IOException;
import org.jsoup.*;
import org.jsoup.Connection.Method;

public class getinfo {
    public String postiplanet(String iPlanetDirectoryPro) {
        String response = null;
        try {
            String a="iPlanetDirectoryPro=";
            String b =iPlanetDirectoryPro;
            String c= a.concat(b);
            response = Jsoup.connect("http://ihealth.hq.gench.edu.cn/api/login/student")
                    .method(Method.GET)
                    .header("Cookie", c)
                    .ignoreContentType(true)
                    .execute()
                    .body();
        } catch (IOException e) {
        }
        return response;
    }
}