package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.util.HashKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * @author Created by zhangao/zhangao@yth.cn.<br/>
 * @version Version: 0.0.1
 * @date DateTime: 2022/09/29 23:54:00<br/>
 */
@Slf4j
@CrossOrigin
@RestController
public class ReplyMsgController {
    /**
     * token值必须和微信公众号中配置的完全一致！！！
     */
    private final String token = "565341313699246";

    @GetMapping(value = "/api/reply")
    String get(@RequestParam(value = "signature") String signature,
               @RequestParam(value = "timestamp") String timestamp,
               @RequestParam(value = "nonce") String nonce,
               @RequestParam(value = "echostr") String echostr) {
        return wxSignatureCheck(signature, timestamp, nonce, echostr);
    }

    public String wxSignatureCheck(String signature, String timestamp, String nonce, String echostr) {
        ArrayList<String> array = new ArrayList<String>();
        array.add(signature);
        array.add(timestamp);
        array.add(nonce);

        //排序
        String sortString = sort(token, timestamp, nonce);
        //加密
        String mytoken = HashKit.sha1(sortString);
        //校验签名
        if (mytoken != null && mytoken != "" && mytoken.equals(signature)) {
            log.info("签名校验通过。");
            return echostr; //如果检验成功输出echostr，微信服务器接收到此输出，才会确认检验完成。
        } else {
            log.error("签名校验失败。");
            return null;
        }
    }

    /**
     * 排序方法
     *
     * @param token
     * @param timestamp
     * @param nonce
     * @return
     */
    public static String sort(String token, String timestamp, String nonce) {
        String[] strArray = {token, timestamp, nonce};
        Arrays.sort(strArray);

        StringBuilder sbuilder = new StringBuilder();
        for (String str : strArray) {
            sbuilder.append(str);
        }

        return sbuilder.toString();
    }
}
