package com.jinghan.core.dependencies.aspectj.aop;

import android.app.Activity;

import com.jinghan.app.AppContext;
import com.jinghan.core.dependencies.aspectj.annotation.Permission;
import com.jinghan.core.helper.PermissionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;


/**
 * @author liuzeren
 * @time 2017/11/6    上午10:42
 * @mail lzr319@163.com
 */
@Aspect
public class SysPermissionAspect {

    @Around("execution(@com.jinghan.core.dependencies.aspectj.annotation.Permission * *(..)) && @annotation(permission)")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint, Permission permission) {
        Activity ac = AppContext.getInstance().getLastActivityManager().getLastActivity();

        PermissionUtils.requestPermissionsResult(ac, 1,permission.value(), new PermissionUtils.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {
                //权限申请成功，执行原方法
                if(permission.isCallback()) {
                    try {
                        joinPoint.proceed();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            }

            @Override
            public void onPermissionDenied() {

            }
        });
    }
}