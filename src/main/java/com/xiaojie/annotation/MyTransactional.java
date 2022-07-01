package com.xiaojie.annotation;

import java.lang.annotation.*;

/**
 * 自定义事务注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MyTransactional {
}
