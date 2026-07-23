package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.AllDTO;
import dto.CasesDTO;
import dto.TasksDTO;
import dto.UsersDTO;

/**
 * タスクテーブルを操作するDAOクラス。
 * <p>
 * タスクテーブルの一覧表示、情報の検索、条件検索、登録などの
 * データベース操作を行う。
 * </p>
 *
 *
 * @author 石田
 */

public class TasksDAO {
	
	/**
	 * TasksDAOを生成するコンストラクタ
	 * @param conn データベース接続情報
	 */
	
	public Connection conn = null;
	
	//コネクションを保持するコンストラクタ
	public TasksDAO(Connection conn) {
		this.conn=conn;
	}
	
	/**
	 * タスクの一覧を取得するメソッド
	 * @return taskList
	 * @throws SQLException SQL実行時にエラーが発生した場合
	 */
	public ArrayList<AllDTO> selectAll() throws SQLException {
		ArrayList<AllDTO> taskList = new ArrayList<AllDTO>();
		
		// SELECT文を準備する
		String sql = "select t.id,c.case_name,t.task_name,u.user_name,t.task_status,t.task_priority,t.deadline,"
				+ "t.progress_rate, COALESCE(sum(works.actual_hours), 0) as actual_hours,t.task_planned_hours "
				+ "from tasks t "
				+ "LEFT JOIN cases c ON t.case_id = c.id "
				+ "LEFT JOIN users u ON t.manager_id = u.id "
				+ "LEFT JOIN works ON t.id = works.task_id " 
				+ "GROUP BY t.id,c.case_name,t.task_name,u.user_name,"
		        + "t.task_status,t.task_priority,t.deadline,"
		        + "t.progress_rate,t.task_planned_hours" ;

		
		
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
			dto.setTaskProgressRate(rs.getInt("progress_rate"));
			dto.setActualHours(rs.getBigDecimal("actual_hours"));
			dto.setTaskPlannedHours(rs.getBigDecimal("task_planned_hours"));

			taskList.add(dto);
		}
		//serviceに返却する
			return taskList;
	}
	
	/**
	 * - すべての案件名を取得する -
	 * 
	 * 
	 * @author haruto.tanaka
	 */
	public ArrayList<CasesDTO> selectCases() throws SQLException {
		ArrayList<CasesDTO> casesList = new ArrayList<CasesDTO>();

		// 案件名をすべて取得
		String sql = "SELECT case_name FROM cases";
		PreparedStatement pStmt = conn.prepareStatement(sql);

		ResultSet rs = pStmt.executeQuery();

		// 格納
		while (rs.next()) {
			CasesDTO dto = new CasesDTO();

			dto.setCaseName(rs.getString("case_name"));

			casesList.add(dto);
		}

		return casesList;
	}

	/**
	 * - すべてのPM名を取得する -
	 * 
	 * 
	 * @author haruto.tanaka
	 */
	public ArrayList<UsersDTO> selectPM() throws SQLException {
		ArrayList<UsersDTO> pmList = new ArrayList<UsersDTO>();

		// PMとして登録されているユーザー名を取得
		String sql =
			"SELECT DISTINCT u.user_name FROM users u "
			+ "INNER JOIN cases c ON u.id = c.pm_id AND u.active = 1";

		PreparedStatement pStmt = conn.prepareStatement(sql);

		ResultSet rs = pStmt.executeQuery();

		// 格納
		while (rs.next()) {
			UsersDTO dto = new UsersDTO();

			dto.setUserName(rs.getString("user_name"));

			pmList.add(dto);
		}

		return pmList;
	}

	/**
	 * - 指定したタスクIDの詳細を取得するメソッド -
	 * 
	 * @return ArrayList<TasksDTO> taskList;
	 * 
	 * 
	 * @author haruto.tanaka
	 */
	public ArrayList<TasksDTO> selectByTaskID(int id) throws SQLException {
		ArrayList<TasksDTO> taskList = new ArrayList<TasksDTO>();
		
		//タスクIDが一致するデータの詳細を検索する
		String sql = "SELECT * FROM tasks WHERE id = ?";
		PreparedStatement pStmt = conn.prepareStatement(sql);
		
		//タスクIDをSQLの絞り込み条件にあてはめる
		pStmt.setInt(1, id);
		
		ResultSet rs = pStmt.executeQuery();

		//格納
		while (rs.next()) {
			TasksDTO dto = new TasksDTO();

			dto.setId(rs.getInt("id"));
			dto.setCaseId(rs.getInt("case_id"));
			dto.setTaskName(rs.getString("task_name"));
			dto.setManagerId(rs.getInt("manager_id"));
			dto.setTaskStatus(rs.getString("task_status"));
			dto.setTaskPriority(rs.getString("task_priority"));
			dto.setDeadline(rs.getString("deadline"));
			dto.setProgressRate(rs.getInt("progress_rate"));
			dto.setStartDate(rs.getString("start_date"));
			dto.setTaskPlannedHours(rs.getInt("task_planned_hours"));
			dto.setTaskDescription(rs.getString("task_description"));

			taskList.add(dto);
		}

		return taskList;
	}

	/**
	 * タスクを登録する
	 *
	 * @return result
	 * @throws SQLException
	 * 
	 * 
	 * @author haruto.tanaka
	 */
	public boolean insert(TasksDTO dto) throws SQLException {
		boolean result = false;

		String sql =
			"INSERT INTO tasks (" +
			"case_id," +
			"task_name," +
			"manager_id," +
			"task_status," +
			"task_priority," +
			"task_planned_hours," +
			"progress_rate," +
			"start_date," +
			"deadline," +
			"task_description) "
			+"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement pStmt = conn.prepareStatement(sql);

		pStmt.setInt(1, dto.getCaseId());
		pStmt.setString(2, dto.getTaskName());
		pStmt.setInt(3, dto.getManagerId());
		pStmt.setString(4, dto.getTaskStatus());
		pStmt.setString(5, dto.getTaskPriority());
		pStmt.setDouble(6, dto.getTaskPlannedHours());
		pStmt.setInt(7, dto.getProgressRate());
		pStmt.setString(8, dto.getStartDate());
		pStmt.setString(9, dto.getDeadline());
		pStmt.setString(10, dto.getTaskDescription());

		int count = pStmt.executeUpdate();

		if (count > 0) {
			result = true;
		}

		return result;
	}
	
	/**
	 * タスクを更新する
	 *
	 * @param dto
	 * @return result
	 *
	 * @throws SQLException
	 */
	public boolean update(TasksDTO dto) throws SQLException {

	    boolean result = false;

	    String sql =
	        "UPDATE tasks SET " +
	        "case_id = ?, " +
	        "manager_id = ?, " +
	        "task_name = ?, " +
	        "task_status = ?, " +
	        "task_priority = ?, " +
	        "task_planned_hours = ?, " +
	        "progress_rate = ?, " +
	        "start_date = ?, " +
	        "deadline = ?, " +
	        "task_description = ? " +
	        "WHERE id = ?";

	    PreparedStatement pStmt = conn.prepareStatement(sql);

	    pStmt.setInt(1, dto.getCaseId());
	    pStmt.setInt(2, dto.getManagerId());
	    pStmt.setString(3, dto.getTaskName());
	    pStmt.setString(4, dto.getTaskStatus());
	    pStmt.setString(5, dto.getTaskPriority());
	    pStmt.setDouble(6, dto.getTaskPlannedHours());
	    pStmt.setInt(7, dto.getProgressRate());
	    pStmt.setString(8, dto.getStartDate());
	    pStmt.setString(9, dto.getDeadline());
	    pStmt.setString(10, dto.getTaskDescription());
	    pStmt.setInt(11, dto.getId());

	    int count = pStmt.executeUpdate();

	    if (count > 0) {
	        result = true;
	    }

	    return result;
	}
}
	