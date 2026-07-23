package dao.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 共通DAOクラス（簡易MyBatis）。
 *
 * <p>
 * DB接続、PreparedStatement生成、SQLパラメータ設定、
 * 検索処理、更新処理、単一値取得などの共通処理をまとめる。
 * </p>
 *
 * <p>
 * エンティティクラスに設定された{@link Table}から、
 * 対応するDBテーブル名を自動的に取得する。
 * </p>
 *
 * @param <T> エンティティ型・DTO型
 * @author YSL黄范航
 */
public abstract class BaseDAO<T> {

	/**
	 * マッピング対象のエンティティクラス・DTOクラス。
	 */
	private final Class<T> entityClass;

	/**
	 * {@link Table}から取得したDBテーブル名。
	 */
	private final String tableName;

	/**
	 * コンストラクタ。
	 *
	 * <p>
	 * 指定されたクラスに設定されている{@link Table}から
	 * テーブル名を取得する。
	 * </p>
	 *
	 * @param entityClass マッピング対象のエンティティクラス・DTOクラス
	 * @throws IllegalArgumentException entityClassがnullの場合、
	 *                                  または@Tableが定義されていない場合
	 */
	protected BaseDAO(Class<T> entityClass) {

		this.entityClass = Objects.requireNonNull(
				entityClass,
				"entityClassはnullにできません。");

		this.tableName = resolveTableName(entityClass);
	}

	/**
	 * PreparedStatementを生成する。
	 *
	 * @param conn   Connection
	 * @param sql    SQL文
	 * @param params SQLパラメータ
	 * @return PreparedStatement
	 * @throws Exception PreparedStatement生成失敗、
	 *                   またはパラメータ設定失敗時
	 */
	private PreparedStatement createPreparedStatement(
			Connection conn,
			String sql,
			Object... params) throws Exception {

		// PreparedStatementを作成
		PreparedStatement ps = conn.prepareStatement(sql);

		try {
			// SQLパラメータを設定
			setParams(ps, params);

			return ps;

		} catch (Exception e) {
			// パラメータ設定に失敗した場合はPreparedStatementを閉じる
			ps.close();
			throw e;
		}
	}

	/**
	 * PreparedStatementにパラメータを設定する。
	 *
	 * @param ps     PreparedStatement
	 * @param params SQLパラメータ
	 * @throws Exception パラメータ設定失敗時
	 */
	private void setParams(
			PreparedStatement ps,
			Object... params) throws Exception {

		// パラメータがない場合は処理しない
		if (params == null || params.length == 0) {
			return;
		}

		// すべてのパラメータを設定
		for (int i = 0; i < params.length; i++) {
			ps.setObject(i + 1, params[i]);
		}
	}

	/**
	 * 複数件取得する。
	 *
	 * @param sql    SQL文
	 * @param params SQLパラメータ
	 * @return マッピングされたエンティティのリスト
	 * @throws RuntimeException 検索処理失敗時
	 */
	protected List<T> selectList(
			String sql,
			Object... params) {

		validateSql(sql);

		try (
				Connection conn = DBUtils.getConnection();
				PreparedStatement ps = createPreparedStatement(conn, sql, params);
				ResultSet rs = ps.executeQuery()) {

			return DBMapper.mapToList(rs, entityClass);

		} catch (Exception e) {
			throw new RuntimeException(
					"複数件検索処理に失敗しました。SQL: " + sql,
					e);
		}
	}

	/**
	 * 単一レコードを取得する。
	 *
	 * <p>
	 * 複数件取得された場合は、先頭のレコードを返す。
	 * データが存在しない場合はnullを返す。
	 * </p>
	 *
	 * @param sql    SQL文
	 * @param params SQLパラメータ
	 * @return エンティティ。存在しない場合はnull
	 * @throws RuntimeException 検索処理失敗時
	 */
	protected T selectOne(
			String sql,
			Object... params) {

		List<T> list = selectList(sql, params);

		if (list.isEmpty()) {
			return null;
		}

		return list.get(0);
	}

	/**
	 * 更新処理を実行する。
	 *
	 * <p>
	 * INSERT、UPDATE、DELETEに対応する。
	 * </p>
	 *
	 * @param sql    SQL文
	 * @param params SQLパラメータ
	 * @return 影響行数
	 * @throws RuntimeException 更新処理失敗時
	 */
	protected int executeUpdate(
			String sql,
			Object... params) {

		validateSql(sql);

		try (
				Connection conn = DBUtils.getConnection();
				PreparedStatement ps = createPreparedStatement(conn, sql, params)) {

			return ps.executeUpdate();

		} catch (Exception e) {
			throw new RuntimeException(
					"更新処理に失敗しました。SQL: " + sql,
					e);
		}
	}

