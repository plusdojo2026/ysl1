package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.AllDTO;
import dto.CasesDTO;
import dto.UsersDTO;

/**
 * casesテーブルを操作するDAOクラス。
 * 
 * @author 横山
 */
public class CasesDAO {

	private final Connection conn;

	/**
	 * コネクションを保持するコンストラクタ
	 *
	 * @param conn データベース接続情報
	 */
	public CasesDAO(Connection conn) {
		this.conn = conn;
	}

	/**
	 * CASE001を数値の1へ変換する。
	 *
	 * CasesDTOのcaseCodeがintであるために使用する。
	 *
	 * @param caseCode DBの案件コード
	 * @return 数値の案件コード
	 * @throws SQLException 案件コードの形式が不正な場合
	 */
	private int parseCaseCode(String caseCode) throws SQLException {

		if (caseCode == null || caseCode.isBlank()) {
			return 0;
		}

		String numberPart = caseCode.trim()
				.replaceFirst("(?i)^CASE", "");

		try {
			return Integer.parseInt(numberPart);

		} catch (NumberFormatException e) {
			throw new SQLException(
					"case_codeの形式が不正です: " + caseCode,
					e);
		}
	}

	/**
	 * 数値の案件コードをDB保存形式へ変換する。
	 *
	 * 1  → CASE001
	 * 12 → CASE012
	 *
	 * @param caseCode 数値の案件コード
	 * @return DB保存用の案件コード
	 */
	private String formatCaseCode(int caseCode) {
		return String.format("CASE%03d", caseCode);
	}

	/**
	 * 案件一覧用SQLを作成する。
	 *
	 * GROUP BYを使わず、サブクエリで集計することで
	 * ONLY_FULL_GROUP_BYによるエラーを回避する。
	 *
	 * @return 案件一覧用SQL
	 */
	private String createCasesListSql() {

		return "SELECT " +
				"    c.*, " +

				"    COALESCE((" +
				"        SELECT SUM(w.actual_hours) " +
				"        FROM tasks t " +
				"        JOIN works w ON t.id = w.task_id " +
				"        WHERE t.case_id = c.id" +
				"    ), 0) AS actual_hours_sum, " +

				"    (" +
				"        SELECT COUNT(*) " +
				"        FROM tasks t " +
				"        WHERE t.case_id = c.id" +
				"    ) AS task_count, " +

				"    (" +
				"        SELECT COUNT(*) " +
				"        FROM tasks t " +
				"        WHERE t.case_id = c.id " +
				"          AND t.task_status = '完了'" +
				"    ) AS completed_task_count, " +

				"    (" +
				"        SELECT GROUP_CONCAT(" +
				"            DISTINCT u.user_name " +
				"            ORDER BY u.user_name " +
				"            SEPARATOR ', '" +
				"        ) " +
				"        FROM tasks t " +
				"        JOIN works w ON t.id = w.task_id " +
				"        JOIN users u ON w.user_id = u.id " +
				"        WHERE t.case_id = c.id" +
				"    ) AS user_names " +

				"FROM `cases` c ";
	}

	/**
	 * ResultSetの案件一覧データをAllDTOへ移し替える。
	 *
	 * @param rs ResultSet
	 * @return AllDTO
	 * @throws SQLException SQLデータ取得エラー
	 */
	private AllDTO createAllDTOFromCaseResult(ResultSet rs)
			throws SQLException {

		AllDTO dto = new AllDTO();

		dto.setCaseId(rs.getInt("id"));
		dto.setCaseName(rs.getString("case_name"));

		// AllDTO.caseCodeはStringなので変換しない
		dto.setCaseCode(rs.getString("case_code"));

		dto.setCustomerName(rs.getString("customer_name"));
		dto.setCasePriority(rs.getString("case_priority"));
		dto.setPmId(rs.getInt("pm_id"));
		dto.setCaseStatus(rs.getString("case_status"));
		dto.setStartDate(rs.getString("start_date"));
		dto.setPlannedEndDate(
				rs.getString("planned_end_date"));
		dto.setCaseDescription(
				rs.getString("case_description"));
		dto.setCasePlannedHours(
				rs.getInt("case_planned_hours"));

		dto.setUserName(rs.getString("user_names"));
		dto.setCaseSum(rs.getInt("task_count"));
		dto.setCaseNow(rs.getInt("completed_task_count"));

		BigDecimal actualHoursSum = rs.getBigDecimal("actual_hours_sum");

		if (actualHoursSum == null) {
			actualHoursSum = BigDecimal.ZERO;
		}

		dto.setActualHoursSum(actualHoursSum);

		int taskCount = rs.getInt("task_count");
		int completedTaskCount = rs.getInt("completed_task_count");

		if (taskCount > 0) {
			int progressRate = completedTaskCount * 100 / taskCount;

			dto.setCaseProgressRate(progressRate);
		} else {
			dto.setCaseProgressRate(0);
		}

		return dto;
	}

