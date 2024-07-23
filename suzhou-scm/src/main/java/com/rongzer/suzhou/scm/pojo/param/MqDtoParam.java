package com.rongzer.suzhou.scm.pojo.param;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2023/8/18 16:36
 * @Version 1.0
 **/
@Getter
@Setter
public class MqDtoParam implements Serializable {
    @NotBlank(message = "rowId不能为空")
    private String rowId;
    /**
     * 区块链表格名称
     */
    @NotBlank(message = "tableName不能为空")
    private String tableName;
    /**
     * 需上链数据
     */
    @NotNull(message = "tableData不能为空")
    private JSONObject tableData;
    /**
     * true 新增，false 更新
     */
    private boolean cochainType;

    private String cochainTime;

    // 将cochainType属性的默认值设置为true
    public MqDtoParam(){
        cochainType=true;
    }
}
