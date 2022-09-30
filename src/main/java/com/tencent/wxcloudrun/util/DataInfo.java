package com.tencent.wxcloudrun.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author Created by zhangao/zhangao@yth.cn.<br/>
 * @version Version: 0.0.1
 * @date DateTime: 2022/09/30 20:59:00<br/>
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataInfo{
    private Integer type;
    private Info info;
}