	/**
	 * 案件詳細を取得する。
	 *
	 * @param id 案件ID
	 * @return 案件詳細
	 * @throws SQLException SQL実行エラー
	 */
	public ArrayList<AllDTO> selectCases(int id)
			throws SQLException {

		ArrayList<AllDTO> allDTOs = new ArrayList<>();

		String sql = createCasesListSql() +
				"WHERE c.id = ?";

		System.out.println("【selectCases SQL】");
		System.out.println(sql);

		try (PreparedStatement pStmt = conn.prepareStatement(sql)) {

			pStmt.setInt(1, id);

			try (ResultSet rs = pStmt.executeQuery()) {

				while (rs.next()) {
					allDTOs.add(
							createAllDTOFromCaseResult(rs));
				}
			}
		}

		return allDTOs;
	}

	/**
	 * 指定案件のタスクを取得する。
	 *
	 * @param id 案件ID
	 * @return タスク一覧
	 * @throws SQLException SQL実行エラー
	 */
	public ArrayList<AllDTO> selectTasks(int id)
			throws SQLException {

		ArrayList<AllDTO> allDTOs = new ArrayList<>();

		String sql = "SELECT " +
				"    t.*, " +
				"    u.user_name, " +
				"    COALESCE((" +
				"        SELECT SUM(w.actual_hours) " +
				"        FROM works w " +
				"        WHERE w.task_id = t.id" +
				"    ), 0) AS actual_hours_sum " +
				"FROM tasks t " +
				"LEFT JOIN users u " +
				"    ON t.manager_id = u.id " +
				"WHERE t.case_id = ? " +
				"ORDER BY t.id";

		System.out.println("【selectTasks SQL】");
		System.out.println(sql);

		try (PreparedStatement pStmt = conn.prepareStatement(sql)) {

			pStmt.setInt(1, id);

			try (ResultSet rs = pStmt.executeQuery()) {

				while (rs.next()) {

					AllDTO dto = new AllDTO();

					dto.setTaskId(rs.getInt("id"));
					dto.setCaseId(rs.getInt("case_id"));
					dto.setTaskName(
							rs.getString("task_name"));

					// manager_idはcaseIdではなくmanagerId
					dto.setManagerId(
							rs.getInt("manager_id"));

					dto.setTaskStatus(
							rs.getString("task_status"));
					dto.setTaskPriority(
							rs.getString("task_priority"));
					dto.setDeadline(
							rs.getString("deadline"));
					dto.setTaskPlannedHours(
							rs.getBigDecimal(
									"task_planned_hours"));
					dto.setTaskProgressRate(
							rs.getInt("progress_rate"));
					dto.setUserName(
							rs.getString("user_name"));
					dto.setActualHours(
							rs.getBigDecimal(
									"actual_hours_sum"));

					allDTOs.add(dto);
				}
			}
		}

		return allDTOs;
	}

