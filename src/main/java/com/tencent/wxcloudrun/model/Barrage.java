package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Created by zhangao/zhangao@yth.cn.<br/>
 * @version Version: 0.0.1
 * @date DateTime: 2022/09/08 11:22:00<br/>
 */
@Data
public class Barrage {
    private Integer id;

    private String text;
    
    private String ip;
    
    private Integer deleted = 0;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