	/**
	 * 単一値を取得する。
	 *
	 * <p>
	 * COUNT、MAX、MIN、SUMなど、
	 * 1つの値だけを取得するSQLに使用する。
	 * </p>
	 *
	 * @param <R>    戻り値型
	 * @param sql    SQL文
	 * @param type   戻り値クラス
	 * @param params SQLパラメータ
	 * @return 取得した値。存在しない場合はnull
	 * @throws RuntimeException 検索処理失敗時、
	 *                          または型変換失敗時
	 */
	protected <R> R selectValue(
			String sql,
			Class<R> type,
			Object... params) {

		validateSql(sql);

		Objects.requireNonNull(
				type,
				"戻り値の型はnullにできません。");

		try (
				Connection conn = DBUtils.getConnection();
				PreparedStatement ps = createPreparedStatement(conn, sql, params);
				ResultSet rs = ps.executeQuery()) {

			// データが存在しない場合
			if (!rs.next()) {
				return null;
			}

			// 1列目の値を取得
			Object value = rs.getObject(1);

			// DB値がnullの場合
			if (value == null) {
				return null;
			}

			return convertValue(value, type);

		} catch (Exception e) {
			throw new RuntimeException(
					"単一値検索処理に失敗しました。SQL: " + sql,
					e);
		}
	}

	/**
	 * 対象テーブルの全レコードを取得する。
	 *
	 * <p>
	 * エンティティクラスの@Tableからテーブル名を取得して、
	 * SELECT文を自動生成する。
	 * </p>
	 *
	 * @return 全レコード
	 */
	protected List<T> selectAll() {

		String sql = "SELECT * FROM " + tableName;

		return selectList(sql);
	}

	/**
	 * 対象テーブルのレコード件数を取得する。
	 *
	 * @return レコード件数
	 */
	protected long countAll() {

		String sql = "SELECT COUNT(*) FROM " + tableName;

		Long count = selectValue(sql, Long.class);

		return count == null ? 0L : count;
	}

	/**
	 * 対象テーブルの全レコードを削除する。
	 *
	 * <p>
	 * このメソッドはテーブルの全データを削除するため、
	 * 使用する場合は十分注意すること。
	 * </p>
	 *
	 * @return 削除行数
	 */
	protected int deleteAll() {

		String sql = "DELETE FROM " + tableName;

		return executeUpdate(sql);
	}

	/**
	 * クラスに設定された@Tableからテーブル名を取得する。
	 *
	 * @param clazz 対象クラス
	 * @return テーブル名
	 * @throws IllegalArgumentException @Tableが存在しない場合、
	 *                                  またはテーブル名が不正な場合
	 */
	private static String resolveTableName(Class<?> clazz) {

		Table annotation = clazz.getAnnotation(Table.class);

		if (annotation == null) {
			throw new IllegalArgumentException(
					clazz.getName()
							+ " に@Tableが定義されていません。");
		}

		String value = annotation.value();

		if (value == null || value.isBlank()) {
			throw new IllegalArgumentException(
					clazz.getName()
							+ " の@Tableにテーブル名が設定されていません。");
		}

		String tableName = value.trim();

		/*
		 * テーブル名はPreparedStatementの「?」に設定できないため、
		 * 安全な文字だけで構成されているか確認する。
		 *
		 * 許可例:
		 * users
		 * m_user
		 * public.users
		 */
		if (!tableName.matches(
				"[A-Za-z_][A-Za-z0-9_]*"
						+ "(\\.[A-Za-z_][A-Za-z0-9_]*)?")) {

			throw new IllegalArgumentException(
					"不正なテーブル名です: " + tableName);
		}

		return tableName;
	}

	/**
	 * SQL文がnullまたは空文字でないことを確認する。
	 *
	 * @param sql SQL文
	 * @throws IllegalArgumentException SQLがnullまたは空文字の場合
	 */
	private static void validateSql(String sql) {

		if (sql == null || sql.isBlank()) {
			throw new IllegalArgumentException(
					"SQL文はnullまたは空文字にできません。");
		}
	}

