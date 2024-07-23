package com.rongzer.suzhou.scm.util;


import com.alibaba.fastjson.JSONObject;
import com.rongzer.suzhou.scm.pojo.Column;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pshe
 */
@Component
public class ExcelUtil {
    /**
     * 生成表头
     * @param columns
     * @return
     */
    public List<List<String>> getHeader(List<Column> columns) {
        List<List<String>> head = new ArrayList<>();
        for(Column column:columns){
            List<String> headCoulumn = new ArrayList<String>();
            StringBuilder columnName = new StringBuilder(column.getDes());
            headCoulumn.add(columnName.toString());
            head.add(headCoulumn);
        }
        return head;
    }

    /**
     * 设置导出的数据内容
     * @param dataList
     * @param columns
     * @return
     */
    public List<List<Object>> dataList(List<JSONObject> dataList, List<Column> columns) {
        List<List<Object>> list = new ArrayList<>();
        for (JSONObject map : dataList) {
            List<Object> data = new ArrayList<>();
            for (int i = 0; i < columns.size(); i++) {
                data.add(map.get(columns.get(i).getName()));
            }
            list.add(data);
        }
        return list;
    }
}