	/**
	 * 指定案件の工数を取得する。
	 *
	 * @param id 案件ID
	 * @return 工数一覧
	 * @throws SQLException SQL実行エラー
	 */
	public ArrayList<AllDTO> selectWorks(int id)
			throws SQLException {

		ArrayList<AllDTO> allDTOs = new ArrayList<>();

		String sql = "SELECT " +
				"    w.*, " +
				"    u.user_name, " +
				"    t.task_name " +
				"FROM works w " +
				"JOIN users u " +
				"    ON w.user_id = u.id " +
				"JOIN tasks t " +
				"    ON w.task_id = t.id " +
				"WHERE t.case_id = ? " +
				"ORDER BY w.work_date DESC, w.id DESC";

		System.out.println("【selectWorks SQL】");
		System.out.println(sql);

		try (PreparedStatement pStmt = conn.prepareStatement(sql)) {

			pStmt.setInt(1, id);

			try (ResultSet rs = pStmt.executeQuery()) {

				while (rs.next()) {

					AllDTO dto = new AllDTO();

					dto.setWorkId(rs.getInt("id"));
					dto.setWorkDate(
							rs.getString("work_date"));
					dto.setTaskName(
							rs.getString("task_name"));
					dto.setUserName(
							rs.getString("user_name"));
					dto.setActualHours(
							rs.getBigDecimal("actual_hours"));
					dto.setWorkDescription(
							rs.getString(
									"work_description"));

					// 原来的代码漏了这一行
					allDTOs.add(dto);
				}
			}
		}

		return allDTOs;
	}

	/**
	 * 案件一覧の初期画面を取得する。
	 *
	 * @return 案件一覧
	 * @throws SQLException SQL実行エラー
	 */
	public ArrayList<AllDTO> initialize()
			throws SQLException {

		ArrayList<AllDTO> allDTOs = new ArrayList<>();

		String sql = createCasesListSql() +
				"ORDER BY c.id DESC";

		System.out.println("【initialize SQL】");
		System.out.println(sql);

		try (
				PreparedStatement pStmt = conn.prepareStatement(sql);

				ResultSet rs = pStmt.executeQuery()) {
			while (rs.next()) {
				allDTOs.add(
						createAllDTOFromCaseResult(rs));
			}
		}

		return allDTOs;
	}

	/**
	 * 案件編集用データを取得する。
	 *
	 * @param id 案件ID
	 * @return 案件情報
	 * @throws SQLException SQL実行エラー
	 */
	public CasesDTO casesEdit(int id)
			throws SQLException {

		CasesDTO dto = new CasesDTO();

		String sql = "SELECT " +
				"    c.*, " +
				"    u.user_name " +
				"FROM `cases` c " +
				"LEFT JOIN users u " +
				"    ON c.pm_id = u.id " +
				"WHERE c.id = ?";

		System.out.println("【casesEdit SQL】");
		System.out.println(sql);

		try (PreparedStatement pStmt = conn.prepareStatement(sql)) {

			// 原来的代码漏了这个参数
			pStmt.setInt(1, id);

			try (ResultSet rs = pStmt.executeQuery()) {

				if (rs.next()) {

					dto.setId(rs.getInt("id"));
					dto.setCaseName(
							rs.getString("case_name"));

					/*
					 * CasesDTO.caseCode是int时：
					 * CASE001 → 1
					 */
					dto.setCaseCode(
							parseCaseCode(
									rs.getString("case_code")));

					dto.setCustomerName(
							rs.getString("customer_name"));
					dto.setPmId(rs.getInt("pm_id"));
					dto.setCaseStatus(
							rs.getString("case_status"));
					dto.setCasePriority(
							rs.getString("case_priority"));
					dto.setStartDate(
							rs.getString("start_date"));
					dto.setPlannedEndDate(
							rs.getString(
									"planned_end_date"));
					dto.setCaseDescription(
							rs.getString(
									"case_description"));
					dto.setCasePlannedHours(
							rs.getInt(
									"case_planned_hours"));
				}
			}
		}

		return dto;
	}

	/**
	 * すべてのPM名を取得する。
	 *
	 * @return PM一覧
	 * @throws SQLException SQL実行エラー
	 */
	public ArrayList<UsersDTO> pmList()
			throws SQLException {

		ArrayList<UsersDTO> usersList = new ArrayList<>();

		String sql = "SELECT " +
				"    id, " +
				"    user_name " +
				"FROM users " +
				"ORDER BY user_name";

		System.out.println("【pmList SQL】");
		System.out.println(sql);

		try (
				PreparedStatement pStmt = conn.prepareStatement(sql);

				ResultSet rs = pStmt.executeQuery()) {
			while (rs.next()) {

				UsersDTO dto = new UsersDTO();

				dto.setUserId(rs.getInt("id"));
				dto.setUserName(
						rs.getString("user_name"));

				usersList.add(dto);
			}
		}

		return usersList;
	}

