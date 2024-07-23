package carbon.sdk.constants;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2024/7/10 9:05
 * @Version 1.0
 **/
public class VideoContractConst {
    private VideoContractConst(){}
    /**
     * 合约名称
     */
    public static final String VIDEO_CONTRACT_NAME = "videoBox";

    /**
     * 添加视频盒子告警信息方法
     */
    public static final String ADD_VIDEO_DATA = "cochainVideoBoxData";

    /**
     * 查询视频盒子告警信息方法
     */
    public static final String FIND_VIDEO_DATA = "findByVideoBoxHash";

    /**
     * 删除视频盒子告警信息方法
     */
    public static final String DELETE_VIDEO_DATA = "deleteByVideoBoxHash";
}
