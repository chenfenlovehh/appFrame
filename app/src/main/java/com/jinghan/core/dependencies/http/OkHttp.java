package com.jinghan.core.dependencies.http;

import android.content.Context;
import android.text.TextUtils;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jinghan.app.global.Constant;
import com.jinghan.core.dependencies.http.interceptor.CacheInterceptor;
import com.jinghan.core.dependencies.http.interceptor.HeaderInterceptor;
import com.jinghan.core.dependencies.http.ssl.SSLHelper;
import com.jinghan.core.helper.PermissionUtils;
import com.orhanobut.logger.Logger;
import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.RuntimeException;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author liuzeren
 * @time 2017/11/3    上午11:32
 * @mail lzr319@163.com
 */
@Singleton
public class OkHttp{

    private Context context;
    private static OkHttp instance;
    private OkHttpClient mOkHttpClient;
    private RetrofitClient mRetrofitClient;

    @Inject
    public OkHttp(Context context){
        this.context = context;
        mRetrofitClient = new RetrofitClient();
        init();
    }

    public static OkHttp getInstance(Context mContext){
        if(null == instance){
            synchronized (OkHttp.class) {
                if(null == instance) {
                    instance = new OkHttp(mContext);
                }
            }
        }

        return instance;
    }

    private void init(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder().addInterceptor(
                new HttpLoggingInterceptor(message -> Logger.i(message)).setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(Constant.HttpInfo.CONNETC_TIMEOUT,TimeUnit.SECONDS)
                .readTimeout(Constant.HttpInfo.READ_STREAM_TIMEOUT,TimeUnit.SECONDS)
                .writeTimeout(Constant.HttpInfo.WRITE_STREAM_TIMEOUT,TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool())
                .retryOnConnectionFailure(true)
                .addNetworkInterceptor(new HeaderInterceptor(context))
                .addNetworkInterceptor(new StethoInterceptor());

        if(Constant.HttpInfo.IS_CACHE) {
            builder.addNetworkInterceptor(new CacheInterceptor(context));

            if(PermissionUtils.checkStoragePermissions(context)){//是否有SD读写权限，有的话则缓存在SD中，没有话则缓存在内部存储文件中
                builder.cache(new Cache(new File(Constant.HttpInfo.CACHE_PATH), Constant.HttpInfo.CACHE_SIZE));
            }else{
                builder.cache(new Cache(new File(context.getExternalCacheDir(),"http"), Constant.HttpInfo.CACHE_SIZE));
            }
        }

        mOkHttpClient = builder.build();

        //判断是否在AppLication中配置Https证书
        if (!TextUtils.isEmpty(Constant.HttpInfo.SSL_NAME_IN_ASSETS)) {
            InputStream inStream = null;
            try {
                inStream = context.getAssets().open(Constant.HttpInfo.SSL_NAME_IN_ASSETS);

                mOkHttpClient = mOkHttpClient.newBuilder()
                        .sslSocketFactory(SSLHelper.getSslSocketFactory(new InputStream[]{inStream}, null, ""))
                        .build();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class RetrofitClient{

        private String baseUrl = Constant.HttpInfo.BASE_URL;

        public RetrofitClient setBaseUrl( String url ){
            baseUrl = url;
            return this;
        }

        public <T> T builder(Class<T> service){
            if (baseUrl == null) {
                throw new RuntimeException("baseUrl is null!");
            }
            if (service == null) {
                throw new RuntimeException("api Service is null!");
            }

            return new Retrofit.Builder()
                    .client(mOkHttpClient)
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build().create(service);
        }
    }

    public RetrofitClient getClient(){
        return mRetrofitClient;
    }
}