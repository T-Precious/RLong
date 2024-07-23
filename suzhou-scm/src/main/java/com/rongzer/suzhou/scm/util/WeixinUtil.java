package com.rongzer.suzhou.scm.util;

import com.alibaba.fastjson.JSONObject;
import com.rongzer.util.HttpClient;
import com.rongzer.suzhou.scm.enums.LoginExceptionEnum;
import com.rongzer.suzhou.scm.exception.LoginException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: luolingling
 * Date: 2022/1/6
 * Description: 请求微信服务端工具类
 */
@Slf4j
public class WeixinUtil {

    /**
     * 请求微信小程序服务端获取access_token
     * @param appid
     * @param secret
     * @return access_token
     * @throws Exception
     */
    private static String getAccessToken(String appid, String secret) {

        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret;

        String httpGet = httpGet(url);

        JSONObject object = JSONObject.parseObject(httpGet);

        if (object.getIntValue("errcode") != 0) {
            throw new LoginException(LoginExceptionEnum.GET_ACCESS_TOKEN_FAIL.getErrorMsg());
        }

        return object.getString("access_token");
    }
    public static void getminiqrQr(String appid, String secret){
        String WxCode_URL = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=ACCESS_TOKEN";//小程序密钥
//        String fileName = "code1.png";
        try {
            String accessToken = getAccessToken(appid, secret);
            String wxCodeURL = WxCode_URL.replace("ACCESS_TOKEN", accessToken);
            URL url = new URL(wxCodeURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
            // 注意小程序页面如果不存在，则注释掉page参数
            paramJson.put("path", "pages/server/serverIP");
//            paramJson.put("scene", "deviceId=18:84:C1:0F:15:43");
            paramJson.put("width", 430);
//            paramJson.put("is_hyaline", true);
//            paramJson.put("auto_color", true);
            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            File file = new File("C:\\rz\\tmp\\zhuhai\\1.png");
            if (!file.exists()){
                file.createNewFile();
            }
            OutputStream os = new FileOutputStream(file);
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1) {
                os.write(arr, 0, len);
                os.flush();
            }
            os.close();
        } catch (Exception e) {
            log.error("===> error : ", e);
        }
    }
    public static void getUnlimitedQRCode(String appid, String secret){
        String accessToken = getAccessToken(appid, secret);
        RestTemplate rest = new RestTemplate();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken;
            Map<String,Object> param = new HashMap<>();
            //因不支持%，中文无法使用 urlencode 处理，请使用其他编码方式
//            param.put("scene", "18:84:C1:0F:15:43&linaro-ali");
            param.put("scene", "18:84:C1:0F:1B:4F&ge215");
            param.put("page", "pages/server/serverIP");
            //检查page 是否存在，为 true 时 page 必须是已经发布的小程序存在的页面（否则报错）；为 false 时允许小程序未发布或者 page 不存在
            param.put("check_path", false);
            //正式版为 "release"，体验版为 "trial"，开发版为 "develop"。默认是正式版
            param.put("env_version", "release");
            param.put("width", 280);
            param.put("auto_color", false);
            Map<String,Object> line_color = new HashMap<>();
            line_color.put("r", 0);
            line_color.put("g", 0);
            line_color.put("b", 0);
            param.put("line_color", line_color);
            param.put("is_hyaline",true);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            HttpEntity requestEntity = new HttpEntity(param, headers);
            ResponseEntity<byte[]> entity =
                    rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
            byte[] result = entity.getBody();
            inputStream = new ByteArrayInputStream(result);
            //我将图片存储到了服务器中的ActQRImage文件夹下
            File file = new File("C:\\rz\\tmp\\suzhou\\RZ20230707RZEC090.png");
            if (!file.exists()){
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            outputStream.write(result);
            outputStream.flush();
        }catch (Exception e){
            System.out.println("调用小程序生成微信永久小程序码URL接口异常"+e);
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void getlimitedreateQRCode(String appid, String secret){
        String accessToken = getAccessToken(appid, secret);
        RestTemplate rest = new RestTemplate();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            String url = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=" + accessToken;
            Map<String,Object> param = new HashMap<>();
            param.put("path", "pages/server/serverIP?deviceId=90:E4:68:EB:07:D2&deviceName=linaro-RBCSN227");
            param.put("width", 280);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            HttpEntity requestEntity = new HttpEntity(param, headers);
            ResponseEntity<byte[]> entity =
                    rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
            byte[] result = entity.getBody();
            inputStream = new ByteArrayInputStream(result);
            //我将图片存储到了服务器中的ActQRImage文件夹下
            File file = new File("C:\\rz\\tmp\\zhuhai\\9.png");
            if (!file.exists()){
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            outputStream.write(result);
            outputStream.flush();
        }catch (Exception e){
            System.out.println("调用小程序生成微信永久小程序码URL接口异常"+e);
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 请求微信小程序服务端获取手机号
     * @param code
     * @return 手机号
     */
    public static String getPhoneNumber(String code, String appid, String secret) {

        String accessToken = getAccessToken(appid, secret);

        String url = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + accessToken;

        JSONObject param = new JSONObject();
        param.put("code", code);

        String post = HttpClient.httpPost(param.toJSONString(), url, "application/json", 10000);
        log.info("post请求 {} 返回{}", url, post);

        JSONObject resultObj = JSONObject.parseObject(post);

        //成功：0
        //系统繁忙，此时请开发者稍候再试：-1
        //不合法的code：40029
        int errcode = resultObj.getIntValue("errcode");

        if (errcode == 0) {
            JSONObject phoneInfo = resultObj.getJSONObject("phone_info");
            return phoneInfo.getString("purePhoneNumber");
        }
        if (errcode == -1) {
            throw new LoginException(LoginExceptionEnum.WX_SYSTEM_BUSY.getErrorMsg());
        }
        if (errcode == 40029) {
            throw new LoginException(LoginExceptionEnum.VALID_PHONE_CODE.getErrorMsg());
        } else {
            throw new LoginException(LoginExceptionEnum.UNKNOWN_ERROR.getErrorMsg());
        }
    }

    /**
     * 请求微信小程序校验登录凭证
     * @param code
     * @return 属性  类型  说明
            openid  string  用户唯一标识
            session_key string  会话密钥
            unionid string  用户在开放平台的唯一标识符，若当前小程序已绑定到微信开放平台帐号下会返回，详见 UnionID 机制说明。
            errcode number  错误码
            errmsg  string  错误信息
     */
    public static JSONObject code2Session(String code, String appid, String secret) {

        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";

        String get = httpGet(url);

        JSONObject resultObj = JSONObject.parseObject(get);

        //-1  系统繁忙，此时请开发者稍候再试
        // 0   请求成功
        // 40029   code 无效
        // 45011   频率限制，每个用户每分钟100次
        // 40226   高风险等级用户，小程序登录拦截
        int errcode = resultObj.getIntValue("errcode");

        if (errcode == 0) {
            return resultObj;
        }
        if (errcode == -1) {
            throw new LoginException(LoginExceptionEnum.WX_SYSTEM_BUSY.getErrorMsg());
        }
        if (errcode == 45011) {
            throw new LoginException(LoginExceptionEnum.OVER_LIMIT.getErrorMsg());
        }
        if (errcode == 40226) {
            throw new LoginException(LoginExceptionEnum.DANGER_USER.getErrorMsg());
        }
        if (errcode == 40029) {
            throw new LoginException(LoginExceptionEnum.VALID_LOGIN_CODE.getErrorMsg());
        }
        if (errcode == 40163) {
            throw new LoginException(LoginExceptionEnum.CODE_BEEN_USED.getErrorMsg());
        } else {
            throw new LoginException(LoginExceptionEnum.UNKNOWN_ERROR.getErrorMsg());
        }
    }


    /**
     * 发送GET请求
     * @param url
     * @return
     * @throws Exception
     */
    public static String httpGet(String url) {

        HttpGet get = new HttpGet(url);

        Scanner scanner = null;
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(get);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("GET请求响应异常，状态码：" + response.getStatusLine().getStatusCode());
            }

            scanner = new Scanner(response.getEntity().getContent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        StringBuilder responseContent = new StringBuilder();

        while (scanner.hasNext()) {
            responseContent.append(scanner.next());
        }

        log.info("请求{}，响应结果：{}", url, responseContent.toString());
        return responseContent.toString();
    }

    public static void main(String[] args) throws Exception {

        // System.out.println(getAccessToken("wx582d937fd976f040_1", "41dc4ead266f2154f9b994efb8e62be4"));

        // String token = "52_L3GmzlYmbkrpWrxyrjl4Vui49YrwdFGNAL3JCFxyVJ8zwfCYJsOndsF61nkX3wcIO3uCHCPG0-cwvsUVgPntE0VdL3qEd85qEGYPzBXp6uzcpkwO1uL9YZiQcXzN30RPrlM5ldfFFImWfEcpVIKaACAQDF";
        //
        // System.out.println(getPhoneNumber(token, "23443"));
        getUnlimitedQRCode("wxbe61190768831e75","54597ea2966c3990a36a55e8a3b62963");
//        getlimitedreateQRCode("wxbe61190768831e75","54597ea2966c3990a36a55e8a3b62963");
//        getminiqrQr("wxbe61190768831e75","54597ea2966c3990a36a55e8a3b62963");
//        System.out.println(code2Session("0732OL000k1Z4N1a5Z200pmQEH12OL0N", "wx582d937fd976f040", "41dc4ead266f2154f9b994efb8e62be4"));

    }
}