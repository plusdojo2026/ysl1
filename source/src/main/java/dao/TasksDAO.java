package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.AllDTO;


public class TasksDAO {
	
	public Connection conn = null;
	
	//コネクションを保持するコンストラクタ
	public TasksDAO(Connection conn) {
		this.conn=conn;
	}
	
	/**
	 * タスクの一覧を取得するメソッド
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<AllDTO> selectAll() throws SQLException {
		ArrayList<AllDTO> taskList = new ArrayList<AllDTO>();
		
		// SELECT文を準備する
		String sql = "select t.id,c.case_name,t.task_name,u.user_name,t.task_status,t.task_priority,t.deadline,"
				+ "t.progress_rate from tasks t"
				+ "LEFT JOIN cases c ON t.case_id = c.id"
				+ "LEFT JOIN  users u ON t.manager_id = u.id";
		
		//デバッグ（SQL文の確認用）
		System.out.println(sql);
		
		// まとめる
		PreparedStatement pStmt = conn.prepareStatement(sql);

		// SELECT文を実行し、結果表を取得する
		ResultSet rs = pStmt.executeQuery();
		
		//移し替え
		while(rs.next()) {
			AllDTO dto = new AllDTO();
			dto.setTaskId(rs.getInt("id"));
			dto.setCaseName(rs.getString("case_name"));
			dto.setTaskName(rs.getString("task_name"));
			dto.setUserName(rs.getString("user_name"));
			dto.setTaskStatus(rs.getString("task_status"));
			dto.setTaskPriority(rs.getString("task_priority"));	
			dto.setDeadline(rs.getString("deadline"));
			dto.setProgressRate(rs.getInt("progress_rate"));
		}
		//serviceに返却する
			return taskList;
	}
}
	