package carbon.sdk.constants;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2024/7/9 14:20
 * @Version 1.0
 **/
public class SensorContractConst {
    private SensorContractConst(){
    }
    /**
     * 合约名称
     */
    public static final String SENSOR_CONTRACT_NAME = "sensorBox";

    /**
     * 添加传感器告警信息方法
     */
    public static final String ADD_SENSOR_DATA = "cochainSensorBoxData";

    /**
     * 查询传感器告警信息方法
     */
    public static final String FIND_SENSOR_DATA = "findBySensorBoxHash";

    /**
     * 删除传感器告警信息方法
     */
    public static final String DELETE_SENSOR_DATA = "deleteBySensorBoxHash";
}
