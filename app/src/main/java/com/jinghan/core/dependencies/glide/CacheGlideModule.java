package com.jinghan.core.dependencies.glide;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;
import com.jinghan.app.global.Constant;
import com.jinghan.core.helper.PermissionUtils;
import java.io.File;

/**
 * 自定义glide的缓存机制
 * @author liuzeren
 * @time 2017/11/6    上午9:55
 * @mail lzr319@163.com
 */
public class CacheGlideModule implements GlideModule {

    @Override public void applyOptions(Context context, GlideBuilder builder) {
        customMemoryCache(context, builder);
        diskCache(context,builder);
    }

    @Override public void registerComponents(Context context, Glide glide) {
        // nothing to do here
    }

    /**
     * 自定义内存缓存
     */
    private void customMemoryCache(Context context, GlideBuilder builder) {
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int)(1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int)(1.2 * defaultBitmapPoolSize);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));
    }

    private void diskCache(Context context,GlideBuilder builder) {
        if(PermissionUtils.checkStoragePermissions(context)) {//如果没有SD卡读写权限，就无法在SD卡中缓存
            builder.setDiskCache(
                    new DiskLruCacheFactory(Constant.GlideInfo.CACHE_PATH, Constant.GlideInfo.CACHE_SIZE)
            );
        }else{
            builder.setDiskCache(new DiskLruCacheFactory(new File(context.getCacheDir(),"glide").getAbsolutePath(), Constant.GlideInfo.CACHE_SIZE));
        }
    }
}