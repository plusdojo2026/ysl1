package dao.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * ResultSet → Javaオブジェクト変換クラス
 * 
 * @author YSL黄范航
 */
public class DBMapper {

	/**
	 * ResultSetの1行をオブジェクトに変換する。
	 *
	 * @param rs    検索結果
	 * @param clazz マッピング対象クラス
	 * @return マッピング済みオブジェクト
	 * @param <T> エンティティ型
	 * @throws Exception 変換失敗時
	 */
	public static <T> T mapToObject(
			ResultSet rs,
			Class<T> clazz)
			throws Exception {

		// オブジェクト作成
		T obj = clazz.getDeclaredConstructor().newInstance();

		// ResultSet構造確認
		ResultSetMetaData metaData = rs.getMetaData();

		// 列数
		int count = metaData.getColumnCount();

		// ループ
		for (int i = 1; i <= count; i++) {

			// 列名
			String column = metaData.getColumnLabel(i);

			// 値
			Object value = rs.getObject(i);

			// DB列名に対応するフィールドを取得
			Field field = findField(clazz, column);

			// 対応するフィールドが存在しない場合、処理しない
			if (field == null) {
				continue;
			}

			// privateフィールドにもアクセスできるようにする
			field.setAccessible(true);

			// DB値をJavaフィールドの型に合わせて変換
			Object convertedValue = convertValue(value, field.getType());

			// 値をオブジェクトに設定
			field.set(obj, convertedValue);
		}

		return obj;
	}

	/**
	 * DB列名に対応するFieldを取得する。
	 * 先に@Columnを検索し、
	 * 見つからない場合はキャメルケース変換で検索する。
	 *
	 * @param clazz  マッピング対象クラス
	 * @param column DB列名
	 * @return 対応するField。存在しない場合はnull
	 */
	private static Field findField(Class<?> clazz, String column) {

		// 親クラスを含めてフィールドを確認
		List<Field> fields = getAllFields(clazz);

		// すべてのフィールド変数をループ
		for (Field field : fields) {

			// @Columnのデータをもらい
			Column annotation = field.getAnnotation(Column.class);

			// データベースの列名と一致の場合
			if (annotation != null && column.equalsIgnoreCase(annotation.value())) {

				// 保存、退出
				return field;
			}
		}

		// フィールド名そのままと一致するか確認
		for (Field field : fields) {

			if (field.getName().equalsIgnoreCase(column)) {
				return field;
			}
		}

		// user_name → userName に変換して検索
		String camelName = toCamel(column);

		// キャメルケース変換後の名前と一致するか確認
		for (Field field : fields) {

			if (field.getName().equals(camelName)) {
				return field;
			}
		}

		// 見つからない場合
		return null;
	}

	/**
	 * 親クラスを含めてすべてのフィールドを取得する。
	 *
	 * @param clazz 対象クラス
	 * @return フィールドリスト
	 */
	private static List<Field> getAllFields(Class<?> clazz) {

		// フィールド保存用リスト
		List<Field> fields = new ArrayList<>();

		// 現在のクラスを保存
		Class<?> current = clazz;

		// Objectクラスまでループ
		while (current != null && current != Object.class) {

			// 現在クラスのフィールドを取得
			Field[] declaredFields = current.getDeclaredFields();

			// フィールドをリストに追加
			for (Field field : declaredFields) {
				fields.add(field);
			}

			// 親クラスへ移動
			current = current.getSuperclass();
		}

		return fields;
	}

	/**
	 * ResultSetをListに変換する。
	 *
	 * @param rs    検索結果
	 * @param clazz マッピング対象クラス
	 * @return List<T>
	 * @param <T> エンティティ型
	 * @throws Exception 変換失敗時
	 */
	public static <T> List<T> mapToList(
			ResultSet rs,
			Class<T> clazz)
			throws Exception {

		// 戻り値用List
		List<T> list = new ArrayList<>();

		// ResultSetを1行ずつ処理
		while (rs.next()) {

			// 1行をオブジェクトに変換してListに追加
			list.add(mapToObject(rs, clazz));
		}

		return list;
	}

	/**
	 * ResultSetの先頭1件をオブジェクトに変換する。
	 *
	 * @param rs    検索結果
	 * @param clazz マッピング対象クラス
	 * @return 1件のオブジェクト。存在しない場合はnull
	 * @param <T> エンティティ型
	 * @throws Exception 変換失敗時
	 */
	public static <T> T mapToSingle(
			ResultSet rs,
			Class<T> clazz)
			throws Exception {

		// データがある場合
		if (rs.next()) {

			// 1行をオブジェクトに変換して返す
			return mapToObject(rs, clazz);
		}

		// データがない場合
		return null;
	}

