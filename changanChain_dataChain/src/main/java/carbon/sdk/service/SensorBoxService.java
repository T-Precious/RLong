package carbon.sdk.service;

import carbon.sdk.constants.SensorContractConst;
import carbon.sdk.constants.ServiceConst;
import carbon.sdk.dto.ResponseData;
import carbon.sdk.enums.CarbonErrorCode;
import carbon.sdk.enums.ContractLanguageTypeEnum;
import carbon.sdk.pojo.SensorCoChainParam;
import carbon.sdk.pojo.SensorHashParam;
import carbon.sdk.utils.LocalDateUtil;
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
 * @Date 2024/7/9 14:25
 * @Version 1.0
 **/
@Slf4j
@Service
public class SensorBoxService {
    @Resource
    private ChainClient chainClient;
    @Resource
    private ChainService chainService;

    public ResponseData<String> cochainSensorBoxData(SensorCoChainParam sensorCoChainParam) {
        try {
            // 构造合约调用参数
            Map<String, byte[]> params = new HashMap<>();
            params.put("sensorBoxHash", sensorCoChainParam.getSensorBoxHash().getBytes());
            params.put("deviceId", sensorCoChainParam.getDeviceId().getBytes());
            params.put("sensorAlarmInfor", sensorCoChainParam.getSensorAlarmInfor().getBytes());
            params.put("time", LocalDateUtil.nowDate(LocalDateUtil.DATE_TIME_FORMAT_2).getBytes());

            ResultOuterClass.TxResponse sensorResponseInfo = chainService.invokeContract(chainClient, SensorContractConst.SENSOR_CONTRACT_NAME,
                    SensorContractConst.ADD_SENSOR_DATA, params, ContractLanguageTypeEnum.DOCKER_GO.getMsg());
            if (sensorResponseInfo.getCodeValue() != 0 || sensorResponseInfo.getContractResult().getCode() != 0) {
                log.error(sensorResponseInfo.getContractResult().getResult().toStringUtf8());
                return ResponseData.fail(CarbonErrorCode.ADD_SENSOR_FAIL, sensorResponseInfo.getContractResult().getResult().toStringUtf8(), sensorResponseInfo.getTxId());
            }
            return ResponseData.ok(ServiceConst.SUCCESS, sensorResponseInfo.getContractResult().getResult().toStringUtf8());
        } catch (Exception e) {
            log.error("添加传感器告警信息失败");
            return ResponseData.fail(CarbonErrorCode.ADD_SENSOR_FAIL);
        }
    }

    public ResponseData<String> findBySensorBoxHash(SensorHashParam sensorHashParam) {
        try {
            // 构造合约调用参数
            Map<String, byte[]> params = new HashMap<>();
            params.put("sensorBoxHash", sensorHashParam.getSensorBoxHash().getBytes());

            ResultOuterClass.TxResponse sensorResponseInfo = chainService.queryContract(chainClient, SensorContractConst.SENSOR_CONTRACT_NAME,
                    SensorContractConst.FIND_SENSOR_DATA, params, ContractLanguageTypeEnum.DOCKER_GO.getMsg());
            if (sensorResponseInfo.getCodeValue() != 0 || sensorResponseInfo.getContractResult().getCode() != 0) {
                log.error(sensorResponseInfo.getContractResult().getResult().toStringUtf8());
                return ResponseData.fail(CarbonErrorCode.FIND_SENSOR_FAIL, sensorResponseInfo.getContractResult().getResult().toStringUtf8(), sensorResponseInfo.getTxId());
            }
            return ResponseData.ok(ServiceConst.SUCCESS, sensorResponseInfo.getContractResult().getResult().toStringUtf8());
        } catch (Exception e) {
            log.error("通过sensorBoxHash查询传感器告警信息失败");
            return ResponseData.fail(CarbonErrorCode.FIND_SENSOR_FAIL);
        }
    }

    public ResponseData<String> deleteBySensorBoxHash(SensorHashParam sensorHashParam) {
        try {
            // 构造合约调用参数
            Map<String, byte[]> params = new HashMap<>();
            params.put("sensorBoxHash", sensorHashParam.getSensorBoxHash().getBytes());

            ResultOuterClass.TxResponse sensorResponseInfo = chainService.invokeContract(chainClient, SensorContractConst.SENSOR_CONTRACT_NAME,
                    SensorContractConst.DELETE_SENSOR_DATA, params, ContractLanguageTypeEnum.DOCKER_GO.getMsg());
            if (sensorResponseInfo.getCodeValue() != 0 || sensorResponseInfo.getContractResult().getCode() != 0) {
                log.error(sensorResponseInfo.getContractResult().getResult().toStringUtf8());
                return ResponseData.fail(CarbonErrorCode.DELETE_SENSOR_FAIL, sensorResponseInfo.getContractResult().getResult().toStringUtf8(), sensorResponseInfo.getTxId());
            }
            return ResponseData.ok(ServiceConst.SUCCESS, sensorResponseInfo.getContractResult().getResult().toStringUtf8());
        } catch (Exception e) {
            log.error("通过sensorBoxHash删除传感器告警信息失败");
            return ResponseData.fail(CarbonErrorCode.DELETE_SENSOR_FAIL);
        }
    }
}
