package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.AllDTO;

/**
 * Worksテーブルを操作するDAOクラス。
 *
 * <p>
 * WorksDTO情報の登録、削除、一覧表示などの
 * データベース操作を行う。
 * </p>
 *
 * <p>
 * DB接続、PreparedStatement生成、パラメータ設定、
 * ResultSetからDTOへの変換処理は親クラスBaseDAOで行う。
 * </p>
 *
 * <p>
 * テーブル名はWorksDTOに定義された@Tableから自動取得する。
 * </p>
 *
 * @author YSL土屋莉里子
 */
public class WorksDAO {
public Connection conn = null;

/**
 * WorksDAOのコンストラクタ。
 *
 * <p>
 * 親クラスBaseDAOにWorksDTOクラスを渡す。
 * BaseDAOはWorksDTOに設定された@Tableから
 * テーブル名を自動取得する。
 * </p>
 */
	//コネクションを保持するコンストラクタ
	public WorksDAO(Connection conn) {
		this.conn=conn;
	}
	
	
	/**
	 * 画面上に入力されたデータをworksテーブルに保存する。
	 * 
	 *
	 * @param works 工数情報
	 * @return pStmt.executeUpdate(）
	 */	
	//工数を登録するメソッド---------------------------------------
			public int worksInsert(int userId, int taskId, String workDate ,BigDecimal actualHours ,String workDescription) throws SQLException{
				int ans = 0;
				// SELECT文を準備する
				String sql ="INSERT INTO works(user_id,task_id,work_date,actual_hours,work_description) VALUES(?,?,?,?,?)";
				//デバッグ（SQL文の確認用）
				System.out.println(sql);
				
				// まとめる
				PreparedStatement pStmt = conn.prepareStatement(sql);
				
				// ?に値をセット
				pStmt.setInt(1, userId);
				pStmt.setInt(2, taskId);
				pStmt.setString(3, workDate);
				pStmt.setBigDecimal(4, actualHours);
				pStmt.setString(5, workDescription);

				// INSERT文を実行
				ans = pStmt.executeUpdate();
				
				
				//serviceに返却する
				return ans;
			}
	
	/**
	* 指定された工数IDの工数を削除する。
	*
	* @param workId 削除対象工数ID
	* @return pStmt.executeUpdate();
	*/
	//工数を削除するメソッド---------------------------------------
				public int worksDelete(int id) throws SQLException{
					int ans = 0;
					// SQL文を準備する
					String sql = "DELETE FROM works WHERE id=?";
					//デバッグ（SQL文の確認用）
					System.out.println(sql);
					
					PreparedStatement pStmt = conn.prepareStatement(sql);
					
					
					// SQL文を完成させる
					pStmt.setInt(1,id);

					
					// DELETE文を実行
					ans = pStmt.executeUpdate();
					
					
					//serviceに返却する
					return ans;
				}
	
