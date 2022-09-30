package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.util.ChpRes;
import com.tencent.wxcloudrun.util.RobotRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author Created by zhangao/zhangao@yth.cn.<br/>
 * @version Version: 0.0.1
 * @date DateTime: 2022/09/30 10:45:00<br/>
 */
@Service
@Slf4j
public class RequestReplyMsgService {
    @Resource
    private RestTemplate restTemplate;
    
    private String CHP_URL = "https://api.shadiao.pro/chp";
    
//    private String ROBOT_URL = "https://api.ownthink.com/bot?spoken=%s";

    private String ROBOT_URL = "http://api.qingyunke.com/api.php?key=free&appid=0&msg=%s";

    public String getChp(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("user-agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36");

        HttpEntity<ChpRes> res =  restTemplate
                .exchange(CHP_URL, HttpMethod.GET, new HttpEntity<>(null, headers),
                        ChpRes.class);
        if(((ResponseEntity<ChpRes>) res).getStatusCode().is2xxSuccessful() && 
                res.getBody() != null &&
                res.getBody().getData() != null &&
                res.getBody().getData().getText() != null){
            log.info(res.getBody().getData().getText());
            return res.getBody().getData().getText();
        }
        return null;
    }
    public String getRobotChat(String msg){
        HttpHeaders headers = new HttpHeaders();
        headers.set("user-agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36");
        HttpEntity<RobotRes> res =  restTemplate
                .exchange(String.format(ROBOT_URL, msg), HttpMethod.GET, new HttpEntity<>(null, headers),
                        RobotRes.class);
        if(((ResponseEntity<RobotRes>) res).getStatusCode().is2xxSuccessful() &&
                res.getBody() != null &&
                res.getBody().getContent()!= null){
            String content = res.getBody().getContent();
            log.info(content);
            return content;
        }
        return null;
    }
}