	/**
	 * DB値をJavaフィールドの型に合わせて変換する。
	 *
	 * @param value      DBから取得した値
	 * @param targetType Javaフィールドの型
	 * @return 変換後の値
	 */
	private static Object convertValue(Object value, Class<?> targetType) {

		// DB値がnullの場合
		if (value == null) {

			// int型の場合
			if (targetType == int.class) {
				return 0;
			}

			// long型の場合
			if (targetType == long.class) {
				return 0L;
			}

			// double型の場合
			if (targetType == double.class) {
				return 0.0;
			}

			// float型の場合
			if (targetType == float.class) {
				return 0.0f;
			}

			// boolean型の場合
			if (targetType == boolean.class) {
				return false;
			}

			// 参照型の場合
			return null;
		}

		// 型がそのまま代入可能な場合
		if (targetType.isAssignableFrom(value.getClass())) {
			return value;
		}

		// String型の場合
		if (targetType == String.class) {
			return String.valueOf(value);
		}

		// int / Integer型の場合
		if (targetType == int.class || targetType == Integer.class) {
			return toNumber(value).intValue();
		}

		// long / Long型の場合
		if (targetType == long.class || targetType == Long.class) {
			return toNumber(value).longValue();
		}

		// double / Double型の場合
		if (targetType == double.class || targetType == Double.class) {
			return toNumber(value).doubleValue();
		}

		// float / Float型の場合
		if (targetType == float.class || targetType == Float.class) {
			return toNumber(value).floatValue();
		}

		// BigDecimal型の場合
		if (targetType == BigDecimal.class) {
			return new BigDecimal(value.toString());
		}

		// boolean / Boolean型の場合
		if (targetType == boolean.class || targetType == Boolean.class) {
			return toBoolean(value);
		}

		// LocalDateTime型の場合
		if (targetType == LocalDateTime.class) {

			// Timestampの場合
			if (value instanceof Timestamp) {
				return ((Timestamp) value).toLocalDateTime();
			}

			// 文字列から変換
			return LocalDateTime.parse(value.toString());
		}

		// LocalDate型の場合
		if (targetType == LocalDate.class) {

			// Dateの場合
			if (value instanceof Date) {
				return ((Date) value).toLocalDate();
			}

			// Timestampの場合
			if (value instanceof Timestamp) {
				return ((Timestamp) value).toLocalDateTime().toLocalDate();
			}

			// 文字列から変換
			return LocalDate.parse(value.toString());
		}

		// enum型の場合
		if (targetType.isEnum()) {
			return Enum.valueOf((Class<Enum>) targetType, value.toString());
		}

		// 変換できない場合、そのまま返す
		return value;
	}

	/**
	 * ObjectをNumberに変換する。
	 *
	 * @param value DB値
	 * @return Number
	 */
	private static Number toNumber(Object value) {

		// すでにNumberの場合
		if (value instanceof Number) {
			return (Number) value;
		}

		// 文字列などの場合
		return new BigDecimal(value.toString());
	}

	/**
	 * ObjectをBooleanに変換する。
	 *
	 * @param value DB値
	 * @return Boolean値
	 */
	private static Boolean toBoolean(Object value) {

		// すでにBooleanの場合
		if (value instanceof Boolean) {
			return (Boolean) value;
		}

		// 数値の場合、0以外はtrue
		if (value instanceof Number) {
			return ((Number) value).intValue() != 0;
		}

		// 文字列に変換
		String str = value.toString().toLowerCase();

		// trueとして扱う値
		return "true".equals(str)
				|| "1".equals(str)
				|| "yes".equals(str)
				|| "y".equals(str);
	}

	/**
	 * user_name → userName
	 *
	 * @param name DB列名
	 * @return キャメルケース文字列
	 */
	private static String toCamel(String name) {

		// 値があるかどうかを確認
		if (name == null || name.isEmpty()) {
			return name;
		}

		// 小文字に転換
		name = name.toLowerCase();

		// 文字列を操作するためにStringBuilder用意
		StringBuilder sb = new StringBuilder();

		// 転換完成フラグ
		boolean upper = false;

		// すべての文字をループ
		for (char c : name.toCharArray()) {

			// アンダーバーがあると処理しない、次の文字を処理する、フラグをTRUEに変更
			if (c == '_') {
				upper = true;
			} else {

				// upperがtrueの場合、大文字に転換
				sb.append(upper ? Character.toUpperCase(c) : c);

				// フラグを戻す
				upper = false;
			}
		}

		return sb.toString();
	}
}