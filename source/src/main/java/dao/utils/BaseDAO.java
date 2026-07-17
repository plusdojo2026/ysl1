package dao.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 共通DAOクラス（簡易MyBatis）
 * <p>
 * DB接続、PreparedStatement生成、SQLパラメータ設定、
 * 検索処理、更新処理、単一値取得などの共通処理をまとめる。
 * </p>
 *
 * @param <T> エンティティ型・DTO型
 * @author YSL黄范航
 */
public abstract class BaseDAO<T> {

	// 設定したクラスの変数
	private final Class<T> entityClass;

	/**
	 * コンストラクタでクラスを設定する。
	 *
	 * @param entityClass マッピング対象のエンティティクラス・DTOクラス
	 */
	protected BaseDAO(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	/**
	 * PreparedStatement生成
	 *
	 * @param conn   Connection
	 * @param sql    SQL文
	 * @param params SQLパラメータ
	 * @return PreparedStatement
	 * @throws SQLException PreparedStatement生成失敗、またはパラメータ設定失敗時
	 */
	private PreparedStatement createPS(Connection conn, String sql, Object... params) throws SQLException {

		// PreparedStatementを作成
		PreparedStatement ps = conn.prepareStatement(sql);

		// パラメータを設定
		setParams(ps, params);

		return ps;
	}

	/**
	 * PreparedStatementにパラメータを設定する。
	 *
	 * @param ps     PreparedStatement
	 * @param params SQLパラメータ
	 * @throws SQLException パラメータ設定失敗時
	 */
	private void setParams(PreparedStatement ps, Object... params) throws SQLException {

		// パラメータがない場合、処理しない
		if (params == null) {
			return;
		}

		// ループしてparamsの中のすべての変数をsql文に入れる
		for (int i = 0; i < params.length; i++) {
			ps.setObject(i + 1, params[i]);
		}
	}

	/**
	 * 複数件取得
	 *
	 * @param sql    SQL文
	 * @param params SQLパラメータ
	 * @return List<DTO>
	 * @throws RuntimeException 検索処理失敗時
	 */
	protected List<T> selectList(String sql, Object... params) {

		try (
				// DBUtilsで接続
				Connection conn = DBUtils.getConnection();

				// PreparedStatement生成
				PreparedStatement ps = createPS(conn, sql, params);) {

			// SQL実行
			try (ResultSet rs = ps.executeQuery()) {

				// ResultSetをListに変換して返す
				return DBMapper.mapToList(rs, entityClass);
			}

		} catch (Exception e) {

			// SQL情報を含めて例外を投げる
			throw new RuntimeException("複数件検索処理に失敗しました。SQL: " + sql, e);
		}
	}

	/**
	 * 単一レコードを取得する。
	 *
	 * @param sql    SQL文
	 * @param params SQLパラメータ
	 * @return エンティティ。存在しない場合はnull
	 * @throws RuntimeException 検索処理失敗時
	 */
	protected T selectOne(String sql, Object... params) {

		// 複数件検索を実行
		List<T> list = selectList(sql, params);

		// データが存在する場合、先頭1件を返す
		return (list != null && !list.isEmpty()) ? list.get(0) : null;
	}

	/**
	 * 更新処理を実行する。
	 * INSERT・UPDATE・DELETEに対応する。
	 *
	 * @param sql    SQL文
	 * @param params SQLパラメータ
	 * @return 影響行数
	 * @throws RuntimeException 更新処理失敗時
	 */
	protected int executeUpdate(String sql, Object... params) {

		try (
				// DBUtilsで接続
				Connection conn = DBUtils.getConnection();

				// PreparedStatement生成
				PreparedStatement ps = createPS(conn, sql, params);) {

			// 更新処理を実行
			return ps.executeUpdate();

		} catch (Exception e) {

			// SQL情報を含めて例外を投げる
			throw new RuntimeException("更新処理に失敗しました。SQL: " + sql, e);
		}
	}

	/**
	 * 単一値を取得する。
	 * <p>
	 * COUNT、MAX、MIN、SUMなど、1つの値だけを取得するSQLに使用する。
	 * </p>
	 *
	 * @param <R>    戻り値型(Result)
	 * @param sql    SQL文
	 * @param type   戻り値クラス
	 * @param params SQLパラメータ
	 * @return 取得した値。存在しない場合はnull
	 * @throws RuntimeException 検索処理失敗時、または型変換失敗時
	 */
	protected <R> R selectValue(String sql, Class<R> type, Object... params) {

		try (
				// DBUtilsで接続
				Connection conn = DBUtils.getConnection();

				// PreparedStatement生成
				PreparedStatement ps = createPS(conn, sql, params);) {

			// SQL実行
			try (ResultSet rs = ps.executeQuery()) {

				// データが存在する場合
				if (rs.next()) {

					// 1列目の値を取得
					Object value = rs.getObject(1);

					// 値がnullの場合
					if (value == null) {
						return null;
					}

					// 型変換して返す
					return convertValue(value, type);
				}
			}

		} catch (Exception e) {

			// SQL情報を含めて例外を投げる
			throw new RuntimeException("単一値検索処理に失敗しました。SQL: " + sql, e);
		}

		// データが存在しない場合
		return null;
	}

	/**
	 * 単一値を指定された型に変換する。
	 *
	 * @param value DBから取得した値
	 * @param type  変換先クラス
	 * @return 変換後の値
	 * @param <R> 戻り値型
	 */
	@SuppressWarnings("unchecked")
	private <R> R convertValue(Object value, Class<R> type) {

		// 変換先がStringの場合
		if (type == String.class) {
			return type.cast(String.valueOf(value));
		}

		// 変換先がIntegerの場合
		if (type == Integer.class || type == int.class) {
			return (R) Integer.valueOf(((Number) value).intValue());
		}

		// 変換先がLongの場合
		if (type == Long.class || type == long.class) {
			return (R) Long.valueOf(((Number) value).longValue());
		}

		// 変換先がDoubleの場合
		if (type == Double.class || type == double.class) {
			return (R) Double.valueOf(((Number) value).doubleValue());
		}

		// 変換先がFloatの場合
		if (type == Float.class || type == float.class) {
			return (R) Float.valueOf(((Number) value).floatValue());
		}

		// 変換先がBooleanの場合
		if (type == Boolean.class || type == boolean.class) {

			// DBの値がNumberの場合
			if (value instanceof Number) {
				return (R) Boolean.valueOf(((Number) value).intValue() != 0);
			}

			// 文字列として判定
			return (R) Boolean.valueOf(Boolean.parseBoolean(value.toString()));
		}

		// すでに指定型の場合
		if (type.isInstance(value)) {
			return type.cast(value);
		}

		// その他の場合、castを試す
		return type.cast(value);
	}

	/**
	 * 空のListを取得する。
	 * <p>
	 * 必要に応じてDAO側で空リストを返したい場合に使用できる。
	 * </p>
	 *
	 * @return 空のList
	 */
	protected List<T> emptyList() {
		return new ArrayList<>();
	}

	/**
	 * getter
	 *
	 * @return マッピング対象のエンティティクラス・DTOクラス
	 */
	protected Class<T> getEntityClass() {
		return entityClass;
	}

	@Override
	public String toString() {
		return "BaseDAO [entityClass=" + entityClass + "]";
	}
}