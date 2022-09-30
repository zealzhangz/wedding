package com.tencent.wxcloudrun.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * @author Created by zhangao/zhangao@yth.cn.<br/>
 * @version Version: 0.0.1
 * @date DateTime: 2022/09/30 21:00:00<br/>
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Info{
    private String text;
    private List<String> heuristic;
}
