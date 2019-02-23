package my.ssm.o2o.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import my.ssm.o2o.exception.RemoteAccessException;

/**  
 * <p>客户端工具，用于发送HTTP、HTTPS请求</p>
 * <p>Date: 2019年2月23日</p>
 * @author Wanghui    
 */  
public final class ClientUtil {
    private static final Logger logger = LoggerFactory.getLogger(ClientUtil.class);
    private ClientUtil() {}
    
    /**  
     * <p>遵循SSL安全协议的GET请求</p>  
     * @param targetUrl 目标地址
     * @param parameters 请求参数
     * @param headers 请求头信息
     * @return  响应结果
     */  
    public static String sslGet(String targetUrl, Map<String, String> parameters, Map<String, String> headers) {
        HttpsURLConnection conn = null;
        BufferedReader br = null;
        try {
            URL url = new URL(new StringBuilder(targetUrl).append(getQueryStr(parameters)).toString());
            logger.debug("sslGet [url = {}]", url.toString());
            conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(getSSLSocketFactory());
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("GET");
            if(MapUtils.isNotEmpty(headers)) {
                for(Entry<String, String> header : headers.entrySet()) {
                    conn.setRequestProperty(header.getKey(), header.getValue());
                }
            }
            conn.connect();
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder resp = new StringBuilder();
            String line;
            while(null != (line = br.readLine())) {
                resp.append(line);
            }
            String respStr = resp.toString();
            logger.debug("sslGet [respStr = {}]", respStr);
            return respStr;
        } catch (Exception e) {
            logger.error("远程访问期间产生异常", e);
            throw new RemoteAccessException("远程访问期间产生异常", e);
        } finally {
            try {
                if(br != null) {
                    br.close();
                }
            } catch(Exception e) {
                logger.error("关闭输入流时产生异常", e);
            }
            try {
                if(conn != null) {
                    conn.disconnect();
                }
            } catch(Exception e) {
                logger.error("关闭连接时产生异常", e);
            }
        }
    }
    
    private static SSLSocketFactory getSSLSocketFactory() throws NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        sslContext.init(null, new TrustManager[] {new X509TrustManagerImpl()}, new SecureRandom());
        return sslContext.getSocketFactory();
    }
    
    private static String getQueryStr(Map<String, String> requestParameters) throws UnsupportedEncodingException {
        if(MapUtils.isEmpty(requestParameters)) {
            return "";
        }
        StringBuilder queryStr = new StringBuilder();
        for(Entry<String, String> param : requestParameters.entrySet()) {
            (queryStr.length() == 0 ? queryStr.append("?") : queryStr.append("&")) 
                .append(param.getKey()).append("=").append(param.getValue());
        }
        return URLEncoder.encode(queryStr.toString(), "UTF-8");
    }
    
    private static class X509TrustManagerImpl implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}
