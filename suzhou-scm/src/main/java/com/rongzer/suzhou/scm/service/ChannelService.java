package com.rongzer.suzhou.scm.service;


import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public interface ChannelService {

    JSONObject getChannelByChannelName(String channelName);

    List<JSONObject> countHistoryTransaction(int day,String channelId);

    Map<String, Integer> countActualTimeUpChain(int hours);
}
