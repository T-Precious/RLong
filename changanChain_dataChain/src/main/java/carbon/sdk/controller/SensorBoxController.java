package carbon.sdk.controller;

import carbon.sdk.dto.ResponseData;
import carbon.sdk.pojo.SensorCoChainParam;
import carbon.sdk.pojo.SensorHashParam;
import carbon.sdk.service.SensorBoxService;
import com.rongzer.eloan.common.starter.constant.CommonConst;
import com.rongzer.eloan.common.starter.vo.ResultVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2024/7/9 14:13
 * @Version 1.0
 **/
@RestController
public class SensorBoxController {

    @Resource
    private SensorBoxService sensorBoxService;
    /**
     * @Description:传感器告警数据上链
     * @Author: tian.changlong
     * @Date: 2024/7/9 14:49
     * @param sensorCoChainParam:
     * @return: java.lang.String
     **/
    @PostMapping("/cochainSensorBoxData")
    public String cochainSensorBoxData(@Valid @RequestBody SensorCoChainParam sensorCoChainParam){
        ResponseData<String> rsdata=sensorBoxService.cochainSensorBoxData(sensorCoChainParam);
        if(rsdata.isSuccess()){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,rsdata).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,rsdata).toString();
        }
    }
    /**
     * @Description:通过sensorBoxHash查询传感器告警信息
     * @Author: tian.changlong
     * @Date: 2024/7/9 14:49
     * @param sensorHashParam:
     * @return: java.lang.String
     **/
    @PostMapping("/findBySensorBoxHash")
    public String findBySensorBoxHash(@Valid @RequestBody SensorHashParam sensorHashParam){
        ResponseData<String> rsdata=sensorBoxService.findBySensorBoxHash(sensorHashParam);
        if(rsdata.isSuccess()){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,rsdata).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,rsdata).toString();
        }
    }
    /**
     * @Description:通过sensorBoxHash删除传感器告警信息
     * @Author: tian.changlong
     * @Date: 2024/7/9 14:49
     * @param sensorHashParam:
     * @return: java.lang.String
     **/
    @PostMapping("/deleteBySensorBoxHash")
    public String deleteBySensorBoxHash(@Valid @RequestBody SensorHashParam sensorHashParam){
        ResponseData<String> rsdata=sensorBoxService.deleteBySensorBoxHash(sensorHashParam);
        if(rsdata.isSuccess()){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,rsdata).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,rsdata).toString();
        }
    }
}
