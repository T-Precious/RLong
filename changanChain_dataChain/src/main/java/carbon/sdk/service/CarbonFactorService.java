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

@Slf4j
@Service
public class CarbonFactorService {
    @Resource
    private ChainClient chainClient;
    @Resource
    private ChainService chainService;

    public ResponseData<String> addCarbonFactor(String factorId, String factorInfo) {
        try {
            // 构造合约调用参数
            Map<String, byte[]> params = new HashMap<>();
            params.put("factorId", factorId.getBytes());
            params.put("factorInfo", factorInfo.getBytes());

            ResultOuterClass.TxResponse responseInfo = chainService.invokeContract(chainClient, ContractConst.CONTRACT_NAME,
                    ContractConst.ADD_CARBON_FACTOR, params, ContractLanguageTypeEnum.DOCKER_GO.getMsg());
            if (responseInfo.getCodeValue() != 0 || responseInfo.getContractResult().getCode() != 0) {
                log.error(responseInfo.getContractResult().getResult().toStringUtf8());
                return ResponseData.fail(CarbonErrorCode.ADD_CARBON_FACTOR_FAIL, responseInfo.getContractResult().getResult().toStringUtf8(), responseInfo.getTxId());
            }
            return ResponseData.ok(ServiceConst.SUCCESS, responseInfo.getTxId());
        } catch (Exception e) {
            log.error("添加排放因子分类信息失败");
            return ResponseData.fail(CarbonErrorCode.ADD_CARBON_FACTOR_FAIL);
        }
    }
}
