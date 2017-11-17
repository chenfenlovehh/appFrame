package com.jinghan.core.dependencies.dragger2.scope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Scope;

/**
 * @author liuzeren
 * @time 2017/11/2    下午3:06
 * @mail lzr319@163.com
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface FragmentScoped {}