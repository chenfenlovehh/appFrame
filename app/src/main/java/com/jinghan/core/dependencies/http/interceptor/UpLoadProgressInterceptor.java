package com.jinghan.core.dependencies.http.interceptor;

import com.jinghan.core.dependencies.http.model.UploadProgressListener;
import com.orhanobut.logger.Logger;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

import java.io.IOException;

/**
 * 文件上传进度拦截器
 * @author liuzeren
 * @time 2017/11/3    下午5:18
 * @mail lzr319@163.com
 */
public class UpLoadProgressInterceptor implements Interceptor {

    private UploadProgressListener mUploadListener;

    public UpLoadProgressInterceptor(UploadProgressListener mUploadListener) {
        this.mUploadListener = mUploadListener;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody body = request.body();

        if (null == body) return chain.proceed(request);

        Request build = request.newBuilder()
                .method(request.method(),
                        new UploadProgressRequestBody(body))
                .build();
        return chain.proceed(build);
    }

    class UploadProgressRequestBody extends RequestBody {

        public UploadProgressRequestBody(RequestBody mRequestBody) {
            this.mRequestBody = mRequestBody;
        }

        private RequestBody mRequestBody;

        private CountingSink mCountingSink = null;

        @Override
        public MediaType contentType() {
            return mRequestBody.contentType();
        }

        @Override
        public long contentLength() {
            try {
                return mRequestBody.contentLength();
            } catch (IOException e) {
                Logger.i(e.getMessage());
                return -1;
            }

        }

        @Override
        public void writeTo(BufferedSink sink) throws IOException {
            BufferedSink bufferedSink;

            mCountingSink = new CountingSink(sink);
            bufferedSink = Okio.buffer(mCountingSink);

            mRequestBody.writeTo(bufferedSink);
            bufferedSink.flush();
        }

        class CountingSink extends ForwardingSink {

            public CountingSink(Sink delegate) {
                super(delegate);
            }

            private long bytesWritten = 0;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                bytesWritten += byteCount;
                mUploadListener.onProgress(bytesWritten, contentLength());
            }
        }
    }
}