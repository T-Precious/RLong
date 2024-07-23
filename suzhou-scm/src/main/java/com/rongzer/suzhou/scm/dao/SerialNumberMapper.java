package com.rongzer.suzhou.scm.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: luolingling
 * Date: 2022/5/9
 * Description:各业务每日流水号计数器
 */
@Repository
public interface SerialNumberMapper {

    /**
     * 插入
     * @param date
     * @param type
     * @param num
     * @return
     */
    int insert(@Param("type") String type,
               @Param("date") String date,
               @Param("num") int num);

    /**
     * 根据主键查询num
     * @param date
     * @param type
     * @return
     */
    int selectId(@Param("type") String type,
                 @Param("date") String date);

    /**
     * num+1
     * @param date
     * @param type
     * @return
     */
    int incrementId(@Param("type") String type,
                    @Param("date") String date);
}