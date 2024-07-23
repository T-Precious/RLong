package carbon.sdk.exception;

import java.io.Serializable;

/**
 * @description
 * @date 2024/3/13 10:17
 */
public interface ErrorCode extends Serializable {
    /**
     * 获取错误码文字描述
     * @return 错误码文字描述
     */
    String getErrorMsg();
}
