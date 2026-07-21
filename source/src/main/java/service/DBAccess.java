package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBAccess {
	
	//コネクションを保持する変数
	protected Connection conn = null;
	
	//データベースとの接続を行うメソッド
		protected void access() {
			
			try {
				// JDBCドライバを読み込む
				Class.forName("com.mysql.cj.jdbc.Driver");
				// データベースに接続する
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample1?"
						+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
						"root", "password");	//あとで変更
				
				//例外処理
				}catch (ClassNotFoundException e) {
					e.printStackTrace();
				}catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		//データベースとの接続を切断するメソッド
		protected void close() {
			//データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

}
