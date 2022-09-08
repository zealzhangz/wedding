package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.BarrageMapper;
import com.tencent.wxcloudrun.model.Barrage;
import com.tencent.wxcloudrun.service.BarrageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Created by zhangao/zhangao@yth.cn.<br/>
 * @version Version: 0.0.1
 * @date DateTime: 2022/09/08 11:29:00<br/>
 */
@Service
public class BarrageServiceImpl implements BarrageService {
    
    final BarrageMapper barrageMapper;

    private Map<String, Object> cache = new ConcurrentHashMap<>();

    public BarrageServiceImpl(@Autowired BarrageMapper barrageMapper) {
        this.barrageMapper = barrageMapper;
    }

    @Override
    public List<Barrage> getBarrageList() {
        List<Barrage> barrages = (List<Barrage>)cache.get("barrage");
        if(barrages != null && !barrages.isEmpty()){
            return barrages;
        } else {
            barrages = barrageMapper.getBarrageList();
            if(barrages != null && !barrages.isEmpty()){
                cache.put("barrage", barrages);
            } else{
                barrages = Collections.EMPTY_LIST;
            }
            return barrages;
        }
    }

    @Override
    public List<Barrage> selectBarrageByText(String text) {
        return barrageMapper.selectBarrageByText(text);
    }

    @Override
    public void insertBarrage(Barrage barrage) {
        if(barrage != null && barrage.getText() != null && StringUtils.isNotBlank(barrage.getText().trim())){
            if(cache.isEmpty()){
                cache.put("barrage",Arrays.asList(barrage));
            } else {
                List<Barrage> barrages = (List<Barrage>)cache.get("barrage");
                barrages.add(barrage);
                cache.put("barrage",barrages);
            }
        }
        barrageMapper.insertBarrage(barrage);
    }

    @Override
    public void deleteBarrage(String text) {
        cache.clear();
        barrageMapper.deleteBarrage(text);
    }
}
