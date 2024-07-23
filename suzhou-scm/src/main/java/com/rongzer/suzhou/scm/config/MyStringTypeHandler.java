package com.rongzer.suzhou.scm.config;

import org.apache.ibatis.executor.result.ResultMapException;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.StringTypeHandler;

import java.sql.*;

/**
 *
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
public class MyStringTypeHandler extends StringTypeHandler {

  @Override
  public String getResult(ResultSet rs, String columnName) throws SQLException {
    String result;
    try {
      result = getNullableResult(rs, columnName);
    } catch (Exception e) {
      throw new ResultMapException("Error attempting to get column '" + columnName + "' from result set.  Cause: " + e, e);
    }
    return result;

  }
  @Override
  public String getNullableResult(ResultSet rs, String columnName)
      throws SQLException {
    String rsString = rs.getString(columnName);
    if(rsString==null){
      return "";
    }
    return rsString;
  }

  @Override
  public String getNullableResult(ResultSet rs, int columnIndex)
      throws SQLException {
    String rsString = rs.getString(columnIndex);
    if(rsString==null){
      return "";
    }
    return rsString;
  }

  @Override
  public String getNullableResult(CallableStatement cs, int columnIndex)
      throws SQLException {
    String rsString = cs.getString(columnIndex);
    if(rsString==null){
      return "";
    }
    return rsString;
  }
}