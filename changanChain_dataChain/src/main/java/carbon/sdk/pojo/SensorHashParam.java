package carbon.sdk.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2024/7/9 14:36
 * @Version 1.0
 **/
@Getter
@Setter
public class SensorHashParam {
    @NotBlank(message = "sensorBoxHash不能为空")
    private String sensorBoxHash;
}
