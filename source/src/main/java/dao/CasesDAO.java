package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.AllDTO;

public class CasesDAO {

	public Connection conn = null;

	//コネクションを保持するコンストラクタ
	public CasesDAO(Connection conn) {
		this.conn = conn;
	}

	//案件詳細を持ってくるメソッド
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

	//タスクを持ってくるメソッド
	public ArrayList<AllDTO> selectTasks(int id) throws SQLException {
		ArrayList<AllDTO> allDTOs = new ArrayList<AllDTO>();

		String sql = "SELECT * FROM tasks JOIN WHERE case_id=?";
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
			dto.setProgressRate(rs.getInt("progress_rate"));

			allDTOs.add(dto);
		}
		return allDTOs;
	}

	//工数を持ってくるメソッド
	public ArrayList<AllDTO> selectWorks(int id) throws SQLException {
		ArrayList<AllDTO> allDTOs = new ArrayList<AllDTO>();
		//sql文
		String sql = "SELECT * FROM taskss WHERE case_id=?";

		// まとめる
		PreparedStatement pStmt = conn.prepareStatement(sql);

		//?のための文
		pStmt.setInt(1, id);

		ResultSet rs = pStmt.executeQuery();

		while (rs.next()) {
			AllDTO dto = new AllDTO();

		}
		return allDTOs;
	}

}
