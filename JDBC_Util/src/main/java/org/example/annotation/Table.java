package org.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)//type 目标对应为类的类型
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    public String value();
}
