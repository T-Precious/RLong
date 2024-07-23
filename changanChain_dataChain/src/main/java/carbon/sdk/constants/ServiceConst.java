package carbon.sdk.constants;

/**
 * @description 链常量
 * @date 2024/3/20 17:39
 */
public class ServiceConst {
    private ServiceConst() {
    }

    public static final String SUCCESS = "success";

    /**
     * 链id
     */
    public static final String CHAIN_ID = "chain1";

    /**
     * 组织id
     */
    public static final String ORG_ID = "org1";

    /**
     * 管理台服务地址
     */
    public static final String MANAGE_BACKEND_URL = "http://36.112.40.10:41120/";

    /**
     * 服务端地址
     */
    public static final String SERVICE_CALLBACK_URL = "http://localhost:9000/mrvs/service/manage/blockchain/message";

    /**
     * 区块链浏览器地址
     */
    public static final String EXPLORER_URL = "http://36.112.40.10:41140/";
}
