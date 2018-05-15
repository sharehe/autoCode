package cn.sharehe.handle.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义数据库属性长度与属性字段说明
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
    public @interface LengthAndNote {
    int length() default 32;
    String note() default  "";
}
