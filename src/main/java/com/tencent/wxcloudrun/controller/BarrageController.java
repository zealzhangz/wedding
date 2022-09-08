package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.BarrageRequest;
import com.tencent.wxcloudrun.dto.CounterRequest;
import com.tencent.wxcloudrun.model.Barrage;
import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.service.BarrageService;
import com.tencent.wxcloudrun.service.CounterService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * counter控制器
 */
@CrossOrigin
@RestController
public class BarrageController {

    final BarrageService barrageService;
    final Logger logger;

    public BarrageController(@Autowired BarrageService barrageService) {
        this.barrageService = barrageService;
        this.logger = LoggerFactory.getLogger(BarrageController.class);
    }


    /**
     * 获取当前计数
     *
     * @return API response json
     */
    @GetMapping(value = "/api/list")
    ApiResponse get() {
        logger.info("/api/list get request");
        List<Barrage> barrages = barrageService.getBarrageList();
        return ApiResponse.ok(barrages);
    }


    /**
     * 插入弹幕
     *
     * @param request {@link CounterRequest}
     * @return API response json
     */
    @PostMapping(value = "/api/comment")
    ApiResponse create(@RequestBody BarrageRequest request, HttpServletRequest req) {
        logger.info("/api/comment post request, action: {}", request.getText());
        if (request != null && request.getText() != null && StringUtils.isNotBlank(request.getText().trim())) {
            List<Barrage> exist = barrageService.selectBarrageByText(request.getText().trim());
            if(!exist.isEmpty()){
                return ApiResponse.error(request.getText().trim() + "已经存在");
            }
            Barrage barrage = new Barrage();
            barrage.setText(request.getText().trim());
            barrage.setDeleted(0);
            barrage.setIp(getUserIp(req));
            try {
                barrageService.insertBarrage(barrage);
                return ApiResponse.ok("insert " + request.getText().trim() + "success");
            } catch (Exception e) {
                logger.info("写入数据异常", e);
                return ApiResponse.error(e.getMessage());
            }
        } else {
            logger.info("无效的评论");
            return ApiResponse.error("无效的评论");
        }
    }

    private String getUserIp(HttpServletRequest request) {
        String ip = request.getHeader("X-real-ip");//先从nginx自定义配置获取  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 删除弹幕
     *
     * @return API response json
     */
    @GetMapping(value = "/api/delete")
    ApiResponse delete(String text) {
        logger.info("/api/delete get request");
        if (text != null && text != null && StringUtils.isNotBlank(text.trim())) {
            try{
                barrageService.deleteBarrage(text);
                return ApiResponse.ok();
            } catch (Exception e){
                logger.info("删除数据异常", e);
                return ApiResponse.error(e.getMessage());
            }
        } else {
            logger.error("被删除数据无效:" + text);
            return ApiResponse.error("被删除数据无效:" + text);
        }
    }
}