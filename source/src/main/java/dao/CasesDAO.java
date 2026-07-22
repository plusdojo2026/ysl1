package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.AllDTO;

public class CasesDAO {

	public Connection conn = null;

	/**
	 * コネクションを保持するコンストラクタ
	 * @param conn
	 */
	public CasesDAO(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 案件詳細を持ってくるメソッド
	 * @return ArrayList<AllDTO>
	 * @throws SQLException
	 */
	public ArrayList<AllDTO> selectCases(int id) throws SQLException {
		ArrayList<AllDTO> allDTOs = new ArrayList<AllDTO>();
		//sql文
		String sql = "SELECT * FROM cases WHERE case_id=?";

		// まとめる
		PreparedStatement pStmt = conn.prepareStatement(sql);

		//?のための文
		pStmt.setInt(1, id);

		ResultSet rs = pStmt.executeQuery();

		while (rs.next()) {
			AllDTO dto = new AllDTO();
			dto.setCaseId(rs.getInt("id"));
			dto.setCaseName(rs.getString("case_name"));
			dto.setCaseCode(rs.getInt("case_sode"));
			dto.setCustomerName(rs.getString("customer_name"));
			dto.setCasePriority(rs.getString("case_priority"));
			dto.setPmId(rs.getInt("pm_id"));
			dto.setCaseStatus(rs.getString("case_status"));
			dto.setStartDate(rs.getString("start_date"));
			dto.setPlannedEndDate(rs.getString("planned_end_date"));
			dto.setCaseDescription(rs.getString("case_description"));
			dto.setCasePlannedHours(rs.getInt("case_planned_hours"));

			allDTOs.add(dto);
		}
		return allDTOs;
	}

	/**
	 * タスクを持ってくるメソッド
	 * @param id
	 * @return ArrayList<AllDTO>
	 * @throws SQLException
	 */
	public ArrayList<AllDTO> selectTasks(int id) throws SQLException {
		ArrayList<AllDTO> allDTOs = new ArrayList<AllDTO>();

		String sql = "select t.*, u.user_name, sum(w.actual_hours) as ac\r\n"
				+ "from tasks as t\r\n"
				+ "join users as u on t.manager_id = u.id\r\n"
				+ "join works as w on t.id = w.task_id\r\n"
				+ "where t.case_id = ?\r\n"
				+ "group by t.id;\r\n"
				+ "";

		// まとめる
		PreparedStatement pStmt = conn.prepareStatement(sql);

		//?のための文
		pStmt.setInt(1, id);

		// SELECT文を実行し、結果表を取得する
		ResultSet rs = pStmt.executeQuery();

		//タスク名、担当者、ステータス、優先度、期間、予定/実績工数、進捗を持ってきたい
		while (rs.next()) {
			AllDTO dto = new AllDTO();
			dto.setTaskId(rs.getInt("id"));
			dto.setCaseId(rs.getInt("case_id"));
			dto.setTaskName(rs.getString("task_name"));
			dto.setCaseId(rs.getInt("manager_id"));//ここ分からん
			dto.setTaskStatus(rs.getString("task_status"));
			dto.setTaskPriority(rs.getString("task_priority"));
			dto.setDeadline(rs.getString("deadline"));

			dto.setTaskPlannedHours(rs.getBigDecimal("task_planned_hours"));
			dto.setTaskProgressRate(rs.getInt("progress_rate"));

			allDTOs.add(dto);
		}
		return allDTOs;
	}

	/**
	 * 工数を持ってくるメソッド
	 * @param id 
	 * @return ArrayList<AllDTO>
	 * @throws SQLException
	 */
	public ArrayList<AllDTO> selectWorks(int id) throws SQLException {
		ArrayList<AllDTO> allDTOs = new ArrayList<AllDTO>();
		//sql文
		String sql = "select works.* ,users.user_name,tasks.task_name from works\r\n"
				+ "join users\r\n"
				+ "on works.user_id = users.id\r\n"
				+ "join tasks\r\n"
				+ "on works.task_id = tasks.id"
				+ "WHERE case_id=?";

		// まとめる
		PreparedStatement pStmt = conn.prepareStatement(sql);

		//?のための文
		pStmt.setInt(1, id);

		ResultSet rs = pStmt.executeQuery();

		//作業日、タスク名、担当者、工数、作業内容を持ってくる
		while (rs.next()) {
			AllDTO dto = new AllDTO();
			dto.setWorkDate(rs.getString("work_date"));
			dto.setTaskName(rs.getString("task_name"));
			dto.setUserName(rs.getString("user_name"));
			dto.setActualHours(rs.getBigDecimal("actual_hours"));
			dto.setWorkDescription(rs.getString("work_description"));

		}
		return allDTOs;
	}

}
