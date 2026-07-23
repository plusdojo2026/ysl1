package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.AllDTO;


public class WorksDAO {
public Connection conn = null;
	
	//コネクションを保持するコンストラクタ
	public WorksDAO(Connection conn) {
		this.conn=conn;
	}
	
	//工数を登録するメソッド---------------------------------------
			public int worksInsert(String workDate ,String actualHours ,String workDescription) throws SQLException{
				int ans = 0;
				// SELECT文を準備する
				String sql ="INSERT INTO works VALUES(?,?,?)";
				//デバッグ（SQL文の確認用）
				System.out.println(sql);
				
				// まとめる
				PreparedStatement pStmt = conn.prepareStatement(sql);
				
				// ?に値をセット
				pStmt.setString(1, workDate);
				pStmt.setString(2, actualHours);
				pStmt.setString(3, workDescription);

				// SELECT文を実行し、結果表を取得する
				ans = pStmt.executeUpdate();
				
				
				//serviceに返却する
				return ans;
			}
				
	//工数を削除するメソッド---------------------------------------
				public int worksDelete(String id) throws SQLException{
					int ans = 0;
					// SQL文を準備する
					String sql = "DELETE FROM works WHERE id=?";
					//デバッグ（SQL文の確認用）
					System.out.println(sql);
					
					PreparedStatement pStmt = conn.prepareStatement(sql);
					
					
					// SQL文を完成させる
					pStmt.setString(1,id);

					
					// SELECT文を実行し、結果表を取得する
					ans = pStmt.executeUpdate();
					
					
					//serviceに返却する
					return ans;
				}
	
				
	//月次集計画面のサマリー（月合計工数、集計案件数、稼働メンバー数）---------------------------------------			
				public AllDTO selectSum(String month) throws SQLException{
					AllDTO selectSum = new AllDTO();
					
					// ①月合計工数のSQL文を準備する
					String sql1 = "select sum(actual_hours) from works where data_format(work_date, '%Y-%m')=?";
					
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
					String sql2 = "select count(distinct t.case_id) from works w join tasks t on w.id = t.id where data_format(work_date, '%Y-%m')=?";
					
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
					String sql3 = "select count(distinct user_id) from works where data_format(work_date, '%Y-%m')=?";
					
					//デバッグ（SQL文の確認用）
					System.out.println(sql3);
					
					PreparedStatement pStmt3 = conn.prepareStatement(sql3);
					
					// ?に値をセット
					pStmt3.setString(1, month);
					
					// SELECT文を実行し、結果表を取得する
					ResultSet rs3 = pStmt3.executeQuery();
					
			
					if(rs3.next()) {
						selectSum.setCaseCount(
								rs3.getInt("case_count"));
					}
					//serviceに返却する
					return selectSum;
				}
					
	//月次集計画面の案件別集計				
				public ArrayList<AllDTO> selectCaseSum(String month) throws SQLException{
					ArrayList<AllDTO> caseList = new ArrayList<AllDTO>();
					
					// SQL文を準備する
					String sql ="select c.case_code, c.case_name, sum(w.actual_hours), c.case_planned_hours, count(distinct t.id) as case_sum,"
								+ " count(distinct case when t.task_status='完了' then t.task_id end) as case_now from cases c join tasks t on c.id = t.case_id"
								+ " join works w on t.id = w.task_id where w.work_date >= ? and work_date <=?";
	
					//デバッグ（SQL文の確認用）
					System.out.println(sql);
					
					PreparedStatement pStmt = conn.prepareStatement(sql);
					
					// SELECT文を実行し、結果表を取得する
					ResultSet rs = pStmt.executeQuery();
					
					
					//serviceに返却する
					return caseList;
				}
	
	//月次集計画面のメンバー別集計				
				public ArrayList<AllDTO> selectMemberSum(String month) throws SQLException{
					ArrayList<AllDTO> userList = new ArrayList<AllDTO>();
					
					// SQL文を準備する
					String sql ="select u.user_name, w.actual_hours,";			

					//デバッグ（SQL文の確認用）
					System.out.println(sql);
					
					PreparedStatement pStmt = conn.prepareStatement(sql);
					
					// SELECT文を実行し、結果表を取得する
					ResultSet rs = pStmt.executeQuery();
					
					
					//serviceに返却する
					return userList;
				}
	
	
	
}
