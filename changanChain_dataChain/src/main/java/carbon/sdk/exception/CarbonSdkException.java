package carbon.sdk.exception;


import lombok.Getter;

/**
 * @description
 * @date 2024/3/13 10:16
 */
public class CarbonSdkException extends Exception {
    @Getter
    private final ErrorCode errorCode;

    protected CarbonSdkException(ErrorCode errorCode) {
        super(errorCode.getErrorMsg());
        this.errorCode = errorCode;
    }

    protected CarbonSdkException(ErrorCode errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    public static CarbonSdkException of(ErrorCode errorCode) {
        return new CarbonSdkException(errorCode);
    }

    public static CarbonSdkException of(ErrorCode errorCode, String msg) {
        return new CarbonSdkException(errorCode, msg);
    }
}
