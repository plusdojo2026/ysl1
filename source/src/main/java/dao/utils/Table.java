package dao.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DBテーブル名とJavaクラス名を紐づけるアノテーション
 *
 * 例:
 * @Table("user")
 * public class UsersDTO{
 *
 * @author YSL黄范航
 */
@Retention(RetentionPolicy.RUNTIME)
//実行中も使える
@Target(ElementType.TYPE)
//クラスの上に有効
public @interface Table {

	String value();

}
