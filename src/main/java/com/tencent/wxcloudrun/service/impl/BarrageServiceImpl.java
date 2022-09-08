package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.BarrageMapper;
import com.tencent.wxcloudrun.model.Barrage;
import com.tencent.wxcloudrun.service.BarrageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Created by zhangao/zhangao@yth.cn.<br/>
 * @version Version: 0.0.1
 * @date DateTime: 2022/09/08 11:29:00<br/>
 */
@Service
public class BarrageServiceImpl implements BarrageService {
    
    final BarrageMapper barrageMapper;

    public BarrageServiceImpl(@Autowired BarrageMapper barrageMapper) {
        this.barrageMapper = barrageMapper;
    }

    @Override
    public List<Barrage> getBarrageList() {
        return barrageMapper.getBarrageList();
    }

    @Override
    public List<Barrage> selectBarrageByText(String text) {
        return barrageMapper.selectBarrageByText(text);
    }

    @Override
    public void insertBarrage(Barrage barrage) {
        barrageMapper.insertBarrage(barrage);
    }

    @Override
    public void deleteBarrage(String text) {
        barrageMapper.deleteBarrage(text);
    }
}
