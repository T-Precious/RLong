package carbon.sdk.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2024/7/10 9:14
 * @Version 1.0
 **/
@Getter
@Setter
public class VideoHashParam {
    @NotBlank(message = "videoBoxHash不能为空")
    private String videoBoxHash;
}
