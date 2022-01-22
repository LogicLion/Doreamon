package com.example.doreamon.net;


import com.example.doreamon.base.App;
import com.example.doreamon.constant.ConstKt;
import com.example.doreamon.entity.User;
import com.example.doreamon.global.UserInfoData;
import com.example.doreamon.utils.AppUtil;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author wzh
 * @date 2021/12/13
 */
class RetrofitFactory {


    public static NetService createRetrofit() {
        long time=System.currentTimeMillis();

        String token = "";
        User user = UserInfoData.INSTANCE.getValue();
        String finalToken = token;
        Interceptor defaultInterceptor = chain -> {
            Request request = chain.request()
                    .newBuilder()
                    .header("User-Agent", "com.fenzhidao.student"+"/"+ AppUtil.getVersionCode())
//                    .header("X-Authorization-Token", "Bearer-nil")
                    .header("timestamp", (time/1000)+"")
//                    .header("protocol", AppUtil.MD5Encode32("14421feb1c9ea719b94bb651bd2667affenzhidao"+"["+"Bearer-nil"+time/1000+"]"+"14421feb1c9ea719b94bb651bd2667affenzhidao"))
                    .header("device", "androidPhone")
                    .header("app-version", AppUtil.getVersionCode())
                    .addHeader("token", finalToken)
                    .build();
            return chain.proceed(request);
        };

        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//            @Override
//            public void log(String message) {
//            }
//        });
//
//
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        LoggingInterceptor loggingInterceptor = new LoggingInterceptor();
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(defaultInterceptor)
                .addInterceptor(loggingInterceptor)
                .readTimeout(ConstKt.HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(ConstKt.HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .hostnameVerifier(getHostnameVerifier())
                .build();
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(ConstKt.getBASE_URL())
                .client(httpClient)
                .addConverterFactory(gsonConverterFactory)
                .build();
        return retrofit.create(NetService.class);
    }

    public static SSLSocketFactory getSSLSocketFactory() throws Exception {
        //创建一个不验证证书链的证书信任管理器。
        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) throws CertificateException {
            }
            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) throws CertificateException {
            }
            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[0];
            }
        }};

        //Install the all-trusting trust manager
        final SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts,
                new java.security.SecureRandom());
        //Create an ssl socket factory with our all-trusting manager
        return sslContext
                .getSocketFactory();
    }


    //使用自定义SSLSocketFactory
    private static void onHttps(OkHttpClient.Builder builder) {
        try {
            builder.sslSocketFactory(getSSLSocketFactory()).hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 不校验域名
     * @return
     */
    public static HostnameVerifier getHostnameVerifier() {
        HostnameVerifier   hostnameVerifier= new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        return hostnameVerifier;
    }



}