	/**
	 * 単一値を指定された型に変換する。
	 *
	 * @param value DBから取得した値
	 * @param type  変換先クラス
	 * @param <R>   戻り値型
	 * @return 変換後の値
	 */
	@SuppressWarnings({
			"unchecked",
			"rawtypes"
	})
	private <R> R convertValue(
			Object value,
			Class<R> type) {

		// すでに変換先の型である場合
		if (type.isInstance(value)) {
			return type.cast(value);
		}

		// String
		if (type == String.class) {
			return type.cast(String.valueOf(value));
		}

		// Integer / int
		if (type == Integer.class || type == int.class) {
			return (R) Integer.valueOf(
					toNumber(value).intValue());
		}

		// Long / long
		if (type == Long.class || type == long.class) {
			return (R) Long.valueOf(
					toNumber(value).longValue());
		}

		// Double / double
		if (type == Double.class || type == double.class) {
			return (R) Double.valueOf(
					toNumber(value).doubleValue());
		}

		// Float / float
		if (type == Float.class || type == float.class) {
			return (R) Float.valueOf(
					toNumber(value).floatValue());
		}

		// Short / short
		if (type == Short.class || type == short.class) {
			return (R) Short.valueOf(
					toNumber(value).shortValue());
		}

		// Byte / byte
		if (type == Byte.class || type == byte.class) {
			return (R) Byte.valueOf(
					toNumber(value).byteValue());
		}

		// BigDecimal
		if (type == BigDecimal.class) {
			return type.cast(
					new BigDecimal(value.toString()));
		}

		// Boolean / boolean
		if (type == Boolean.class || type == boolean.class) {
			return (R) toBoolean(value);
		}

		// LocalDate
		if (type == LocalDate.class) {

			if (value instanceof Date date) {
				return type.cast(date.toLocalDate());
			}

			if (value instanceof Timestamp timestamp) {
				return type.cast(
						timestamp.toLocalDateTime().toLocalDate());
			}

			return type.cast(
					LocalDate.parse(value.toString()));
		}

		// LocalDateTime
		if (type == LocalDateTime.class) {

			if (value instanceof Timestamp timestamp) {
				return type.cast(
						timestamp.toLocalDateTime());
			}

			return type.cast(
					LocalDateTime.parse(value.toString()));
		}

		// Enum
		if (type.isEnum()) {
			return (R) Enum.valueOf(
					(Class<? extends Enum>) type,
					value.toString());
		}

		// その他の型
		return type.cast(value);
	}

	/**
	 * ObjectをNumberに変換する。
	 *
	 * @param value DB値
	 * @return Number
	 */
	private Number toNumber(Object value) {

		if (value instanceof Number number) {
			return number;
		}

		return new BigDecimal(value.toString());
	}

	/**
	 * ObjectをBooleanに変換する。
	 *
	 * @param value DB値
	 * @return Boolean値
	 */
	private Boolean toBoolean(Object value) {

		if (value instanceof Boolean bool) {
			return bool;
		}

		if (value instanceof Number number) {
			return number.intValue() != 0;
		}

		String str = value.toString()
				.trim()
				.toLowerCase();

		return "true".equals(str)
				|| "1".equals(str)
				|| "yes".equals(str)
				|| "y".equals(str);
	}

	/**
	 * 空のListを取得する。
	 *
	 * @return 空のList
	 */
	protected List<T> emptyList() {
		return new ArrayList<>();
	}

	/**
	 * マッピング対象クラスを取得する。
	 *
	 * @return マッピング対象クラス
	 */
	/**
	 * マッピング対象クラスを取得する。
	 *
	 * @return マッピング対象クラス
	 */
	protected final Class<T> getEntityClass() {
		return entityClass;
	}

	/**
	 * エンティティに設定された値を条件として1件検索する。
	 *
	 * <p>
	 * {@link Column}が設定されたフィールドのみを検索条件として使用する。
	 * String型は部分一致検索、それ以外は完全一致検索とする。
	 * </p>
	 *
	 * <p>
	 * 複数件取得された場合は先頭の1件を返す。
	 * データが存在しない場合はnullを返す。
	 * </p>
	 *
	 * @param conditionEntity 検索条件が設定されたエンティティ
	 * @return 条件に一致するエンティティ。存在しない場合はnull
	 */
	protected T selectOneByCondition(T conditionEntity) {

		SearchCondition condition = createSearchCondition(conditionEntity);

		return selectOne(
				condition.getSql(),
				condition.getParams());
	}

