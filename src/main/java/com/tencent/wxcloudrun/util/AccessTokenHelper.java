package com.tencent.wxcloudrun.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.tencent.wxcloudrun.constant.WeChatConstant.*;


/**
 * @author Atom
 */
@Slf4j
@Service
public class AccessTokenHelper {

    @Resource
    private RestTemplate restTemplate;

    private Map<String, Object> map = new ConcurrentHashMap<>();

    /**
     * fetch AccessToken.
     * 公众平台的API调用所需的access_token，即微信接口调用凭证
     *
     * @return
     */
    public AccessTokenResp fetchAccessToken() {
        AccessTokenResp token = null;
        if (map.get("accessTokenCache") != null) {
            token = (AccessTokenResp) map.get("accessTokenCache");
            Long now = System.currentTimeMillis();
            //提前一分钟生成缓存
            if (now > (token.getExpiresIn() - 60 * 1000)) {
                //重新获取token
                token = restTemplate.getForObject(ACCESS_TOKEN_URL, AccessTokenResp.class, APP_ID, APP_SECRET);
                if(token.getAccessToken() != null){
                    map.put("accessTokenCache", token);
                } else {
                    log.error("get accessToken error >>>>>>>>>>>>>>>>>>>>>>[{}]", token.toString());
                    return null;
                }
            } else {
                return token;
            }
        } else {
            token = restTemplate.getForObject(ACCESS_TOKEN_URL, AccessTokenResp.class, APP_ID, APP_SECRET);
            if(token.getAccessToken() != null){
                map.put("accessTokenCache", token);
            } else {
                log.error("get accessToken error >>>>>>>>>>>>>>>>>>>>>>[{}]", token.toString());
                return null;
            }
        }
        log.info("get accessToken method from Tencent>>>>>>>>>>>>>>>>>>>>>>[{}]", token.toString());
        return token;
    }

    public JsTicketResp jsapiTicketCache() {
        JsTicketResp ticket = null;
        if (map.get("jsapiTicketCache") != null) {
            ticket = (JsTicketResp)map.get("jsapiTicketCache");
            Long now = System.currentTimeMillis();
            //提前一分钟生成缓存
            if (now > (ticket.getExpiresIn() - 60 * 1000)) {
                //重新获取token
                ticket = restTemplate.getForObject(JSAPI_TICKET_URL, JsTicketResp.class, fetchAccessToken().getAccessToken());
                if(ticket.getTicket() != null){
                    map.put("jsapiTicketCache", ticket);
                } else {
                    log.error("get jsapi ticket error >>>>>>>>>>>>>>>>>>>>>>[{}]", ticket.toString());
                    return null;
                }
            } else {
                return ticket;
            }
        } else {
            AccessTokenResp token = fetchAccessToken();
            if(token != null && token.getAccessToken() != null){
                ticket = restTemplate.getForObject(JSAPI_TICKET_URL, JsTicketResp.class, fetchAccessToken().getAccessToken());
                if(ticket != null){
                    map.put("jsapiTicketCache", ticket);
                } else {
                    log.error("get jsapi ticket error >>>>>>>>>>>>>>>>>>>>>>[{}]", ticket.toString());
                    return null;
                }
            } else {
                log.error("get access token error");
                return null;
            }
        }
        log.info("get jsapiTicket method from Tencent>>>>>>>>>>>>>>>>>>>>>>[{}]", ticket.toString());
        return ticket;
    }
    
    

}
