package dao;

import java.sql.Connection;

public class CasesDAO {

	public Connection conn = null;

	//コネクションを保持するコンストラクタ
	public CasesDAO(Connection conn) {
		this.conn = conn;
	}

	//ページを開いた時に表示するもののメソッド
}
