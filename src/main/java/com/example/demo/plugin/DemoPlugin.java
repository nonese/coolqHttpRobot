package com.example.demo.plugin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Autosiginin;
import com.example.demo.entity.Btc;
import com.example.demo.entity.Shangzhengzhishu;
import com.example.demo.service.IAutosigininService;
import com.example.demo.service.IBtcService;
import com.example.demo.service.IShangzhengzhishuService;
import com.example.demo.util.*;
import lombok.SneakyThrows;
import net.lz1998.cq.event.message.CQGroupMessageEvent;
import net.lz1998.cq.event.message.CQPrivateMessageEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
import net.lz1998.cq.utils.CQCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.apache.commons.lang3.RandomUtils;

import java.util.*;


/**
 * 示例插件
 * 插件必须继承CQPlugin，上面要 @Component
 * <p>
 * 添加事件：光标移动到类中，按 Ctrl+O 添加事件(讨论组消息、加群请求、加好友请求等)
 * 查看API参数类型：光标移动到方法括号中按Ctrl+P
 * 查看API说明：光标移动到方法括号中按Ctrl+Q
 */
@Component
public class DemoPlugin extends CQPlugin {
    @Autowired
    IAutosigininService AutosigininService;
    @Autowired
    IBtcService BtcService;
    @Autowired
    IShangzhengzhishuService shangzhengzhishuService;
    @SneakyThrows
    public int onPrivateMessage(CoolQ cq, CQPrivateMessageEvent event) {
        // 获取 发送者QQ 和 消息内容
        //IAutosigininService AutosigininService;
        long userId = event.getUserId();
        String qqid2 =Long.toString(userId);
        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("qq",qqid2);
        Collection check = AutosigininService.listByMap(columnMap);
        //System.out.println(qqid2+"是否已开启自动签到："+check.size());
        String msg = event.getMessage();
        int autosignpd = msg.indexOf("开启自动签到");
        int liuyanpd = msg.indexOf("微信留言");
        if (autosignpd != -1 &check.size()!=1) {
            // 调用API发送hello
            System.out.println(msg);
            String[] arrays = msg.split(" ");
            String genchid = arrays[1];
            String genchpassword = arrays[2];
            System.out.println(genchid);
            System.out.println(genchpassword);
            String iPlanetDirectoryPro=null;
            int i=0;
            while (iPlanetDirectoryPro == null & i<=10) {
                jiexi jx = new jiexi();
                String Jsession = jx.getJsession();
                String loginticket = jx.getloginticket(Jsession);
                postlogin plogin = new postlogin();
                iPlanetDirectoryPro = plogin.login(genchid, genchpassword, loginticket);
                System.out.println("\n"+iPlanetDirectoryPro);
                i++;
            }
            postlogin plogin2 = new postlogin();
            String genchuser = plogin2.login2(iPlanetDirectoryPro);
            getinfo gf = new getinfo();
            String response = gf.postiplanet(iPlanetDirectoryPro);
            System.out.println("\n" + response);
            sign sg = new sign();
            String finalresponse = sg.postinfo(response, genchuser);
            JSONObject json = JSON.parseObject(finalresponse);
            String msg2 = json.getString("msg");
            System.out.print("\n" + msg2);
            if (msg2.equals("添加成功")) {
                String qqid = Long.toString(userId);
                JSONObject json2 = JSON.parseObject(response);
                JSONObject data = json2.getJSONObject("data");
                String phone = data.getString("phone");
                String uuid = data.getString("uuid");
                String username = data.getString("username");
                Autosiginin autosiginin = new Autosiginin();
                autosiginin.setQq(qqid);
                System.out.print(username);
                autosiginin.setGenchid(genchid);
                autosiginin.setGenchpassword(genchpassword);
                autosiginin.setOpen("1");
                autosiginin.setPhone(phone);
                autosiginin.setCookie(genchuser);
                autosiginin.setUid(uuid);
                autosiginin.setName(username);
                AutosigininService.save(autosiginin);
                cq.sendPrivateMsg(userId, "开启自动签到成功", false);
                return MESSAGE_BLOCK;
            }
            cq.sendPrivateMsg(userId, "开启自动签到失败", false);
            // 不执行下一个插件
            return MESSAGE_BLOCK;
        }
        else if (msg.equals("如何自动签到"))
        {
            String str1="**如果15秒内无回复请检查您的账户密码是否正确并重试,因为有几率不成功，所以无回复情况下请多试几回.**";
            String str2="首先将您准备好建桥官网的密码，后打开https://nonese.github.io/%E5%AF%86%E7%A0%81%E5%8A%A0%E5%AF%86%E8%AE%A1%E7%AE%97%E5%99%A8 以此进行密码加密";
            String str3="暂时开启后无法关闭自动签到,需要关闭请联系本人";
            cq.sendPrivateMsg(userId, str2+"\n"+"再以以下格式回复：开启自动签到+您的学号+加密后的密码"+"\n"+"例如：开启自动签到 1720428 xxxxxxxxxxxxxxxxxxx（每个参数以空格隔开！）"+"\n"+str1+"\n"+str3, false);
            //cq.sendPrivateMsg(userId, "再以以下格式回复：开启自动签到+您的学号+加密后的密码", false);
            //cq.sendPrivateMsg(userId, "例如：开启自动签到 1720428 xxxxxxxxxxxxxxxxxxx", false);
            return MESSAGE_BLOCK;
        }
        else if (autosignpd != -1 &check.size()==1){
            cq.sendPrivateMsg(userId, "开启自动签到失败,请确认是否已开启", false);
            // 不执行下一个插件
            return MESSAGE_BLOCK;
        }
        else if (msg.equals("功能查询"))
        {
            String str1="私聊功能列表：1.查询如何签到 回复“如何自动签到”";
            String str2="群聊功能列表：1.查询btc现在价格 回复“btc现价查询”";
            String str3="2.windows与office辅助激活相关 回复“系统激活”";
            String str4="3.常用以及有用软件资源（有其他想要的联系本人） 回复“常用软件资源”";
            String str5="4.如若给本人留言，请回复“给本人留言”";
            cq.sendPrivateMsg(userId, str1+"\n"+str3+"\n"+str4+"\n"+str5+"\n"+str2, false);
            //cq.sendPrivateMsg(userId, "再以以下格式回复：开启自动签到+您的学号+加密后的密码", false);
            //cq.sendPrivateMsg(userId, "例如：开启自动签到 1720428 xxxxxxxxxxxxxxxxxxx", false);
            return MESSAGE_BLOCK;
        }
        else if (msg.equals("系统激活"))
        {
            String str1="按win键，输入cmd，右键选择管理员打开”";
            String str2="输入slmgr /skms kerara.cn:24794";
            String str3="上一条按回车执行后再输入slmgr /ato";
            String str4="稍等片刻就会弹出激活成功";
            cq.sendPrivateMsg(userId, str1+"\n"+str2+"\n"+str3+"\n"+str4, false);
            //cq.sendPrivateMsg(userId, "再以以下格式回复：开启自动签到+您的学号+加密后的密码", false);
            //cq.sendPrivateMsg(userId, "例如：开启自动签到 1720428 xxxxxxxxxxxxxxxxxxx", false);
            return MESSAGE_BLOCK;
        }
        else if (msg.equals("office激活"))
        {
            String str1="整体参考http://www.xitongcheng.com/jiaocheng/dnrj_article_44859.html”";
            String str2="其中cscript ospp.vbs /sethst:kms.03k.org 换成 cscript ospp.vbs /sethst:kerara.cn:24794";
            String str3="其余的按照指示进行即可";
            String str4="稍等片刻就会弹出激活成功";
            cq.sendPrivateMsg(userId, str1+"\n"+str2+"\n"+str3+"\n"+str4, false);
            //cq.sendPrivateMsg(userId, "再以以下格式回复：开启自动签到+您的学号+加密后的密码", false);
            //cq.sendPrivateMsg(userId, "例如：开启自动签到 1720428 xxxxxxxxxxxxxxxxxxx", false);
            return MESSAGE_BLOCK;
        }
        else if (msg.equals("给本人留言"))
        {
            String str1="回复“微信留言+你的内容”";
            String str2="例如：微信留言 XXXXXXXXXX”";
            cq.sendPrivateMsg(userId, str1+"\n"+str2, false);
            //cq.sendPrivateMsg(userId, "再以以下格式回复：开启自动签到+您的学号+加密后的密码", false);
            //cq.sendPrivateMsg(userId, "例如：开启自动签到 1720428 xxxxxxxxxxxxxxxxxxx", false);
            return MESSAGE_BLOCK;
        }
        /*
         *
         * 微信留言
         *
         * */
        else if (liuyanpd != -1){
            String[] arrays = msg.split(" ");
            String lyid ="\n留言QQ："+qqid2;
            String content = arrays[1]+lyid;
            String str = wechatsend.postmsg("微信留言",content);
            cq.sendPrivateMsg(userId, "留言成功", false);
            // 不执行下一个插件
            return MESSAGE_BLOCK;
        }
        else if (msg.equals("常用软件资源"))
        {
            String str1="windows系统 https://msdn.itellyou.cn/";
            String str2="office 系列 https://msdn.itellyou.cn/";
            String str3="Adobe 2020 全家桶 链接: https://pan.baidu.com/s/1_HM7DsgcmzYY60yBktXvkg 提取码: yjqn";
            String str4="联想运维工具箱 链接: https://pan.baidu.com/s/1D9bI8xpT1InPrYSX4LIqRw 提取码: yjqn";
            String str5="IDM_V6.36带补丁 链接: https://pan.baidu.com/s/14jKZjOBqJwnMRD9v72vu2w 提取码: yjqn";
            String str6="免费商用字体 链接: https://pan.baidu.com/s/11TPkqNmJaMoOHwsVUCEODQ 提取码: yjqn";
            String str7="毕业论文写作攻略大全 链接: https://pan.baidu.com/s/1J1WOZLYtL4wPmNtr9sOLVg 提取码: yjqn";
            String str8="C4D+AE 28万年薪计划（？）链接: https://pan.baidu.com/s/1HOJ-DVwqCZLkwkEfGmcL9A 提取码: yjqn";
            String str9="PS全套插件 链接: https://pan.baidu.com/s/1d2m2sZUH6p6u652o7gwm2w 提取码: yjqn";
            String str10="图吧工具箱2020.05 链接: https://pan.baidu.com/s/1-Fvdd7YZbNxhjXCUtdL6Vg 提取码: yjqn";
            String str11="毕设资料 链接: https://pan.baidu.com/s/1z8ryhFsPgC-O_4UokbvC5w 提取码: yjqn";

            cq.sendPrivateMsg(userId, str1+"\n"+str2+"\n"+str3+"\n"+str4+"\n"+str5+"\n"+str6+"\n"+str7+"\n"+str8+"\n"+str9+"\n"+str10+"\n"+str11, false);
            //cq.sendPrivateMsg(userId, "再以以下格式回复：开启自动签到+您的学号+加密后的密码", false);
            //cq.sendPrivateMsg(userId, "例如：开启自动签到 1720428 xxxxxxxxxxxxxxxxxxx", false);
            return MESSAGE_BLOCK;
        }

        // 继续执行下一个插件
        cq.sendPrivateMsg(userId, "你好，这里是Nonese亲自写的BOT，你看到这条消息八成是因为输入的指令运行出错或无法识别指令。有任何不清楚请回复“功能查询”，希望能帮到你。", false);
        return MESSAGE_BLOCK;
    }
    /**
     * 收到群消息时会调用这个方法
     *
     * @param cq    机器人对象，用于调用API，例如发送群消息 sendGroupMsg
     * @param event 事件对象，用于获取消息内容、群号、发送者QQ等
     * @return 是否继续调用下一个插件，IGNORE表示继续，BLOCK表示不继续
     */
    @SneakyThrows
    @Override
    public int onGroupMessage(CoolQ cq, CQGroupMessageEvent event) {
        // 获取 消息内容 群号 发送者QQ
        String msg = event.getMessage();
        long groupId = event.getGroupId();
        long userId = event.getUserId();
        System.out.println(groupId+"群消息:"+msg+"\n");
        int atednotice = msg.indexOf("[CQ:at,qq=1918220478]");
        int zxwnotice =msg.indexOf("zxw");
        int maren = RandomUtils.nextInt(1,101);
        System.out.println("本次群随机骂人数为："+maren);
        if (maren <= 20 & groupId == 693331213){
            int suijishu = RandomUtils.nextInt(0,9);
            long zxwid = 853969248;
            String marenyuju2 = getmaren.getstring();
            String result = CQCode.at(zxwid)+marenyuju2;
            cq.sendGroupMsg(groupId, result, false);
        }
        if (groupId == 557103756& userId == 1794528220){
            String marenyuju2 = getmaren.getstring();
            String result = CQCode.at(userId)+marenyuju2;
            cq.sendGroupMsg(groupId, result, false);
        }
        if (maren <= 35 &maren >= 21 & groupId == 557103756){
            //int suijishu = RandomUtils.nextInt(0,9);
            long cslid = 1317761732;
            String marenyuju2 = getmaren.getstring();
            String result = CQCode.at(cslid)+marenyuju2;
            cq.sendGroupMsg(groupId, result, false);
        }
        if (zxwnotice != -1 & userId != 853969248 &userId != 2027855474 & groupId == 693331213){
            //long zxwid = 853969248;
            //String result = CQCode.at(zxwid)+msg;
            cq.sendGroupMsg(groupId, msg, false);
        }
        if (atednotice != -1) {
            String[] arrays = msg.split(" ");
            String content = arrays[1];
            System.out.println("被at的群消息内容："+content+"\n");
            if (content.equals("btc现价查询")){
                List<Btc> btcs = new ArrayList<>();
                btcs = BtcService.list();
                String USDprice = null;
                String CNYprice= null;
                String open24h= null;
                String high24h= null;
                String low24h= null;
                String lastprice= null;
                for(Btc btc : btcs){
                    USDprice=btc.getUsd();
                    CNYprice=btc.getCny();
                    open24h=btc.getOpen24h();
                    high24h=btc.getHigh24h();
                    low24h=btc.getLow24h();
                    lastprice=btc.getLastprice();
                }
                String str="USD:"+USDprice+" "+"CNY:"+CNYprice+" "+"OPEN 24H:"+open24h+" "+"HIGH 24H:"+high24h+" "+"LOW 24H:"+low24h+" "+"Last Price:"+lastprice+" "+"数据源爬自：https://cointelegraph.com/bitcoin-price-index";
                String result = CQCode.at(userId)+str;
                cq.sendGroupMsg(groupId, result, false);
                return MESSAGE_BLOCK;
            }
            else if (content.equals("上证券指数查询")){
                List<Shangzhengzhishu> szzs = new ArrayList<>();
                szzs = shangzhengzhishuService.list();
                String str = null;
                String str2 = null;
                for(Shangzhengzhishu szs : szzs){
                    str = szs.getZhishu();
                    str2 = szs.getXiangqing();
                }
                String str3=str+"\n"+str2;
                String result = CQCode.at(userId)+str3;
                cq.sendGroupMsg(groupId, result, false);
                return MESSAGE_BLOCK;
            }
            else if (content.equals("功能查询")){
                String str1="私聊功能列表：1.查询如何每日自动建桥i健康自动签到 回复“如何自动签到”";
                String str3="群聊功能列表：1.查询btc现在价格 回复“btc现价查询”";
                String str2="2.windows与office辅助激活相关 回复“系统激活”";
                String str4="3.常用以及有用软件资源（有其他想要的联系本人） 回复“常用软件资源”";
                String str5="4.如若给本人留言，请回复“给本人留言”";
                String result = CQCode.at(userId) + str1+"\n"+str2+"\n"+str4+"\n"+str5+"\n"+str3;
                cq.sendGroupMsg(groupId, result, false);
            }
            // 不执行下一个插件
            return MESSAGE_BLOCK;
        }
        // 继续执行下一个插件
        return MESSAGE_BLOCK;
    }

}
