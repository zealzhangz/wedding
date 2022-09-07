package com.tencent.wxcloudrun.util;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Created by zhangao/zhangao@yth.cn.<br/>
 * @version Version: 0.0.1
 * @date DateTime: 2022/09/07 17:15:00<br/>
 */
@Data
public class JsTicketResp implements Serializable {
    /**
     * 正确获取到 ticket 时有值
     */
    private String ticket;
    /**
     * 正确获取到 access_token 时有值
     */
    private Integer expiresIn;
    /**
     *  出错时有值
     */
    private Integer errcode;
    /**
     * 出错时有值
     */
    private String errmsg;
    /**
     * 正确获取到 ticket 时有值，存放过期时间
     */
    private Long expiredTime;
}
