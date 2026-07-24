package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.AllDTO;
import dto.CasesDTO;
import dto.UsersDTO;

/**
 * Casesrsテーブルを操作するDAOクラス。
 *
 * <p>
 * CasesDTO情報の案件一覧の表示、検索、条件検索、案件登録および更新などの
 * データベース操作を行う。
 * </p>
 *
 *
 *
 * @author 横山
 */

public class CasesDAO {

	public Connection conn = null;

	/**
	 * コネクションを保持するコンストラクタ
	 * @param conn データベース接続情報
	 * 
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
		String sql = "select"
				+ "	cases.*,"
				+ "	sum(distinct works.actual_hours),"
				+ "	COUNT(DISTINCT tasks.id) AS task_count,"
				+ "	COUNT(DISTINCT CASE WHEN tasks.task_status = '完了' THEN tasks.id END) "
				+ "AS completed_task_count,"
				+ "    GROUP_CONCAT(DISTINCT users.user_name) AS user_names "
				+ "from  cases "
				+ "join tasks  "
				+ "on cases.id = tasks.case_id "
				+ "join works "
				+ "on tasks.id = works.task_id "
				+ "join users "
				+ "on users.id = works.user_id "
				+ "WHERE case_id=? "
				+ "group by cases.id";

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
			dto.setUserName(rs.getString("user_name"));

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
			dto.setUserName(rs.getString("user_name"));

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

	/**
	 * //案件一覧の初期画面の表示のメソッド
	 * @return ArrayList<AllDTO>
	 * @throws SQLException
	 */
	public ArrayList<AllDTO> initialize() throws SQLException {
		ArrayList<AllDTO> allDTOs = new ArrayList<AllDTO>();

		//sql文
		String sql = "select cases.*, sum(distinct works.actual_hours),COUNT(DISTINCT tasks.id) AS task_count,"
				+ "COUNT(DISTINCT CASE WHEN tasks.task_status = '完了' THEN tasks.id END) AS completed_task_count,"
				+ "GROUP_CONCAT(DISTINCT users.user_name) AS user_names"
				+ "from cases join tasks on cases.id = tasks.case_id join works on tasks.id = works.task_id"
				+ "join users on users.id = works.user_id group by cases.id";

		//デバッグ（SQL文の確認用）
		System.out.println(sql);

		// まとめる
		PreparedStatement pStmt = conn.prepareStatement(sql);

		// SELECT文を実行し、結果表を取得する
		ResultSet rs = pStmt.executeQuery();

		//移し替え
		while (rs.next()) {
			AllDTO dto = new AllDTO();
			dto.setCaseId(rs.getInt("id"));
			dto.setCaseName(rs.getString("case_name"));
			dto.setCaseCode(rs.getInt("case_code"));
			dto.setCustomerName(rs.getString("customer_name"));
			dto.setUserName(rs.getString("user_names"));
			dto.setCaseStatus(rs.getString("case_status"));
			dto.setCasePriority(rs.getString("case_priority"));
			dto.setStartDate(rs.getString("start_date"));
			dto.setPlannedEndDate(rs.getString("planned_end_date"));
			dto.setCaseNow(rs.getInt("case_now"));
			dto.setCaseSum(rs.getInt("case_sum"));
			dto.setActualHoursSum(rs.getBigDecimal("actual_hours_sum"));

			allDTOs.add(dto);
		}

		return allDTOs;
	}

	public CasesDTO casesEdit(int id) throws SQLException {
		CasesDTO dto = new CasesDTO();

		//sql文
		String sql = "SELECT cases.*, u.user_name FROM cases JOIN users AS u ON cases.pm_id=u.id WHERE cases.id=?";

		//デバッグ（SQL文の確認用）
		System.out.println(sql);

		// まとめる
		PreparedStatement pStmt = conn.prepareStatement(sql);
		pStmt.executeQuery();

		// SELECT文を実行し、結果表を取得する
		ResultSet rs = pStmt.executeQuery();

		//移し替え
		while (rs.next()) {

			dto.setId(rs.getInt("id"));
			dto.setCaseName(rs.getString("case_name"));
			dto.setCaseCode(rs.getInt("case_code"));
			dto.setCustomerName(rs.getString("customer_name"));
			dto.setPmId(rs.getInt("pm_id"));
			dto.setCaseStatus(rs.getString("case_status"));
			dto.setCasePriority(rs.getString("case_priority"));
			dto.setStartDate(rs.getString("start_date"));
			dto.setPlannedEndDate(rs.getString("planned_end_date"));
			dto.setCaseDescription(rs.getString("caseDescription"));
			dto.setPlannedEndDate(rs.getString("case_planned_hours"));

		}
		return dto;

	}

	/**
	 * - すべてのPM名を取得する -
	 * 
	 * 	 * @author 横山大貴
	 */
	//PM一覧を取得するメソッド(案件編集や案件登録の際にPMをプルダウンで一覧で出すため)
	public ArrayList<UsersDTO> selectPmList() throws SQLException {
		ArrayList<UsersDTO> usersList = new ArrayList<>();

		String sql = "SELECT id, user_name FROM users ORDER BY user_name";
		//デバッグ（SQL文の確認用）
		System.out.println(sql);

		// まとめる
		PreparedStatement pStmt = conn.prepareStatement(sql);

		// SELECT文を実行し、結果表を取得する
		ResultSet rs = pStmt.executeQuery();

		//移し替え
		while (rs.next()) {

			UsersDTO dto = new UsersDTO();

			dto.setUserId(rs.getInt("id"));
			dto.setUserName(rs.getString("user_name"));

			usersList.add(dto);

		}
		return usersList;

	}

	/**
	 * - すべての案件を表示する -
	 * 
	 * 
	 * @author 横山大貴
	 */

