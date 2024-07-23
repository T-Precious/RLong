package com.rongzer.suzhou.scm.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface ChannelMapper {
    JSONObject getChannelByChannelName(@Param("channelName") String channelName);

    List<Map<String, String>> countActualTimeUpChain(@Param("startHour")String startHour, @Param("endHour")String endHour);
}
