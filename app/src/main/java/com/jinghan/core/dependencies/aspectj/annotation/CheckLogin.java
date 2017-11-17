package com.jinghan.core.dependencies.aspectj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 检测登录，即必须要先登录才能使用的功能
 * @author liuzeren
 * @time 2017/11/6    上午10:26
 * @mail lzr319@163.com
 */

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface CheckLogin{}