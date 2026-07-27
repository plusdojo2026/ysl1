package service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.WorksDAO;
import dto.AllDTO;

/**
 * 
 * WorksServiceクラス。
 * <p>
 * TODO DAOとActionを繋がるサービス層。条件・結果を判断、処理を行う。
 * </p>
 *
 * @author YSL土屋莉里子
 */
public class WorksService extends DBAccess{
	
	public WorksService() {
		super.access();
	}
	

	/**
	 * 工数登録メソッド
	 *
	 * @param userId, taskId, workDate, actualHours, workdescription
	 * @return int
	 */
	//工数を登録するメソッド---------------------------------------
		public int worksInsert(int userId, int taskId, String workDate ,BigDecimal actualHours ,String workDescription) {
			//DAOに処理を任せる
			WorksDAO dao = new WorksDAO(conn);
			int ans=0;
			try {
				ans = dao.worksInsert(userId, taskId, workDate,actualHours,workDescription);
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			return ans;		
		}
	
	/**
	 * 工数削除メソッド
	 *
	 * @param id
	 * @return int
	 */
		
	//工数を削除するメソッド---------------------------------------
		public int worksDelete(int id) {
			//DAOに処理を任せる
			WorksDAO dao = new WorksDAO(super.conn);
			int ans=0;
			try {
				ans = dao.worksDelete(id);
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			return ans;		
			
		}
	
	/**
	* サマリー表示メソッド
	*
	* @param month
	* @return AllDTO
	*/	
		
	//サマリー（月合計工数、集計案件数、稼働メンバー数）---------------------------------------	
		public AllDTO selectSum(String month) {
			AllDTO selectSum =null;
			WorksDAO dao = new WorksDAO(super.conn);
			
			//月合計工数、集計案件数、稼働メンバー数
			 try {
				selectSum=dao.selectSum(month);
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			
			return selectSum;
		}
			
	/**
	 * 案件別集計メソッド
	 *
	 * @param month
	 * @return ArrayList<AllDTO>
	 */
	//案件別集計---------------------------------------
		public ArrayList<AllDTO> selectCaseSum(String month){
			ArrayList<AllDTO> caseSumList =null;
			WorksDAO dao = new WorksDAO(super.conn);
			
			
			//案件別集計
			try {
				caseSumList=dao.selectCaseSum(month);
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			//達成率を計算（完了タスク数/総タスク数）
			for(AllDTO dto: caseSumList) {
				System.out.println("総タスク数" + dto.getCaseSum());
				System.out.println("完了タスク数" + dto.getCaseNow());
				
				int totalTask=dto.getCaseSum();
				int completedTask=dto.getCaseNow();
				int progressRate=0;
				
				if(totalTask!=0) {
					progressRate=completedTask /totalTask * 100;
				}
				dto.setCaseProgressRate(progressRate);
			}
			
			return caseSumList ;
		}
		
	/**
	 * メンバー別集計メソッド
	 *
	 * @param month
	 * @return ArrayList<AllDTO>
	 */	
	//メンバー別集計---------------------------------------
		public ArrayList<AllDTO> selectMemberSum(String month){
			ArrayList<AllDTO> memberSumList= null;
			WorksDAO dao = new WorksDAO(super.conn);
			
			//メンバー別集計
			try {
				memberSumList=dao.selectMemberSum(month);
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			//月全体の実績工数を取得
			AllDTO sumDTO = null;
			try {
				sumDTO = dao.selectSum(month);
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			BigDecimal monthlyTotalHours=sumDTO.getMonthlyTotalHours();
			
			
			//全体に占める割合の計算
			for(AllDTO dto: memberSumList) {
				BigDecimal memberHours=dto.getActualHours();
				int workRate=0;
				if(monthlyTotalHours!=null && monthlyTotalHours.compareTo(BigDecimal.ZERO)!=0) {
					workRate=memberHours.multiply(new BigDecimal("100")).divide(monthlyTotalHours, 0,RoundingMode.DOWN).intValue();
				}
				dto.setWorkRate(workRate);
			}
			
			return memberSumList;
		}
		
	/**
	 * 工数ログ一覧メソッド
	 *
	 * @param month
	 * @return ArrayList<AllDTO>
	 */	
		
	//指定した月の工数ログ（月次集計画面）---------------------------------------
		public ArrayList<AllDTO> selectByMonth(String month){
			ArrayList<AllDTO> workList = null;
			WorksDAO dao = new WorksDAO(super.conn);
			
			//工数ログ一覧
			try {
				workList=dao.selectByMonth(month);
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			
			return workList ;		
		}
	
	
	//指定した月の月次集計（月次集計画面）(aggregateメソッドで3つのサマリーを取得)---------------------------------------
//		public ArrayList<AllDTO> aggregate() {
//			WorksDAO dao = new WorksDAO(super.conn);
//			try {
//				aggregateList = dao.aggregate();
//			} catch (SQLException e) {
//				System.out.println("SQL文おかしいよ");
//				e.printStackTrace();
//			}
//			super.close();
//			
//			return aggregatesList ;		
//		}
//		}	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
