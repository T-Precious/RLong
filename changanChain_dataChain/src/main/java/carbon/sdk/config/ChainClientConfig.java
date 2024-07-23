package carbon.sdk.config;

import carbon.sdk.exception.CarbonSdkException;
import carbon.sdk.utils.StreamUtil;
import lombok.extern.slf4j.Slf4j;
import org.chainmaker.sdk.ChainClient;
import org.chainmaker.sdk.ChainClientException;
import org.chainmaker.sdk.ChainManager;
import org.chainmaker.sdk.RpcServiceClientException;
import org.chainmaker.sdk.config.NodeConfig;
import org.chainmaker.sdk.config.SdkConfig;
import org.chainmaker.sdk.crypto.ChainMakerCryptoSuiteException;
import org.chainmaker.sdk.utils.UtilsException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 初始化长安链客户端
 * @date 2024/3/16 14:05
 */
@Slf4j
@Configuration
public class ChainClientConfig {
    static String SDK_CONFIG = "sdk_config.yml";

    //throws UtilsException, RpcServiceClientException, ChainMakerCryptoSuiteException,
    //            ChainClientException, IOException, CarbonSdkException
    @Bean
    public ChainClient getChainClient()  {
        ChainClient chainClient=null;
        try {
            // 初始化客户端
            Yaml yaml = new Yaml();
            InputStream in = ChainClientConfig.class.getClassLoader().getResourceAsStream(SDK_CONFIG);
            log.info("sdk_config.yml路径加载成功："+in.toString());
            SdkConfig sdkConfig;
            sdkConfig = yaml.loadAs(in, SdkConfig.class);
            log.info("sdkConfig加载成功："+sdkConfig.getChainClient().getChainId());
            in.close();
            addCertToSdkConfig(sdkConfig);
            log.info("添加证书信息成功："+sdkConfig.getChainClient().getUserCrtFilePath());
            ChainManager chainManager = ChainManager.getInstance();
            log.info("ChainManager初始化成功："+chainManager.toString());
            chainClient= chainManager.getChainClient(sdkConfig.getChainClient().getChainId());
            if (chainClient == null) {
                chainClient = chainManager.createChainClient(sdkConfig);
            }
            log.info("chainClient创建成功："+chainClient.getChainId());
        }catch (UtilsException e){
            log.error("报UtilsException异常，异常信息："+e.toString());
        }catch (RpcServiceClientException e){
            log.error("报RpcServiceClientException异常，异常信息："+e.toString());
        }catch (ChainMakerCryptoSuiteException e){
            log.error("报ChainMakerCryptoSuiteException异常，异常信息："+e.toString());
        }catch (ChainClientException e){
            log.error("报ChainClientException异常，异常信息："+e.toString());
        }catch (IOException e){
            log.error("报IOException异常，异常信息："+e.toString());
        }catch (CarbonSdkException e){
            log.error("报CarbonSdkException异常，异常信息："+e.toString());
        }catch (Exception e){
            log.error("报Exception异常，异常信息："+e.toString());
        }
        return chainClient;
    }

    private void addCertToSdkConfig(SdkConfig sdkConfig) throws CarbonSdkException {
        // 添加证书信息
        org.chainmaker.sdk.config.ChainClientConfig chainClientConfig = sdkConfig.getChainClient();
        chainClientConfig.setUserKeyBytes(StreamUtil.streamToByteArray(chainClientConfig.getUserKeyFilePath()));
        chainClientConfig.setUserCrtBytes(StreamUtil.streamToByteArray(chainClientConfig.getUserCrtFilePath()));
        chainClientConfig.setUserSignKeyBytes(StreamUtil.streamToByteArray(chainClientConfig.getUserSignKeyFilePath()));
        chainClientConfig.setUserSignCrtBytes(StreamUtil.streamToByteArray(chainClientConfig.getUserSignCrtFilePath()));

        for (NodeConfig nodeConfig : sdkConfig.getChainClient().getNodes()) {
            List<byte[]> tlsCaCertList = new ArrayList<>();
            if (nodeConfig.getTrustRootPaths() != null) {
                for (String rootPath : nodeConfig.getTrustRootPaths()) {
                    tlsCaCertList.add(StreamUtil.streamToByteArray(rootPath));
                }
            }
            byte[][] tlsCaCerts = new byte[tlsCaCertList.size()][];
            tlsCaCertList.toArray(tlsCaCerts);
            nodeConfig.setTrustRootBytes(tlsCaCerts);
        }
    }
}
