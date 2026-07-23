package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.AllDTO;


/**
 * ダッシュボードの各項目の件数を取得するDAOクラス。
 * <p>
 * 進行中の案件、担当タスク、タスクの期限超過件数
 * をカウントするデータベース操作を行う。
 * </p>
 *
 *
 * @author 石田
 */

public class HomeDAO {
	
	/**
	 * TasksDAOを生成するコンストラクタ
	 * @param conn データベース接続情報
	 */
	
	public Connection conn = null;
	
	//コネクションを保持するコンストラクタ
	public HomeDAO(Connection conn) {
		this.conn=conn;
	}
	
	
	/**
	 * 進行中の案件、担当タスク、タスクの期限超過件数
	 * をカウントするメソッド
	 * 
	 * @param id
	 * @return selectdto
	 * @throws SQLException 
	 */
	//カウントした結果を取得してを返すメソッド
	public AllDTO select(int id) throws SQLException {
		AllDTO selectdto = new AllDTO();
		
		
		//ログインユーザーの参加している進行中の案件数
		String casesql = "SELECT COUNT(*) AS cases from cases c "
						+ "WHERE c.pm_id = ? and c.case_status = '進行中'" ;
				
		//デバッグ（SQL文の確認用）
		System.out.println(casesql);
		
		
		// まとめる
		PreparedStatement pStmt = conn.prepareStatement(casesql);
		
		// SELECT文を実行し、結果表を取得する
		ResultSet rs = pStmt.executeQuery();
		
		//移し替え
		while(rs.next()) {			
			selectdto.setInProgressCase(rs.getInt("cases"));
		}

		
//--------------------------------------------------------------------------------------
		
		//ログインユーザーの担当タスク件数
		String tasksql = "SELECT COUNT(*) AS tasks from tasks t "
						+ "WHERE t.manager_id  = ? " ;
				
		//デバッグ（SQL文の確認用）
		System.out.println(tasksql);
		
		// まとめる
		pStmt = conn.prepareStatement(tasksql);
		
		// SELECT文を実行し、結果表を取得する
		 rs = pStmt.executeQuery();
				
		//移し替え
		while(rs.next()) {			
			selectdto.setInProgressCase(rs.getInt("tasks"));
		}		
			
		

//--------------------------------------------------------------------------------------
		
		//担当タスク内の期限超過件数
		String oversql = "SELECT COUNT(*) AS over from tasks t "
						 + "WHERE t.manager_id = ? and t.deadline < CURRENT_DATE ";
				 
		//デバッグ（SQL文の確認用）
		System.out.println(oversql);		
				
		// まとめる
		pStmt = conn.prepareStatement(oversql);
		
		// SELECT文を実行し、結果表を取得する
		 rs = pStmt.executeQuery();
				
		//移し替え
			while(rs.next()) {			
				selectdto.setInProgressCase(rs.getInt("over"));
			}
		
		return selectdto;	
	}
	
}
