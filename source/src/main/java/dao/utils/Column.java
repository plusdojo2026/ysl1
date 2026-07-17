package dao.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * DB列名とJavaフィールド名を紐づけるアノテーション
 *
 * 例:
 * @Column("user_name")
 * private String userName;
 *
 * @author YSL黄范航
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    String value();
}