	/**
	* 月次集計画面のサマリーを表示する
	*
	* @param month 月が選択されていない場合は
	* 当月の月次集計が表示される
	* @return AllDTO selectSum
	*/			
	//月次集計画面のサマリー（月合計工数、集計案件数、稼働メンバー数）---------------------------------------			
				public AllDTO selectSum(String month) throws SQLException{
					AllDTO selectSum = new AllDTO();
					
					// ①月合計工数のSQL文を準備する
					String sql1 = "select sum(actual_hours) as monthly_total_hours from works where date_format(work_date, '%Y-%m')=?";
					
					//デバッグ（SQL文の確認用）
					System.out.println(sql1);
					
					PreparedStatement pStmt1 = conn.prepareStatement(sql1);
					
					// ?に値をセット
					pStmt1.setString(1, month);
					
					// SELECT文を実行し、結果表を取得する
					ResultSet rs1 = pStmt1.executeQuery();
					
			
					if(rs1.next()) {
						selectSum.setMonthlyTotalHours(
								rs1.getBigDecimal("monthly_total_hours"));
					}
					
					//②集計案件数のSQL文を準備する
					String sql2 = "select count(distinct t.case_id) as case_count from works w join tasks t on w.task_id = t.id where date_format(work_date, '%Y-%m')=?";
					
					//デバッグ（SQL文の確認用）
					System.out.println(sql2);
					
					PreparedStatement pStmt2 = conn.prepareStatement(sql2);
					
					// ?に値をセット
					pStmt2.setString(1, month);
					
					// SELECT文を実行し、結果表を取得する
					ResultSet rs2 = pStmt2.executeQuery();
					
			
					if(rs2.next()) {
						selectSum.setCaseCount(
								rs2.getInt("case_count"));
					}
					
					//③稼働メンバー数のSQL文を準備する
					String sql3 = "select count(distinct user_id) as member_count from works where date_format(work_date, '%Y-%m')=?";
					
					//デバッグ（SQL文の確認用）
					System.out.println(sql3);
					
					PreparedStatement pStmt3 = conn.prepareStatement(sql3);
					
					// ?に値をセット
					pStmt3.setString(1, month);
					
					// SELECT文を実行し、結果表を取得する
					ResultSet rs3 = pStmt3.executeQuery();
					
			
					if(rs3.next()) {
						selectSum.setMemberCount(
								rs3.getInt("member_count"));
					}
					//serviceに返却する
					return selectSum;
				}
	/**
	* 月次集計画面の案件別集計を表示する
	*
	* @param month 月が選択されていない場合は
	* 当月の月次集計が表示される
	* @return ArrayList<AllDTO> caseList
	*/						
	//月次集計画面の案件別集計				
				public ArrayList<AllDTO> selectCaseSum(String month) throws SQLException{
					ArrayList<AllDTO> caseList = new ArrayList<AllDTO>();
					
					// SQL文を準備する
					String sql ="select c.case_code, c.case_name, sum(w.actual_hours) as actual_hours_sum, c.case_planned_hours, count(distinct t.id) as case_sum,"
								+ " count(distinct case when t.task_status='完了' then t.id end) as case_now from cases c join tasks t on c.id = t.case_id"
								+ " left join works w on t.id = w.task_id where date_format(work_date, '%Y-%m')=? "
								+ "group by c.case_code, c.case_name, c.case_planned_hours";
	
					//デバッグ（SQL文の確認用）
					System.out.println(sql);
					
					PreparedStatement pStmt = conn.prepareStatement(sql);
					
					// ?に値をセット
					pStmt.setString(1, month);
					
					// SELECT文を実行し、結果表を取得する
					ResultSet rs = pStmt.executeQuery();
					
					//AllDTOの変数にDBから取得してきた値をセット
					while(rs.next()) {
						AllDTO dto=new AllDTO();
						dto.setCaseCode(rs.getString("case_code"));
						dto.setCaseName(rs.getString("case_name"));
						dto.setActualHoursSum(rs.getBigDecimal("actual_hours_sum"));
						dto.setCasePlannedHours(rs.getInt("case_planned_hours"));
						dto.setCaseSum(rs.getInt("case_sum"));
						dto.setCaseNow(rs.getInt("case_now"));
						caseList.add(dto);
					}
					
					//serviceに返却する
					return caseList;
				}
	/**
	* 月次集計画面のメンバー別集計を表示する
	*
	* @param month 月が選択されていない場合は
	* 当月の月次集計が表示される
	* @return ArrayList<AllDTO> userList
	*/		
	//月次集計画面のメンバー別集計				
				public ArrayList<AllDTO> selectMemberSum(String month) throws SQLException{
					ArrayList<AllDTO> userList = new ArrayList<AllDTO>();
					
					// SQL文を準備する
					String sql ="select u.user_name, sum(w.actual_hours) as actual_hours from users u join works w on u.id=w.user_id "
							+ "where date_format(work_date, '%Y-%m')=? group by u.id, u.user_name";			

					//デバッグ（SQL文の確認用）
					System.out.println(sql);
					
					PreparedStatement pStmt = conn.prepareStatement(sql);
					
					// ?に値をセット
					pStmt.setString(1, month);
					
					// SELECT文を実行し、結果表を取得する
					ResultSet rs = pStmt.executeQuery();
					
					
					while(rs.next()) {
						AllDTO dto=new AllDTO();
						dto.setUserName(rs.getString("user_name"));
						dto.setActualHours(rs.getBigDecimal("actual_hours"));
						userList.add(dto);
					}
					
					//serviceに返却する
					return userList;
				}
	/**
	* 月次集計画面の月ごとの工数ログを表示する
	*
	* @param month 月が選択されていない場合は
	* 当月の工数ログが表示される
	* @return ArrayList<AllDTO> workList
	*/	
	//月ごとの工数ログ
				public ArrayList<AllDTO> selectByMonth(String month) throws SQLException{
					ArrayList<AllDTO> workList = new ArrayList<AllDTO>();	
					
					String sql="select c.case_name, w.work_date, t.task_name, u.user_name, w.actual_hours, w.work_description from cases c "
							+ "join tasks t on c.id = t.case_id join works w on t.id = w.task_id join users u on u.id = w.user_id "
							+ "where date_format(work_date, '%Y-%m')=?";
					
					//デバッグ（SQL文の確認用）
					System.out.println(sql);
					
					PreparedStatement pStmt = conn.prepareStatement(sql);
					
					// ?に値をセット
					pStmt.setString(1, month);
					
					// SELECT文を実行し、結果表を取得する
					ResultSet rs = pStmt.executeQuery();
					
					
					while(rs.next()) {
						AllDTO dto=new AllDTO();
						dto.setCaseName(rs.getString("case_name"));
						dto.setWorkDate(rs.getString("work_date"));
						dto.setTaskName(rs.getString("task_name"));
						dto.setUserName(rs.getString("user_name"));
						dto.setActualHours(rs.getBigDecimal("actual_hours"));
						dto.setWorkDescription(rs.getString("work_description"));
						workList.add(dto);
					}
					
					return workList;
}
}
