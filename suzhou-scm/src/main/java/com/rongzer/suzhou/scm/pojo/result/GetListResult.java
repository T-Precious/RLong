package com.rongzer.suzhou.scm.pojo.result;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: luolingling
 * Date: 2021/12/28
 * Description:
 */
@Setter
@Getter
public class GetListResult<T> {

    //是最后一页
    public static final String LAST_PAGE = "Y";

    //非最后一页
    public static final String NOT_LAST_PAGE = "N";

    /**
     * 总数量
     */
    private int totalSize;

    /**
     * 是否最后一页
     */
    private String isLastPage;

    /**
     * 列表
     */
    private List<T> list;
    /**
     * 列表为空返回对象
     * @return
     */
    public static GetListResult getEmptyResult() {
        GetListResult result = new GetListResult();
        result.setTotalSize(0);
        result.setIsLastPage(GetListResult.LAST_PAGE);
        result.setList(new ArrayList<>());
        return result;
    }

}