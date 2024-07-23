package carbon.sdk.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2024/7/4 10:20
 * @Version 1.0
 **/
@Getter
@Setter
public class GoTestParam {
    @NotBlank(message = "fileHash不能为空")
    private String fileHash;
    @NotBlank(message = "fileName不能为空")
    private String fileName;
    @NotBlank(message = "time不能为空")
    private String time;
}
