package carbon.sdk.service;

import carbon.sdk.constants.SensorContractConst;
import carbon.sdk.constants.ServiceConst;
import carbon.sdk.constants.VideoContractConst;
import carbon.sdk.dto.ResponseData;
import carbon.sdk.enums.CarbonErrorCode;
import carbon.sdk.enums.ContractLanguageTypeEnum;
import carbon.sdk.pojo.VideoCoChainParam;
import carbon.sdk.pojo.VideoHashParam;
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
 * @Date 2024/7/10 9:15
 * @Version 1.0
 **/
@Slf4j
@Service
public class VideoBoxService {
    @Resource
    private ChainClient chainClient;
    @Resource
    private ChainService chainService;

    public ResponseData<String> cochainVideoBoxData(VideoCoChainParam videoCoChainParam) {
        try {
            // 构造合约调用参数
            Map<String, byte[]> params = new HashMap<>();
            params.put("videoBoxHash", videoCoChainParam.getVideoBoxHash().getBytes());
            params.put("boardId", videoCoChainParam.getBoardId().getBytes());
            params.put("videoAlarmInfor", videoCoChainParam.getVideoAlarmInfor().getBytes());
            params.put("time", LocalDateUtil.nowDate(LocalDateUtil.DATE_TIME_FORMAT_2).getBytes());

            ResultOuterClass.TxResponse videoResponseInfo = chainService.invokeContract(chainClient, VideoContractConst.VIDEO_CONTRACT_NAME,
                    VideoContractConst.ADD_VIDEO_DATA, params, ContractLanguageTypeEnum.DOCKER_GO.getMsg());
            if (videoResponseInfo.getCodeValue() != 0 || videoResponseInfo.getContractResult().getCode() != 0) {
                log.error(videoResponseInfo.getContractResult().getResult().toStringUtf8());
                return ResponseData.fail(CarbonErrorCode.ADD_VIDEO_FAIL, videoResponseInfo.getContractResult().getResult().toStringUtf8(), videoResponseInfo.getTxId());
            }
            return ResponseData.ok(ServiceConst.SUCCESS, videoResponseInfo.getContractResult().getResult().toStringUtf8());
        } catch (Exception e) {
            log.error("添加视频盒子告警信息失败");
            return ResponseData.fail(CarbonErrorCode.ADD_VIDEO_FAIL);
        }
    }

    public ResponseData<String> findByVideoBoxHash(VideoHashParam videoHashParam) {
        try {
            // 构造合约调用参数
            Map<String, byte[]> params = new HashMap<>();
            params.put("videoBoxHash", videoHashParam.getVideoBoxHash().getBytes());

            ResultOuterClass.TxResponse videoResponseInfo = chainService.queryContract(chainClient, VideoContractConst.VIDEO_CONTRACT_NAME,
                    VideoContractConst.FIND_VIDEO_DATA, params, ContractLanguageTypeEnum.DOCKER_GO.getMsg());
            if (videoResponseInfo.getCodeValue() != 0 || videoResponseInfo.getContractResult().getCode() != 0) {
                log.error(videoResponseInfo.getContractResult().getResult().toStringUtf8());
                return ResponseData.fail(CarbonErrorCode.FIND_VIDEO_FAIL, videoResponseInfo.getContractResult().getResult().toStringUtf8(), videoResponseInfo.getTxId());
            }
            return ResponseData.ok(ServiceConst.SUCCESS, videoResponseInfo.getContractResult().getResult().toStringUtf8());
        } catch (Exception e) {
            log.error("通过videoBoxHash查询视频盒子告警信息失败");
            return ResponseData.fail(CarbonErrorCode.FIND_VIDEO_FAIL);
        }
    }

    public ResponseData<String> deleteByVideoBoxHash(VideoHashParam videoHashParam) {
        try {
            // 构造合约调用参数
            Map<String, byte[]> params = new HashMap<>();
            params.put("videoBoxHash", videoHashParam.getVideoBoxHash().getBytes());

            ResultOuterClass.TxResponse videoResponseInfo = chainService.invokeContract(chainClient, VideoContractConst.VIDEO_CONTRACT_NAME,
                    VideoContractConst.DELETE_VIDEO_DATA, params, ContractLanguageTypeEnum.DOCKER_GO.getMsg());
            if (videoResponseInfo.getCodeValue() != 0 || videoResponseInfo.getContractResult().getCode() != 0) {
                log.error(videoResponseInfo.getContractResult().getResult().toStringUtf8());
                return ResponseData.fail(CarbonErrorCode.DELETE_VIDEO_FAIL, videoResponseInfo.getContractResult().getResult().toStringUtf8(), videoResponseInfo.getTxId());
            }
            return ResponseData.ok(ServiceConst.SUCCESS, videoResponseInfo.getContractResult().getResult().toStringUtf8());
        } catch (Exception e) {
            log.error("通过videoBoxHash删除视频盒子告警信息失败");
            return ResponseData.fail(CarbonErrorCode.DELETE_VIDEO_FAIL);
        }
    }
}
