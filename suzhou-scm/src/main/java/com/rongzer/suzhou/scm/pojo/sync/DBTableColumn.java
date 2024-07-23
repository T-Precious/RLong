package com.rongzer.suzhou.scm.pojo.sync;

public class DBTableColumn
{
  private String columnName;
  private String columnType;
  private String columnNull;
  private String columnComment;
  
  public void setColumnType(String columnType)
  {
    this.columnType = columnType;
  }
  
  public void setColumnNull(String columnNull)
  {
    this.columnNull = columnNull;
  }
  
  public void setColumnComment(String columnComment)
  {
    this.columnComment = columnComment;
  }
  
  public void setColumnName(String columnName)
  {
    this.columnName = columnName;
  }
  
  public String getColumnName()
  {
    return this.columnName;
  }
  
  public String getColumnType()
  {
    return this.columnType;
  }
  
  public String getColumnNull()
  {
    return this.columnNull;
  }
  
  public String getColumnComment()
  {
    return this.columnComment;
  }
}
