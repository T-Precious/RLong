package carbon.sdk.dto;

import carbon.sdk.enums.CarbonErrorCode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseData<T> {
    public static final String SUCCESS = "success";
    public static final int SUCCESS_CODE = 200;
    public static final int FAILURE_CODE = 500;
    private static final long serialVersionUID = -4272342803400464446L;
    /**
     * 状态码
     */
    private int code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 数据
     */
    @JsonSerialize
    private T data;

    /**
     * 交易id
     */
    private String txId;

    public ResponseData() {
    }

    public ResponseData(T data, String txId) {
        this.code = SUCCESS_CODE;
        this.success = true;
        this.data = data;
        this.txId = txId;
    }

    public ResponseData(int code, String message, T data, String txId) {
        this.code = code;
        this.message = message;
        this.success = code == SUCCESS_CODE;
        this.data = data;
        this.txId = txId;
    }

    public ResponseData(int code, boolean success, String message, String txId) {
        this.code = code;
        this.message = message;
        this.success = success;
        this.txId = txId;
    }

    public static <T> ResponseData<T> ok(T data) {
        return new ResponseData<>(200, "", data, "");
    }

    public static <T> ResponseData<T> ok(T data, String txId) {
        return new ResponseData<>(200, "", data, txId);
    }

    public static <T> ResponseData<T> waitForResult(T data, String txId) {
        return new ResponseData<>(201, "", data, txId);
    }

    public static <T> ResponseData<T> okMsg(String msg, String txId) {
        return new ResponseData<>(200, msg, null, txId);
    }

    public static <T> ResponseData<T> err(String message, String txId) {
        return new ResponseData<>(500, message, null, txId);
    }

    public static <T> ResponseData<T> of(int code, String message, String txId) {
        return new ResponseData<>(code, message, null, txId);
    }

    public static <T> ResponseData<T> of(int code, String message, T data, String txId) {
        return new ResponseData<>(code, message, data, txId);
    }

    public static <T> ResponseData<T> fail(CarbonErrorCode errorCode) {
        return new ResponseData<>(errorCode.getCode(), errorCode.getErrorMsg(), null, "");
    }

    public static <T> ResponseData<T> fail(CarbonErrorCode errorCode, String msg, String txId) {
        return new ResponseData<>(errorCode.getCode(), msg, null, txId);
    }
}
