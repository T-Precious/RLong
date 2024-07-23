package carbon.sdk.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2024/7/9 14:15
 * @Version 1.0
 **/
@Getter
@Setter
public class SensorCoChainParam {

    @NotBlank(message = "sensorBoxHash不能为空")
    private String sensorBoxHash;

    @NotBlank(message = "deviceId不能为空")
    private String deviceId;

    @NotBlank(message = "sensorAlarmInfor不能为空")
    private String sensorAlarmInfor;

    private String time;
}
