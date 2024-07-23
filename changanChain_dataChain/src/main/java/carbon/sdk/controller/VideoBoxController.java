package carbon.sdk.controller;

import carbon.sdk.dto.ResponseData;
import carbon.sdk.pojo.VideoCoChainParam;
import carbon.sdk.pojo.VideoHashParam;
import carbon.sdk.service.VideoBoxService;
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
 * @Date 2024/7/10 9:27
 * @Version 1.0
 **/
@RestController
public class VideoBoxController {

    @Resource
    private VideoBoxService videoBoxService;

    /**
     * @Description:视频盒子告警数据上链
     * @Author: tian.changlong
     * @Date: 2024/7/9 14:49
     * @param videoCoChainParam:
     * @return: java.lang.String
     **/
    @PostMapping("/cochainVideoBoxData")
    public String cochainVideoBoxData(@Valid @RequestBody VideoCoChainParam videoCoChainParam){
        ResponseData<String> rsdata=videoBoxService.cochainVideoBoxData(videoCoChainParam);
        if(rsdata.isSuccess()){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,rsdata).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,rsdata).toString();
        }
    }
    /**
     * @Description:通过videoBoxHash查询视频盒子告警信息
     * @Author: tian.changlong
     * @Date: 2024/7/9 14:49
     * @param videoHashParam:
     * @return: java.lang.String
     **/
    @PostMapping("/findByVideoBoxHash")
    public String findByVideoBoxHash(@Valid @RequestBody VideoHashParam videoHashParam){
        ResponseData<String> rsdata=videoBoxService.findByVideoBoxHash(videoHashParam);
        if(rsdata.isSuccess()){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,rsdata).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,rsdata).toString();
        }
    }
    /**
     * @Description:通过videoBoxHash删除视频盒子告警信息
     * @Author: tian.changlong
     * @Date: 2024/7/9 14:49
     * @param videoHashParam:
     * @return: java.lang.String
     **/
    @PostMapping("/deleteByVideoBoxHash")
    public String deleteByVideoBoxHash(@Valid @RequestBody VideoHashParam videoHashParam){
        ResponseData<String> rsdata=videoBoxService.deleteByVideoBoxHash(videoHashParam);
        if(rsdata.isSuccess()){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,rsdata).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,rsdata).toString();
        }
    }
}
