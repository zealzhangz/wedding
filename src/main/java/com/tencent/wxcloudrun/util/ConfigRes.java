package com.tencent.wxcloudrun.util;

import lombok.Data;

/**
 * @author Created by zhangao/zhangao@yth.cn.<br/>
 * @version Version: 0.0.1
 * @date DateTime: 2022/09/07 17:41:00<br/>
 */
@Data
public class ConfigRes {
    
    private  String appId;
    private String timestamp;
    private String nonceStr;
    private String signature;
}
