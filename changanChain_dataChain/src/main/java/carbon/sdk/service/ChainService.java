package carbon.sdk.service;

import carbon.sdk.enums.CarbonErrorCode;
import carbon.sdk.enums.ContractLanguageTypeEnum;
import carbon.sdk.exception.CarbonSdkException;
import lombok.extern.slf4j.Slf4j;
import org.chainmaker.pb.common.ResultOuterClass;
import org.chainmaker.sdk.ChainClient;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class ChainService {
    private static final long RPC_CALL_TIMEOUT = 20000;
    private static final long SYNC_RESULT_TIMEOUT = 20000;
    private static final String ORG_ID = "org1";

    /**
     * 合约调用
     */
    public ResultOuterClass.TxResponse invokeContract(ChainClient chainClient, String contractName, String method,
                                                      Map<String, byte[]> params, String languageType) throws CarbonSdkException {
        // 合约语言类型不同，参数不同
        // docker-go类型：合约方法名放在参数列表中，方法固定传invoke_contract
        if (languageType.equals(ContractLanguageTypeEnum.DOCKER_GO.getMsg())) {
            params.put("method", method.getBytes());

            method = "invoke_contract";
        }

        try {
            ResultOuterClass.TxResponse responseInfo = chainClient.invokeContract(contractName, method, null, params, RPC_CALL_TIMEOUT, SYNC_RESULT_TIMEOUT);
            if (Objects.isNull(responseInfo)) {
                log.error("合约调用失败");
                throw CarbonSdkException.of(CarbonErrorCode.INVOKE_CONTRACT_ERROR);
            }

            return responseInfo;
        } catch (Exception e) {
            throw CarbonSdkException.of(CarbonErrorCode.INVOKE_CONTRACT_ERROR);
        }
    }

    /**
     * 合约查询
     */
    public ResultOuterClass.TxResponse queryContract(ChainClient chainClient, String contractName, String methodName,
                                                     Map<String, byte[]> params, String languageType) throws CarbonSdkException {
        // 合约语言类型不同，参数不同
        // docker-go类型：合约方法名放在参数列表中，方法固定传invoke_contract
        String method;
        if (languageType.equals(ContractLanguageTypeEnum.DOCKER_GO.getMsg())) {
            method = "invoke_contract";

            params.put("method", methodName.getBytes());
        } else {
            method = methodName;
        }

        try {
            ResultOuterClass.TxResponse responseInfo = chainClient.queryContract(contractName, method, null, params, RPC_CALL_TIMEOUT);
            if (Objects.isNull(responseInfo)) {
                log.error("合约查询失败");
                throw CarbonSdkException.of(CarbonErrorCode.QUERY_CONTRACT_ERROR);
            }

            return responseInfo;
        } catch (Exception e) {
            throw CarbonSdkException.of(CarbonErrorCode.QUERY_CONTRACT_ERROR);
        }
    }
}
