package com.rongzer.suzhou.scm.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User:
 * Date:
 * Description:数据库表数据读写dao
 */
@Repository
public interface TableDataMapper {
    /**
     * 根据idKey查询txId
     * @param tableName
     * @param idKey
     * @return
     */
    String selectTxIdByIdKey(@Param("tableName") String tableName, @Param("idKey") String idKey);

    /**
     * 根据txId查询记录数
     * @param tableName
     * @param txId
     * @return
     */
    int countByTxId(@Param("tableName") String tableName, @Param("txId") String txId);

    /**
     * 根据表主键查询记录数
     * @param tableName
     * @param primaryKeys
     * @return
     */
    int countByPrimaryKey(@Param("tableName") String tableName, @Param("primaryKeys") List<Map<String, String>> primaryKeys);

    /**
     * 批量插入数据
     * @param tableInfo
     */
    public void batchInsert(@Param("tableInfo") List<Map<String, String>> tableInfo);


    /**
     * 批量更新数据
     * @param tableInfo
     */
    public void batchUpdate(@Param("tableInfo") List<Map<String, String>> tableInfo);

    /**
     * 批量新增或修改数据
     * @param data
     */
    public int batchReplaceInto(@Param("data") Map<String, Object> data);
    /**
     * 单个新增或修改数据
     * @param data
     */
    public int singleReplaceInto(@Param("data") Map<String, Object> data);

}