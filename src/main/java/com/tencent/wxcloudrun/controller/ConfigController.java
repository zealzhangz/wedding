package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.TreeMap;

import static com.tencent.wxcloudrun.constant.WeChatConstant.APP_ID;

/**
 * @author Created by zhangao/zhangao@yth.cn.<br/>
 * @version Version: 0.0.1
 * @date DateTime: 2022/09/07 17:38:00<br/>
 */
@RestController
public class ConfigController {
    final Logger logger = LoggerFactory.getLogger(ConfigController.class);
    @Autowired
    private AccessTokenHelper accessTokenHelper;
    
    @GetMapping(value = "/api/config")
    ApiResponse config(HttpServletRequest request) {
        String url = request.getScheme() + "://" + request.getServerName() + request.getServletPath();
        logger.info("/api/config get request,url:" + url);
        ConfigRes configRes = new ConfigRes();
        configRes.setAppId(APP_ID);
        configRes.setNonceStr(StringKit.genRandomString32());
        configRes.setTimestamp(String.valueOf(System.currentTimeMillis()));

        TreeMap<String, Object> params = new TreeMap<String, Object>();
        JsTicketResp resp = accessTokenHelper.jsapiTicketCache();
        if(resp != null && resp.getTicket() != null){
            params.put("jsapi_ticket", resp.getTicket());
            params.put("noncestr", configRes.getNonceStr());
            params.put("timestamp", configRes.getTimestamp());
            params.put("url", url);

            configRes.setSignature(HashKit.signSHA1(params));
            return ApiResponse.ok(configRes);
        } else {
            logger.error("获取 ticket 失败");
            return ApiResponse.error("获取 ticket 失败");
        }
    }
}
