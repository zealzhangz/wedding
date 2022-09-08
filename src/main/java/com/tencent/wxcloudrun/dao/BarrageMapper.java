package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Barrage;
import com.tencent.wxcloudrun.model.Counter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BarrageMapper {

  List<Barrage> getBarrageList();

  List<Barrage> selectBarrageByText(String text);

  void insertBarrage(Barrage barrage);
  
  void deleteBarrage(String text);
}
