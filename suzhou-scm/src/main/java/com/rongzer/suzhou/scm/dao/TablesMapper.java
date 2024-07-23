package com.rongzer.suzhou.scm.dao;

import com.alibaba.fastjson.JSONObject;
import com.rongzer.suzhou.scm.pojo.sync.DBTableColumn;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User:
 * Date:
 * Description:数据库表读写dao
 */
@Repository
public interface TablesMapper {
    /**
     * 判断表名是否已存在
     * @param tableName
     * @return
     */
    int findTableCountByTableName(@Param("tableName") String tableName);

    /**
     * 查询表的所有字段名称，放入list中
     * @param tableName
     * @return
     */
    List<String> selectColumnsByTableName(@Param("tableName") String tableName);

    List<JSONObject> selectTableDataByIndex(@Param("tableName") String tableName,@Param("indexName") String indexName,
                                      @Param("primaryValues") List<String> primaryValues,@Param("page") Integer page,@Param("pageSize") Integer pageSize);

    List<JSONObject> selectSoleTableDataByIndex(@Param("tableName") String tableName,@Param("indexName") String indexName,@Param("indexValue") String indexValue,@Param("sortField") String sortField);

    int deleteTableDataByIndex(@Param("tableName") String tableName,@Param("indexName") String indexName,
                               @Param("primaryValues") List<String> primaryValues);
    /**
     * 创建表结构
     * @param tableName
     * @param tableDesc
     * @param columns
     * @param primaryKeys
     * @return
     */
    int createModelTable(@Param("tableName") String tableName,
                         @Param("tableDesc") String tableDesc,
                         @Param("columns") List<DBTableColumn> columns,
                         @Param("primaryKeys") List<String> primaryKeys);

}