	//すべての案件を表示するメソッドselectAll
	public ArrayList<AllDTO> selectAll() throws SQLException {
		ArrayList<AllDTO> allDTOs = new ArrayList<AllDTO>();

		//sql文
		String sql = "select cases.*, sum(distinct works.actual_hours),COUNT(DISTINCT tasks.id) AS task_count,"
				+ "COUNT(DISTINCT CASE WHEN tasks.task_status = '完了' THEN tasks.id END) AS completed_task_count,"
				+ "GROUP_CONCAT(DISTINCT users.user_name) AS user_names"
				+ "from cases join tasks on cases.id = tasks.case_id join works on tasks.id = works.task_id"
				+ "join users on users.id = works.user_id group by cases.id";

		//デバッグ（SQL文の確認用）
		System.out.println(sql);

		// まとめる
		PreparedStatement pStmt = conn.prepareStatement(sql);

		// SELECT文を実行し、結果表を取得する
		ResultSet rs = pStmt.executeQuery();

		//移し替え
		while (rs.next()) {
			AllDTO dto = new AllDTO();
			dto.setCaseId(rs.getInt("id"));
			dto.setCaseName(rs.getString("case_name"));
			dto.setCaseCode(rs.getInt("case_code"));
			dto.setCustomerName(rs.getString("customer_name"));
			dto.setUserName(rs.getString("user_names"));
			dto.setCaseStatus(rs.getString("case_status"));
			dto.setCasePriority(rs.getString("case_priority"));
			dto.setStartDate(rs.getString("start_date"));
			dto.setPlannedEndDate(rs.getString("planned_end_date"));
			dto.setCaseNow(rs.getInt("case_now"));
			dto.setCaseSum(rs.getInt("case_sum"));
			dto.setActualHoursSum(rs.getBigDecimal("actual_hours_sum"));

			allDTOs.add(dto);
		}

		return allDTOs;
	}

	/**
	 * @param dto
	 * @return insert
	 * @throws SQLException
	 * 案件を新規登録する
	 */
	public int insert(CasesDTO dto) throws SQLException {
		int ans = 0;
		// SELECT文を準備する
		String sql = "INSERT INTO cases VALUES(0, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		//デバッグ（SQL文の確認用）
		System.out.println(sql);

		// まとめる
		PreparedStatement pStmt = conn.prepareStatement(sql);

		// SQL文を完成させる
		if (dto.getCaseName() != null) {
			pStmt.setString(1, dto.getCaseName());
		} else {
			pStmt.setString(1, "");
		}

		pStmt.setInt(2, dto.getCaseCode());

		if (dto.getCustomerName() != null) {
			pStmt.setString(3, dto.getCustomerName());
		} else {
			pStmt.setString(3, "");
		}
		if (dto.getCasePriority() != null) {
			pStmt.setString(4, dto.getCasePriority());
		} else {
			pStmt.setString(4, "");
		}

		pStmt.setInt(5, dto.getPmId());

		if (dto.getCaseStatus() != null) {
			pStmt.setString(6, dto.getCaseStatus());
		} else {
			pStmt.setString(6, "");
		}
		if (dto.getStartDate() != null) {
			pStmt.setString(7, dto.getStartDate());
		} else {
			pStmt.setString(7, "");
		}
		if (dto.getPlannedEndDate() != null) {
			pStmt.setString(8, dto.getPlannedEndDate());
		} else {
			pStmt.setString(8, "");
		}
		if (dto.getCaseDescription() != null) {
			pStmt.setString(9, dto.getCaseDescription());
		} else {
			pStmt.setString(9, "");
		}
		pStmt.setInt(10, dto.getCasePlannedHours());

		// SELECT文を実行し、結果表を取得する
		ans = pStmt.executeUpdate();

		//serviceに返却する
		return ans;

	}

	/**
	 * @param dto
	 * @return update
	 * @throws SQLException
	 * 案件を編集する
	 */
	public int update(CasesDTO dto) throws SQLException {
		int ans = 0;
		// SELECT文を準備する
		String sql = "INSERT INTO cases VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		//デバッグ（SQL文の確認用）
		System.out.println(sql);

		// まとめる
		PreparedStatement pStmt = conn.prepareStatement(sql);

		// SQL文を完成させる
		pStmt.setInt(1, dto.getId());

		if (dto.getCaseName() != null) {
			pStmt.setString(2, dto.getCaseName());
		} else {
			pStmt.setString(2, "");
		}

		pStmt.setInt(3, dto.getCaseCode());

		if (dto.getCustomerName() != null) {
			pStmt.setString(4, dto.getCustomerName());
		} else {
			pStmt.setString(4, "");
		}
		if (dto.getCasePriority() != null) {
			pStmt.setString(5, dto.getCasePriority());
		} else {
			pStmt.setString(5, "");
		}

		pStmt.setInt(6, dto.getPmId());

		if (dto.getCaseStatus() != null) {
			pStmt.setString(7, dto.getCaseStatus());
		} else {
			pStmt.setString(7, "");
		}
		if (dto.getStartDate() != null) {
			pStmt.setString(8, dto.getStartDate());
		} else {
			pStmt.setString(8, "");
		}
		if (dto.getPlannedEndDate() != null) {
			pStmt.setString(9, dto.getPlannedEndDate());
		} else {
			pStmt.setString(9, "");
		}
		if (dto.getCaseDescription() != null) {
			pStmt.setString(10, dto.getCaseDescription());
		} else {
			pStmt.setString(10, "");
		}
		pStmt.setInt(11, dto.getCasePlannedHours());

		// SELECT文を実行し、結果表を取得する
		ans = pStmt.executeUpdate();

		//serviceに返却する
		return ans;

	}

}
