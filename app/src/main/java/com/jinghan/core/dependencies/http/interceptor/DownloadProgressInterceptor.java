package com.jinghan.core.dependencies.http.interceptor;

import com.jinghan.core.dependencies.http.model.DownloadProgressListener;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

import java.io.IOException;

/**
 * 文件下载拦截器
 * @author liuzeren
 * @time 2017/11/3    下午5:21
 * @mail lzr319@163.com
 */
public class DownloadProgressInterceptor implements Interceptor {

    private DownloadProgressListener listener;

    public DownloadProgressInterceptor(DownloadProgressListener listener){
        this.listener = listener;
    }

    @Override public Response intercept(Interceptor.Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        ResponseBody body = originalResponse.body();

        if(null == body){ return originalResponse;}

        return originalResponse.newBuilder()
                .body(new DownloadProgressResponseBody(body))
                .build();
    }

    class DownloadProgressResponseBody extends ResponseBody {

        public DownloadProgressResponseBody(ResponseBody responseBody){
            this.responseBody = responseBody;
        }

        private ResponseBody responseBody;

        private BufferedSource bufferedSource = null;

        @Override public MediaType contentType() {
            return responseBody.contentType();
        }

        @Override public long contentLength() {
            return responseBody.contentLength();
        }

        @Override public BufferedSource source() {
            if (bufferedSource == null) {
                bufferedSource = Okio.buffer(source(responseBody.source()));
            }
            return bufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) {
                private long totalBytesRead = 0L;

                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    // read() returns the number of bytes read, or -1 if this source is exhausted.
                    totalBytesRead += (bytesRead != -1L? bytesRead : 0);

                    listener.update(totalBytesRead, responseBody.contentLength(), bytesRead == -1L);
                    return bytesRead;
                }
            };

        }
    }
}