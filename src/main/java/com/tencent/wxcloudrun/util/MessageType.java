package com.tencent.wxcloudrun.util;

/**
 * @author Created by zhangao/zhangao@yth.cn.<br/>
 * @version Version: 0.0.1
 * @date DateTime: 2022/09/30 10:30:00<br/>
 */
public enum MessageType {
    /**
     * 接收到的消息类型
     */
        TEXT,//文本消息
        IMAGE,//图片消息
        VOICE,//语音消息
        VIDEO,//视频消息
        SHORTVIDEO,//小视频消息
        LOCATION,//地理位置消息
        LINK,//链接消息
        EVENT//事件消息
}