	/**
	 * エンティティに設定された値を条件として複数件検索する。
	 *
	 * <p>
	 * {@link Column}が設定されたフィールドのみを検索条件として使用する。
	 * String型は部分一致検索、それ以外は完全一致検索とする。
	 * </p>
	 *
	 * @param conditionEntity 検索条件が設定されたエンティティ
	 * @return 条件に一致するエンティティのリスト。
	 *         存在しない場合は空のリスト
	 */
	protected List<T> selectByCondition(T conditionEntity) {

		SearchCondition condition = createSearchCondition(conditionEntity);

		return selectList(
				condition.getSql(),
				condition.getParams());
	}

	/**
	 * エンティティに設定された値から検索SQLを生成する。
	 *
	 * <p>
	 * {@link Column}が設定されたフィールドのみを検索条件として使用する。
	 * String型はLIKE検索、それ以外は完全一致検索とする。
	 * </p>
	 *
	 * @param conditionEntity 検索条件が設定されたエンティティ
	 * @return SQL文とSQLパラメータ
	 */
	private SearchCondition createSearchCondition(
			T conditionEntity) {

		StringBuilder sql = new StringBuilder(
				"SELECT * FROM "
						+ tableName
						+ " WHERE 1 = 1");

		List<Object> params = new ArrayList<>();

		// 条件がnullの場合は全件検索
		if (conditionEntity == null) {
			return new SearchCondition(
					sql.toString(),
					params.toArray());
		}

		// null、空文字、空白のみの文字列を除いたフィールドを取得
		List<Field> fields = DBMapper.getNotEmptyFields(conditionEntity);

		for (Field field : fields) {

			// @Columnを取得
			Column column = field.getAnnotation(Column.class);

			// @Columnがないフィールドは検索対象外
			if (column == null) {
				continue;
			}

			String columnName = column.value();

			// カラム名がnullまたは空文字の場合は対象外
			if (columnName == null
					|| columnName.isBlank()) {

				continue;
			}

			// SQLに使用できるカラム名か確認
			validateColumnName(columnName);

			try {
				// privateフィールドを読み取れるようにする
				field.setAccessible(true);

				// フィールドの値を取得
				Object value = field.get(conditionEntity);

				if (value == null) {
					continue;
				}

				// String型は部分一致検索
				if (value instanceof String) {

					String stringValue = (String) value;

					sql.append(" AND ")
							.append(columnName)
							.append(" LIKE ?");

					params.add("%" + stringValue + "%");

				} else {
					// String型以外は完全一致検索
					sql.append(" AND ")
							.append(columnName)
							.append(" = ?");

					params.add(value);
				}

			} catch (IllegalAccessException e) {
				throw new RuntimeException(
						"検索条件の取得に失敗しました。フィールド名: "
								+ field.getName(),
						e);
			}
		}

		return new SearchCondition(
				sql.toString(),
				params.toArray());
	}

	/**
	 * SQLに使用するカラム名を検証する。
	 *
	 * <p>
	 * カラム名はPreparedStatementのパラメータとして設定できないため、
	 * 英字、数字、アンダースコアだけを許可する。
	 * </p>
	 *
	 * @param columnName カラム名
	 * @throws IllegalArgumentException カラム名が不正な場合
	 */
	private static void validateColumnName(
			String columnName) {

		if (!columnName.matches(
				"[A-Za-z_][A-Za-z0-9_]*")) {

			throw new IllegalArgumentException(
					"不正なカラム名です: "
							+ columnName);
		}
	}

	/**
	 * マッピング対象テーブル名を取得する。
	 *
	 * @return DBテーブル名
	 */
	protected final String getTableName() {
		return tableName;
	}

	/**
	 * 動的検索用のSQL文とSQLパラメータを保持するクラス。
	 */
	private static class SearchCondition {

		/**
		 * SQL文。
		 */
		private final String sql;

		/**
		 * SQLパラメータ。
		 */
		private final Object[] params;

		/**
		 * コンストラクタ。
		 *
		 * @param sql SQL文
		 * @param params SQLパラメータ
		 */
		private SearchCondition(
				String sql,
				Object[] params) {

			this.sql = sql;
			this.params = params;
		}

		/**
		 * SQL文を取得する。
		 *
		 * @return SQL文
		 */
		private String getSql() {
			return sql;
		}

		/**
		 * SQLパラメータを取得する。
		 *
		 * @return SQLパラメータ
		 */
		private Object[] getParams() {
			return params;
		}
	}

	@Override
	public String toString() {
		return "BaseDAO [entityClass="
				+ entityClass.getName()
				+ ", tableName="
				+ tableName
				+ "]";
	}
}