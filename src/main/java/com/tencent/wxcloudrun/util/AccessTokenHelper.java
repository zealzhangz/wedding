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
        Long now = System.currentTimeMillis();
        if (map.get("accessTokenCache") != null) {
            token = (AccessTokenResp) map.get("accessTokenCache");
            //提前一分钟生成缓存
            if (now > (token.getExpiresIn() - 60 * 1000)) {
                //重新获取token
                token = restTemplate.getForObject(ACCESS_TOKEN_URL, AccessTokenResp.class, APP_ID, APP_SECRET);
                if (token.getAccessToken() != null) {
                    token.setExpiresIn(now + token.getExpiresIn() * 1000);
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
            if (token.getAccessToken() != null) {
                token.setExpiresIn(now + token.getExpiresIn() * 1000);
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
        AccessTokenResp token = fetchAccessToken();
        Long now = System.currentTimeMillis();
        if (map.get("jsapiTicketCache") != null) {
            ticket = (JsTicketResp) map.get("jsapiTicketCache");
            //提前一分钟生成缓存
            if (now > (ticket.getExpiresIn() - 60 * 1000)) {
                if(token != null && token.getAccessToken() != null){
                    //重新获取token
                    ticket = restTemplate.getForObject(JSAPI_TICKET_URL, JsTicketResp.class, token.getAccessToken());
                    if (ticket.getTicket() != null) {
                        //设置过期绝对时间
                        ticket.setExpiresIn(now + ticket.getExpiresIn() * 1000);
                        map.put("jsapiTicketCache", ticket);
                    } else {
                        log.error("get jsapi ticket error >>>>>>>>>>>>>>>>>>>>>>[{}]", ticket.toString());
                        return null;
                    }
                } else {
                    log.error("get access token error");
                    return null;
                }
            } else {
                return ticket;
            }
        } else {
            if (token != null && token.getAccessToken() != null) {
                ticket = restTemplate.getForObject(JSAPI_TICKET_URL, JsTicketResp.class, token.getAccessToken());
                if (ticket.getTicket() != null) {
                    //设置过期绝对时间
                    ticket.setExpiresIn(now + ticket.getExpiresIn() * 1000);
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
