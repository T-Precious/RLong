package carbon.sdk.utils;

import carbon.sdk.enums.CarbonErrorCode;
import carbon.sdk.exception.CarbonSdkException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @description
 * @date 2022/6/1 21:02
 */
public class StreamUtil {
    private StreamUtil() {
    }

    public static byte[] streamToByteArray(String resourcePath) throws CarbonSdkException {
        try {
            Resource resource = new ClassPathResource(resourcePath);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            InputStream input = resource.getInputStream();
            byte[] buffer = new byte[1024 * 4];
            int n;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }
            return output.toByteArray();
        } catch (IOException e) {
            throw CarbonSdkException.of(CarbonErrorCode.INPUT_STREAM_TO_BYTE_ARRAY_ERROR);
        }
    }
}
