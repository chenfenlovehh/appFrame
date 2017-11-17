package com.jinghan.core.dependencies.aspectj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限申请
 * @param isCallback 是否需要回调注解方式 true:需要回调
 * @author liuzeren
 * @time 2017/11/6    上午10:25
 * @mail lzr319@163.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Permission{
    String[] value();
    boolean isCallback();
}
