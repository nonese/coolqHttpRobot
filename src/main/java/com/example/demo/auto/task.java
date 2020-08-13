package com.example.demo.auto;

import com.example.demo.entity.Safereport;
import com.example.demo.entity.Autosiginin;
import com.example.demo.service.IAutosigininService;
import com.example.demo.service.ISafereportService;
import com.example.demo.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Thread.sleep;

@Component
public class task {
    private final Logger logger = LoggerFactory.getLogger(task.class);
    @Autowired
    IAutosigininService AutosigininService;
    @Autowired
    ISafereportService safereportService;
    @Scheduled(cron="0 0 0 * * ? ")
    //@Scheduled(fixedRate = 30000)
    public void scheduledTask() throws IOException, InterruptedException {
        logger.info("Scheduled 网抑云 is starting... ...");
        //String id ="923308744";
        String id ="693331213";
        sendgroupmessage.sendgroup(id,"到点了,打开网抑云.jpg");
        int i;
        for (i=0; i<=3; i++) {
            String getyuju = getwangyiyunapi.getstring();
            sleep(3000);
            sendgroupmessage.sendgroup(id, getyuju);
        }
        logger.info("Scheduled 网抑云 is ending... ...");
    }
    @Scheduled(cron="0 0 12 * * ? ")
    public void scheduledTask2() throws IOException {
        logger.info("Scheduled 天狗日记 is starting... ...");
        String getyuju = tiangou.getString();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String date2=formatter.format(date);
        String id ="693331213";
        sendgroupmessage.sendgroup(id,"今天是"+date2);
        sendgroupmessage.sendgroup(id,getyuju);
        logger.info("Scheduled 天狗日记 is ending... ...");
    }
    //@Scheduled(fixedRate = 30000)
    //@Scheduled(cron="0 0 0,6,12,18 * * ?")
    public void scheduledTask3() throws IOException {
        logger.info("Scheduled 热榜 is starting... ...");
        String zhihu= news.getzhihuString();
        String xl =news.getxlString();
        String weibo =news.getweiboString();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String date2=formatter.format(date);
        //String id ="923308744";
        String id ="693331213";
        sendgroupmessage.sendgroup(id,"现在时间是"+date2);
        sendgroupmessage.sendgroup(id,zhihu);
        sendgroupmessage.sendgroup(id,xl);
        sendgroupmessage.sendgroup(id,weibo);
        logger.info("Scheduled 热榜 is ending... ...");
    }
    //@Scheduled(fixedRate = 30000)
    @Scheduled(cron = "0 40 20 * * ?")
    public void scheduledTask4() throws IOException {
        logger.info("Scheduled 自动签到 is running... ...");
        List<Autosiginin> users = new ArrayList<>();
        users= AutosigininService.list();
        for(Autosiginin user : users){
            String username=user.getName();
            String uuid = user.getUid();
            String userid=user.getGenchid();
            String phone =user.getPhone();
            String cookie =user.getCookie();
            sign sg = new sign();
            sg.postinfo2(username,uuid,userid,phone,cookie);
            System.out.println("自动签到"+"任务执行时间：" + LocalDateTime.now());
        }
        logger.info("Scheduled 自动签到 is endning... ...");
    }
    //@Scheduled(cron = "0 0 8 * * ? ")
    //@Scheduled(fixedRate = 30000)
    public void scheduledTask5() throws IOException {
        logger.info("Scheduled 360安全周报 is running... ...");
        List<Safereport> tests = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String date2=formatter.format(date);
        tests= safereportService.list();
        for(Safereport test : tests){
            String content=test.getContent();
            String id ="923308744";
            //String id ="693331213";
            sendgroupmessage.sendgroup(id,"现在时间是"+date2);
            sendgroupmessage.sendgroup(id,"漏洞每日推送:\n"+content);
            System.out.println("360安全周报"+"任务执行时间：" + LocalDateTime.now());
        }
        logger.info("Scheduled 360安全周报 is endning... ...");
    }
}
