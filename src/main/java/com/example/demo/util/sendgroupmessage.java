package com.example.demo.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

public class sendgroupmessage {
    public static void sendgroup(String id, String mes) throws IOException {
        Connection.Response response;
        response = Jsoup.connect("http://192.168.2.205:5700/send_group_msg")
                .method(Connection.Method.POST)
                .ignoreContentType(true)
                .data("group_id",id)
                .data("message",mes)
                .execute();
    }
}
