package com.doreamon.treasure.net;


import android.util.Log;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Hello World
 * Date: 2019/5/20
 * Author: Cham
 */
public class LoggingInterceptor implements Interceptor {
    static String TAG = "Retrofit";
    private static final Charset UTF8 = StandardCharsets.UTF_8;
    private static final String NEW_LINE = "\n";
    private static final String SPACE = "\t\t";

    @Override
    public Response intercept(Chain chain) throws IOException {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        Request request = chain.request();

        long t1 = System.nanoTime();//请求发起的时间

        //解析请求参数
        RequestBody body = request.body();
        StringBuilder sb = new StringBuilder();
        if (body != null) {
            MediaType mediaType = body.contentType();
            if (mediaType != null) {
                sb.append("content type:");
                sb.append(mediaType.toString());
                sb.append(NEW_LINE);
            }
            Buffer buffer = new Buffer();
            body.writeTo(buffer);
            if (isPlaintext(buffer)) {
                sb.append("params:");
                sb.append(buffer.readString(UTF8));
                sb.append(NEW_LINE);
            }
        }
//        LogUtilKt.e(TAG, String.format("请求URL------%s on %s%n请求头------%s",
//                request.url(), chain.connection(), request.headers()));
        Log.i("Http",String.format("%n请求URL------%s on %s%n请求实体------%s请求头------%s",
                request.url(), chain.connection(),sb.toString(), request.headers()));
        Response response = chain.proceed(request);

        long t2 = System.nanoTime();//收到响应的时间

        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        Log.i("http",String.format("%n响应URL-------: %s %n响应数据------%s 请求用时--------%.1fms%n%s",
                response.request().url(),
                responseBody.string(),
                (t2 - t1) / 1e6d,
                response.headers()));

        return response;
    }

    static boolean isPlaintext(Buffer buffer){
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }
}
