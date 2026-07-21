package dao.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * データベース接続用ユーティリティクラス
 * <p>
 * 本クラスは以下の機能を提供する：
 * <ul>
 * <li>DB接続取得</li>
 * <li>トランザクション管理（commit / rollback）</li>
 * <li>リソースクローズ（Connection / Statement / ResultSet）</li>
 * <li>複数DB対応（MySQL / PostgreSQL / Oracle / SQLServer）</li>
 * <li>DBタイプに応じたポート自動判定機能</li>
 * </ul>
 * </p>
 *
 * @author YSL黄范航
 */
public class DBUtils {

	/**
	 * データベース種類定義
	 */
	public enum DBType {
		MYSQL(3306), POSTGRESQL(5432), ORACLE(1521), SQLSERVER(1433);

		/** デフォルトポート番号 */
		private final int defaultPort;

		/**
		 * コンストラクタ
		 *
		 * @param port デフォルトポート
		 */
		DBType(int port) {
			this.defaultPort = port;
		}

		/**
		 * デフォルトポート取得
		 *
		 * @return ポート番号
		 */
		public int getPort() {
			return defaultPort;
		}
	}

	/** ===== DB設定 ===== */

	/** 使用するDBタイプ */
	private static final DBType DB_TYPE = DBType.MYSQL;

	/** JDBCドライバ */
	private static final String DRIVER = getDriver();

	/** DBホスト */
	private static final String HOST = "localhost";

	/**
	 * ポート設定（nullの場合は自動選択）
	 */
	private static final Integer PORT = null;

	/** DB名 */
	private static final String DB_NAME = "ysl1";

	/** ユーザー名 */
	private static final String USER = "root";

	/** パスワード */
	private static final String PASSWORD = "password";

	/**
	 * 実際に使用するポートを取得する
	 *
	 * @return ポート番号
	 */
	private static int getPort() {
		return (PORT == null) ? DB_TYPE.getPort() : PORT;
	}

	/**
	 * DBタイプに応じたJDBCドライバを取得する
	 *
	 * @return ドライバクラス名
	 */
	private static String getDriver() {
		switch (DB_TYPE) {
		case MYSQL:
			return "com.mysql.cj.jdbc.Driver";
		case POSTGRESQL:
			return "org.postgresql.Driver";
		case ORACLE:
			return "oracle.jdbc.OracleDriver";
		case SQLSERVER:
			return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		default:
			throw new IllegalArgumentException("Unexpected Database Type");
		}
	}

	/**
	 * JDBC接続URLを生成する
	 *
	 * @return JDBC URL
	 */
	private static String getUrl() {
		String url;

		switch (DB_TYPE) {

		case MYSQL:
			url = "jdbc:mysql://%s:%d/%s"
					+ "?useUnicode=true"
					+ "&characterEncoding=UTF-8"
					+ "&serverTimezone=Asia/Tokyo"
					+ "&useSSL=false";
			break;

		case POSTGRESQL:
			url = "jdbc:postgresql://%s:%d/%s";
			break;

		case ORACLE:
			url = "jdbc:oracle:thin:@%s:%d:%s";
			break;

		case SQLSERVER:
			url = "jdbc:sqlserver://%s:%d;databaseName=%s";
			break;

		default:
			throw new IllegalArgumentException("Unexpected Database Type");
		}

		return String.format(url, HOST, getPort(), DB_NAME);
	}

	/**
	 * DB接続を取得する
	 *
	 * @return Connectionオブジェクト
	 * @throws ClassNotFoundException ドライバ未検出時
	 * @throws SQLException DB接続失敗時
	 */
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER);
		System.out.println("【DEBUG】接続URL: " + getUrl());
		return DriverManager.getConnection(getUrl(), USER, PASSWORD);
	}

	/**
	 * 自動コミットモードを設定する
	 *
	 * @param conn Connection
	 * @param autoCommit true:自動コミットON / false:OFF
	 */
	public static void setAutoCommit(Connection conn, boolean autoCommit) {
		try {
			if (conn != null) {
				conn.setAutoCommit(autoCommit);
			}
		} catch (SQLException e) {
			System.err.println("自動コミット設定エラー");
			e.printStackTrace();
		}
	}

	/**
	 * トランザクションをコミットする
	 *
	 * @param conn Connection
	 */
	public static void commit(Connection conn) {
		try {
			if (conn != null) {
				conn.commit();
			}
		} catch (SQLException e) {
			System.err.println("コミット失敗");
			e.printStackTrace();
		}
	}

	/**
	 * トランザクションをロールバックする
	 *
	 * @param conn Connection
	 */
	public static void rollback(Connection conn) {
		try {
			if (conn != null) {

				conn.rollback();
			}
		} catch (SQLException e) {
			System.err.println("ロールバック失敗");
			e.printStackTrace();
		}
	}

	/**
	 * Connectionをクローズする
	 *
	 * @param conn Connection
	 */
	public static void close(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Statementをクローズする
	 *
	 * @param stmt Statement
	 */
	public static void close(Statement stmt) {
		try {
			if (stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ResultSetをクローズする
	 *
	 * @param rs ResultSet
	 */
	public static void close(ResultSet rs) {
		try {
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Connection / Statement / ResultSet をまとめてクローズする
	 *
	 * @param conn Connection
	 * @param stmt Statement
	 * @param rs ResultSet
	 */
	public static void close(Connection conn, Statement stmt, ResultSet rs) {
		close(rs);
		close(stmt);
		close(conn);
	}

	/**
	 * Connection / Statement をまとめてクローズする
	 *
	 * @param conn Connection
	 * @param stmt Statement
	 */
	public static void close(Connection conn, Statement stmt) {
		close(stmt);
		close(conn);
	}
}