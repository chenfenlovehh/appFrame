package com.jinghan.core.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * @author liuzeren
 * @time 2017/11/17    下午2:07
 * @mail lzr319@163.com
 */
public class BarHelper {

    /**
     * 全屏,并且隐藏导航栏
     * */
    public static void fullScreen(Activity aty){
        View decorView = aty.getWindow().getDecorView();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if ((getHasVirtualKey(aty) - getNoHasVirtualKey(aty)) > 0) {
                int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
                decorView.setSystemUiVisibility(uiOptions);
            } else {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }

            if (Build.VERSION.SDK_INT >= 21) {
                aty.getWindow().setNavigationBarColor(Color.TRANSPARENT);
                aty.getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        } else {
            aty.getWindow().addFlags(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    /**
     * * 显示或隐藏StatusBar
     *
     * @param enable false 显示，true 隐藏
     */
    protected void hideStatusBar(Activity aty,Boolean enable) {
        WindowManager.LayoutParams p = aty.getWindow().getAttributes();
        if (enable)
        //|=：或等于，取其一
        {
            p.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        } else
        //&=：与等于，取其二同时满足，     ~ ： 取反
        {
            p.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        aty.getWindow().setAttributes(p);
        aty.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }


    private static int getHasVirtualKey(Activity aty) {
        int dpi = 0;
        Display display = aty.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            dpi = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

    private static int getNoHasVirtualKey(Activity aty) {
        int height = aty.getWindowManager().getDefaultDisplay().getHeight();
        return height;
    }
}
