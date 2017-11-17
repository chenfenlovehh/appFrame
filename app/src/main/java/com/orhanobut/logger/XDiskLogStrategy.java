package com.orhanobut.logger;

import android.os.Looper;

/**
 * @author liuzeren
 * @time 2017/11/16    下午3:30
 * @mail lzr319@163.com
 */
public class XDiskLogStrategy extends DiskLogStrategy {
    public XDiskLogStrategy(Looper looper, String folder, int maxFileSize){
        super(new DiskLogStrategy.WriteHandler(looper,folder,maxFileSize));
    }
}
