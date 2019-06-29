package com.kaywall.annotation.builder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE) // 源码期可用（编译），运行时不可用
public @interface BuilderProperty {
}
