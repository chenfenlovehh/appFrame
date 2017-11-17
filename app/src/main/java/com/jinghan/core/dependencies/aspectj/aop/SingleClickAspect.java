package com.jinghan.core.dependencies.aspectj.aop;

import android.view.View;
import com.jinghan.core.R;
import com.orhanobut.logger.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Calendar;

/**
 * @author liuzeren
 * @time 2017/11/6    上午10:27
 * @mail lzr319@163.com
 */
@Aspect
public class SingleClickAspect{

    public static int TIME_TAG = R.id.aspectj_click_time;
    public static int MIN_CLICK_DELAY_TIME = 6000;

    @Pointcut("execution(@com.jinghan.core.dependencies.aspectj.annotation.SingleClick * *(..))") //方法切入点
    public void methodAnnotated(){}

    @Around("methodAnnotated()")//在连接点进行方法替换
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint){
        View view  = null;

        for(Object arg : joinPoint.getArgs()) {
            if (arg instanceof View) {
                view = (View) arg;
                break;
            }

            Object tag = view.getTag();
            long lastClickTime = (tag != null? (long)tag: 0L);

            Logger.i("SingleClickAspect", "lastClickTime:" + lastClickTime);
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {//过滤掉600毫秒内的连续点击
                view.setTag(TIME_TAG, currentTime);
                Logger.i("SingleClickAspect", "currentTime:" + currentTime);
                try {
                    joinPoint.proceed();//执行原方法
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }
    }
}