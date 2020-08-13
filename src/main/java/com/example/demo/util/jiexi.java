package com.example.demo.util;

import org.jsoup.*;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.Map;
public class jiexi {
    public static void main(String[] args) throws IOException {

    }
    public String getloginticket(String JSESSIONID) throws IOException {

        Document doc = Jsoup.connect("http://mids.gench.edu.cn/_customize/passLogin")
                .cookie("JSESSIONID", JSESSIONID)
                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36")
                .header("Referer", "http://mids.gench.edu.cn/_customize/passLogin")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language", "en,zh-CN;q=0.9,zh;q=0.8")
                .header("Connection", "close")
                .get();
        String loginticket = doc.select("#loginTicket").attr("value");
        System.out.print("\nloginticket:"+loginticket);
        return loginticket;
    }
    public String getJsession() throws IOException {

        Response doc = Jsoup.connect("http://mids.gench.edu.cn/_customize/passLogin")
                .method(Method.GET)
                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36")
                .header("Referer", "http://mids.gench.edu.cn/_customize/passLogin")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language", "en,zh-CN;q=0.9,zh;q=0.8")
                .header("Connection", "close")
                .execute();
        Map<String,String> cookies = doc.cookies();
        String JSESSIONID=cookies.get("JSESSIONID");
        System.out.print("\n"+JSESSIONID);
        return JSESSIONID;
    }
}