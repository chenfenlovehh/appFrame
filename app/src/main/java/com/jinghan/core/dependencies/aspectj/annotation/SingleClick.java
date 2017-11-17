package com.jinghan.core.dependencies.aspectj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liuzeren
 * @time 2017/11/6    上午10:23
 * @mail lzr319@163.com
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD})
public @interface SingleClick{}