	/**
	 * すべての案件を取得する。
	 *
	 * @return 案件一覧
	 * @throws SQLException SQL実行エラー
	 */
	public ArrayList<AllDTO> selectAll()
			throws SQLException {

		// initializeと同じSQLを重複して書かない
		return initialize();
	}

	/**
	 * 案件を新規登録する。
	 *
	 * @param dto 案件情報
	 * @return 登録件数
	 * @throws SQLException SQL実行エラー
	 */
	public int insert(CasesDTO dto)
			throws SQLException {

		String sql = "INSERT INTO `cases` (" +
				"    case_name, " +
				"    case_code, " +
				"    customer_name, " +
				"    case_priority, " +
				"    pm_id, " +
				"    case_status, " +
				"    start_date, " +
				"    planned_end_date, " +
				"    case_description, " +
				"    case_planned_hours" +
				") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		System.out.println("【insert SQL】");
		System.out.println(sql);

		try (PreparedStatement pStmt = conn.prepareStatement(sql)) {

			pStmt.setString(
					1,
					valueOrEmpty(dto.getCaseName()));

			/*
			 * CasesDTO.caseCodeがintなので、
			 * 数字1をCASE001に変換して保存する。
			 */
			pStmt.setString(
					2,
					formatCaseCode(dto.getCaseCode()));

			pStmt.setString(
					3,
					valueOrEmpty(dto.getCustomerName()));
			pStmt.setString(
					4,
					valueOrEmpty(dto.getCasePriority()));
			pStmt.setInt(5, dto.getPmId());
			pStmt.setString(
					6,
					valueOrEmpty(dto.getCaseStatus()));
			pStmt.setString(
					7,
					valueOrEmpty(dto.getStartDate()));
			pStmt.setString(
					8,
					valueOrEmpty(dto.getPlannedEndDate()));
			pStmt.setString(
					9,
					valueOrEmpty(dto.getCaseDescription()));
			pStmt.setInt(
					10,
					dto.getCasePlannedHours());

			return pStmt.executeUpdate();
		}
	}

	/**
	 * 案件を更新する。
	 *
	 * @param dto 案件情報
	 * @return 更新件数
	 * @throws SQLException SQL実行エラー
	 */
	public int update(CasesDTO dto)
			throws SQLException {

		String sql = "UPDATE `cases` SET " +
				"    case_name = ?, " +
				"    case_code = ?, " +
				"    customer_name = ?, " +
				"    case_priority = ?, " +
				"    pm_id = ?, " +
				"    case_status = ?, " +
				"    start_date = ?, " +
				"    planned_end_date = ?, " +
				"    case_description = ?, " +
				"    case_planned_hours = ? " +
				"WHERE id = ?";

		System.out.println("【update SQL】");
		System.out.println(sql);

		try (PreparedStatement pStmt = conn.prepareStatement(sql)) {

			pStmt.setString(
					1,
					valueOrEmpty(dto.getCaseName()));

			// 数字1 → CASE001
			pStmt.setString(
					2,
					formatCaseCode(dto.getCaseCode()));

			pStmt.setString(
					3,
					valueOrEmpty(dto.getCustomerName()));
			pStmt.setString(
					4,
					valueOrEmpty(dto.getCasePriority()));
			pStmt.setInt(5, dto.getPmId());
			pStmt.setString(
					6,
					valueOrEmpty(dto.getCaseStatus()));
			pStmt.setString(
					7,
					valueOrEmpty(dto.getStartDate()));
			pStmt.setString(
					8,
					valueOrEmpty(dto.getPlannedEndDate()));
			pStmt.setString(
					9,
					valueOrEmpty(dto.getCaseDescription()));
			pStmt.setInt(
					10,
					dto.getCasePlannedHours());
			pStmt.setInt(11, dto.getId());

			return pStmt.executeUpdate();
		}
	}

	/**
	 * nullを空文字へ変換する。
	 *
	 * @param value 文字列
	 * @return nullの場合は空文字
	 */
	private String valueOrEmpty(String value) {

		if (value == null) {
			return "";
		}

		return value;
	}
}
