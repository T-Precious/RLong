package com.rongzer.suzhou.scm.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rongzer.suzhou.scm.dao.ForEnterprisesMapper;
import com.rongzer.suzhou.scm.pojo.param.ForEnterprisesPageParam;
import com.rongzer.suzhou.scm.pojo.param.ForEnterprisesParam;
import com.rongzer.suzhou.scm.service.ForEnterprisesService;
import com.rongzer.suzhou.scm.service.LeadCockpitService;
import com.rongzer.suzhou.scm.vo.ReturnPageResult;
import com.rongzer.suzhou.scm.vo.ReturnResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2022/12/28 10:41
 * @Version 1.0
 **/
@Slf4j
@Service
public class ForEnterprisesServiceimpl implements ForEnterprisesService {

    @Autowired
    private ForEnterprisesMapper forEnterprisesMapper;
    @Autowired
    private LeadCockpitService leadCockpitService;

    @Override
    public ReturnPageResult selectAllForEnterprises(ForEnterprisesPageParam forEnterprisesPageParam) {
        ReturnPageResult returnResult=new ReturnPageResult();
        returnResult.setStatus(1);
        returnResult.setMsg("查询四上企业成功");
        try {
            PageHelper.startPage(forEnterprisesPageParam.getCurrentPage(),forEnterprisesPageParam.getPageSize());
            List<JSONObject> jsonObjects=forEnterprisesMapper.selectAllForEnterprises();
            //实列化PageInfo
            PageInfo<JSONObject> pageInfo=new PageInfo<>(jsonObjects);
            returnResult.setCurrentPage(pageInfo.getPageNum());
            returnResult.setTotalPages(pageInfo.getPages());
            returnResult.setPageSize(pageInfo.getPageSize());
            returnResult.setTotalRows(pageInfo.getTotal());
            if(returnResult.getCurrentPage()>returnResult.getTotalPages()){
                returnResult.setStatus(0);
                returnResult.setMsg("查询四上企业异常：当前页码大于总页码数");
                return returnResult;
            }
           /* JSONArray jsonArray=new JSONArray();
            for(int i=0;i<jsonObjects.size();i++){
                JSONObject object=jsonObjects.get(i);
                ForEnterprisesParam forEnterprisesParam=new ForEnterprisesParam();
                forEnterprisesParam.setSocialCreditCode(object.getString("social_credit_code"));
                ReturnResult result=leadCockpitService.selectEnterpriseInfoByUscc(forEnterprisesParam);
                if(result.getStatus()==1){
                    JSONObject object1= (JSONObject) result.getData();
                    object.put("assessScore",object1.getString("assessScore"));
                    object.put("doubleAnnouncement",object1.getString("doubleAnnouncement"));
                    object.put("ratingLevel",object1.getString("ratingLevel"));
                    object.put("creditAppeal",object1.getString("creditAppeal"));
                    jsonArray.add(object);
                }else{
                    returnResult.setStatus(0);
                    returnResult.setMsg("查询四上企业的评分等级信息异常："+result.getMsg());
                    log.error("通过统一社会信用代码查询企业评分等级信息异常：method:selectEnterpriseInfoByUscc,param(social_credit_code):"+
                            object.getString("social_credit_code")+",result:"+result.getMsg());
                    break;
                }
            }*/
            returnResult.setData(jsonObjects);
        }catch (Exception e){
            returnResult.setStatus(0);
            returnResult.setMsg("查询四上企业异常："+e.getMessage());
            log.error("查询四上企业异常："+e.toString());
        }
        return returnResult;
    }

    @Override
    public ReturnResult selectForEnterprisesBySocialCreditCode(ForEnterprisesParam forEnterprisesParam) {
        ReturnResult returnResult=new ReturnResult();
        returnResult.setStatus(1);
        returnResult.setMsg("通过统一社会信用代码查询四上企业成功");
        try {
            List<JSONObject> jsonObjects=forEnterprisesMapper.selectForEnterprisesBySocialCreditCode(forEnterprisesParam.getSocialCreditCode());
            if (jsonObjects.size()>0){
                returnResult.setData(jsonObjects.get(0));
            }else{
                returnResult.setStatus(0);
                returnResult.setMsg("通过统一社会信用代码未查询到四上企业");
            }
        }catch (Exception e){
            returnResult.setStatus(0);
            returnResult.setMsg("通过统一社会信用代码查询四上企业异常："+e.getMessage());
            log.error("通过统一社会信用代码查询四上企业异常："+e.toString());
        }
        return returnResult;
    }

    @Override
    public ReturnResult updateForEnterprises(ForEnterprisesParam forEnterprisesParam) {
        ReturnResult returnResult=new ReturnResult();
        try {
            List<JSONObject> jsonObjects=forEnterprisesMapper.selectForEnterprisesBySocialCreditCode(forEnterprisesParam.getSocialCreditCode());
            if (jsonObjects.size()>0){
                int num=forEnterprisesMapper.updateForEnterprises(forEnterprisesParam);
                if(num>0){
                    returnResult.setStatus(1);
                    returnResult.setMsg("更新四上企业经纬度成功");
                }else{
                    returnResult.setStatus(0);
                    returnResult.setMsg("更新四上企业经纬度失败");
                }
            }else{
                returnResult.setStatus(0);
                returnResult.setMsg("未查询到需要更新的四上企业信息");
            }
        }catch (Exception e){
            returnResult.setStatus(0);
            returnResult.setMsg("更新四上企业经纬度异常："+e.getMessage());
            log.error("更新四上企业经纬度异常："+e.toString());
        }
        return returnResult;
    }
}
