package com.rongzer.suzhou.scm.service.impl;


import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.google.common.collect.Lists;
import com.rongzer.rdp.core.tool.utils.CollectionUtil;
import com.rongzer.suzhou.scm.dao.ChannelMapper;
import com.rongzer.suzhou.scm.service.ChannelService;
import com.rongzer.suzhou.scm.util.DateUtil;
import com.rongzer.util.LocalDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/**
 * @Description TODO
 * @Author pshe
 * @Date 2022/12/28 10:41
 * @Version 1.0
 **/
@Slf4j
@Service
@DS("db2")
public class ChannelServiceimpl implements ChannelService {

    @Autowired
    private ChannelMapper channelMapper;
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public JSONObject getChannelByChannelName(String channelName) {
        return channelMapper.getChannelByChannelName(channelName);
    }

    @Override
    public List<JSONObject> countHistoryTransaction(int day,String channelId) {
        String sql = "SELECT\n" +
                "\tt1.days,\n" +
                "\tCOUNT( t2.id ) AS blockNum,\n" +
                "\tIFNULL( SUM( t2.tx_count ), 0 ) AS txNum \n" +
                "FROM\n" +
                "\t(\n" +
                "\tSELECT\n" +
                "\t\tDATE_FORMAT( @cdate := DATE_ADD( @cdate, INTERVAL - 1 DAY ), '%Y-%m-%d' ) AS days \n" +
                "\tFROM\n" +
                "\t\t( SELECT @cdate := DATE_ADD( NOW(), INTERVAL + 1 DAY ) FROM `t_rbaas_channel_trade` ) t0 \n" +
                "\t\tLIMIT "+day+" \n" +
                "\t) t1\n" +
                "\tLEFT JOIN ( SELECT id, channel_id, tx_count, DATE_FORMAT( create_time, '%Y-%m-%d' ) AS days FROM t_rbaas_channel_trade WHERE `channel_id` = ' "+channelId + "' ) t2 ON t2.days = t1.days \n" +
                "GROUP BY\n" +
                "\tt1.days order by t1.days asc";
        PreparedStatement pst = null;
        SqlSession session = getSqlSession();
        ResultSet result = null;
        List<JSONObject> list = new ArrayList<>();
        try {
            pst = session.getConnection().prepareStatement(sql);
            result = pst.executeQuery();
            //获得结果集结构信息,元数据
            ResultSetMetaData md = result.getMetaData();
            //获得列数
            int columnCount = md.getColumnCount();
            while (result.next()) {
                JSONObject rowData = new JSONObject();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), result.getObject(i));
                }
                list.add(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(pst!=null){
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeSqlSession(session);
        }
        return list;
    }

    @Override
    public Map<String, Integer> countActualTimeUpChain(int hours) {
        List<String> allHours = Lists.newArrayList();
        /**
         * 由于sql中between不包含头尾，所以开始时间-1，结束时间+1
         */
        String endHour = DateUtil.getFirstFewHours(LocalDateUtil.nowDate("yyyy-MM-dd HH"), "yyyy-MM-dd HH", true, 1);
        for(int i=0;i<hours;i++){
            String perHour = DateUtil.getFirstFewHours(LocalDateUtil.nowDate("yyyy-MM-dd HH"), "yyyy-MM-dd HH", false, i);
            allHours.add(perHour);
        }
        String startHour = DateUtil.getFirstFewHours(LocalDateUtil.nowDate("yyyy-MM-dd HH"), "yyyy-MM-dd HH", false, hours+1);

        List<Map<String, String>> countUpChainMaps = channelMapper.countActualTimeUpChain(startHour,endHour);
        //存放从数据库查询出来的所有的时间
        List<String> dayHours = new ArrayList<>();
        Map<String, Integer> countUpChainMap = new HashMap<>();
        if(CollectionUtil.isNotEmpty(countUpChainMaps)){
            for(Map<String, String> map:countUpChainMaps){
                String dayHour = map.get("dayHour");
                String count = String.valueOf(map.get("Count"));
                dayHours.add(dayHour);

                countUpChainMap.put(dayHour,Integer.parseInt(count));
            }
        }

        // 创建LinkedHashMap进行接受
        Map<String,Integer> orderMap = new LinkedHashMap<>();
        for(String hour:allHours){
            if(!dayHours.contains(hour)){
                countUpChainMap.put(hour,0);
            }
        }
        countUpChainMap.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEachOrdered(entry->{orderMap.put(entry.getKey().substring(11), entry.getValue());});
        return orderMap;
    }

    /**
     * 获取sqlSession
     * @return
     */
    public SqlSession getSqlSession(){
        return SqlSessionUtils.getSqlSession(sqlSessionTemplate.getSqlSessionFactory(),
                sqlSessionTemplate.getExecutorType(), sqlSessionTemplate.getPersistenceExceptionTranslator());
    }
    /**
     * 关闭sqlSession
     * @param session
     */
    public void closeSqlSession(SqlSession session){
        SqlSessionUtils.closeSqlSession(session, sqlSessionTemplate.getSqlSessionFactory());
    }
}
