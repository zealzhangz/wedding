package com.tencent.wxcloudrun.util;

import lombok.Data;

/**
 * @author Created by zhangao/zhangao@yth.cn.<br/>
 * @version Version: 0.0.1
 * @date DateTime: 2022/09/30 10:50:00<br/>
 */
@Data
public class ChpRes {
    private Detail data;
    
    @Data
    public class Detail {
        private String type;
        private String text;
    }
}
