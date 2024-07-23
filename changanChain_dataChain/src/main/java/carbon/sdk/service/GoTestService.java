package carbon.sdk.service;

import carbon.sdk.constants.ContractConst;
import carbon.sdk.constants.ServiceConst;
import carbon.sdk.dto.ResponseData;
import carbon.sdk.enums.CarbonErrorCode;
import carbon.sdk.enums.ContractLanguageTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.chainmaker.pb.common.ResultOuterClass;
import org.chainmaker.sdk.ChainClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2024/7/4 10:13
 * @Version 1.0
 **/
@Slf4j
@Service
public class GoTestService {
    @Resource
    private ChainClient chainClient;
    @Resource
    private ChainService chainService;

    public ResponseData<String> addGoTest(String fileHash, String fileName,String time) {
        try {
            // 构造合约调用参数
            Map<String, byte[]> params = new HashMap<>();
            params.put("file_hash", fileHash.getBytes());
            params.put("file_name", fileName.getBytes());
            params.put("time", time.getBytes());

            ResultOuterClass.TxResponse responseInfo = chainService.invokeContract(chainClient, ContractConst.CONTRACT_NAME,
                    ContractConst.ADD_CARBON_FACTOR, params, ContractLanguageTypeEnum.DOCKER_GO.getMsg());
            if (responseInfo.getCodeValue() != 0 || responseInfo.getContractResult().getCode() != 0) {
                log.error(responseInfo.getContractResult().getResult().toStringUtf8());
                return ResponseData.fail(CarbonErrorCode.ADD_CARBON_FACTOR_FAIL, responseInfo.getContractResult().getResult().toStringUtf8(), responseInfo.getTxId());
            }
            return ResponseData.ok(ServiceConst.SUCCESS, responseInfo.getTxId());
        } catch (Exception e) {
            log.error("添加测试信息失败");
            return ResponseData.fail(CarbonErrorCode.ADD_CARBON_FACTOR_FAIL);
        }
    }
}
