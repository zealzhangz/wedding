package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Barrage;

import java.util.List;

/**
 * @author Created by zhangao/zhangao@yth.cn.<br/>
 * @version Version: 0.0.1
 * @date DateTime: 2022/09/08 11:29:00<br/>
 */
public interface BarrageService {
    List<Barrage> getBarrageList();

    List<Barrage> selectBarrageByText(String text);
    
    void insertBarrage(Barrage barrage);
    
    void deleteBarrage(String text);
}
