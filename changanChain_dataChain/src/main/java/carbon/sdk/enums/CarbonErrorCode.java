package carbon.sdk.enums;


import carbon.sdk.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description
 * @date 2024/3/13 10:19
 */
@Getter
@AllArgsConstructor
public enum CarbonErrorCode implements ErrorCode {
    /**
     * 合约调用失败
     */
    INVOKE_CONTRACT_ERROR(10001, "合约调用失败!"),
    /**
     * 合约查询失败
     */
    QUERY_CONTRACT_ERROR(10002, "合约查询失败!"),
    /**
     * 初始化客户端失败
     */
    CREATE_CLIENT_USER_ERROR(10003, "构造用户失败!"),
    /**
     * 初始化客户端失败
     */
    INIT_CLIENT_ERROR(10004, "初始化客户端失败!"),
    /**
     * inputStream转byte数组异常
     */
    INPUT_STREAM_TO_BYTE_ARRAY_ERROR(10005, "inputStream转byte数组异常!"),

    /**
     * 添加测试信息失败
     */
    ADD_CARBON_FACTOR_FAIL(10006, "添加测试信息失败"),

    /**
     * 添加传感器告警信息失败
     */
    ADD_SENSOR_FAIL(20006, "添加传感器告警信息失败"),

    /**
     * 通过sensorBoxHash查询传感器告警信息失败
     */
    FIND_SENSOR_FAIL(20007, "通过sensorBoxHash查询传感器告警信息失败"),

    /**
     * 通过sensorBoxHash删除传感器告警信息失败
     */
    DELETE_SENSOR_FAIL(20008, "通过sensorBoxHash删除传感器告警信息失败"),
    /**
     * 添加视频盒子告警信息失败
     */
    ADD_VIDEO_FAIL(30006, "添加视频盒子告警信息失败"),

    /**
     * 通过videoBoxHash查询视频盒子警信息失败
     */
    FIND_VIDEO_FAIL(30007, "通过videoBoxHash查询视频盒子警信息失败"),

    /**
     * 通过videoBoxHash删除视频盒子告警信息失败
     */
    DELETE_VIDEO_FAIL(30008, "通过videoBoxHash删除视频盒子告警信息失败"),
    ;

    final int code;

    final String msg;

    @Override
    public String getErrorMsg() {
        return this.msg;
    }

}
