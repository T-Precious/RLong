package carbon.sdk.controller;

import carbon.sdk.dto.ResponseData;
import carbon.sdk.pojo.GoTestParam;
import carbon.sdk.service.GoTestService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2024/7/4 10:11
 * @Version 1.0
 **/
@RestController
public class GoTestController {

    @Resource
    private GoTestService goTestService;

    @PostMapping("/addGoTest")
    public String addGoTest(@Valid @RequestBody GoTestParam goTestParam){
        ResponseData<String> rsdata=goTestService.addGoTest(goTestParam.getFileHash(),goTestParam.getFileName(),goTestParam.getTime());
        if(rsdata.isSuccess()){
            return rsdata.getData();
        }else{
            return rsdata.getMessage();
        }
    }
}
