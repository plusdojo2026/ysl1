package service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.WorksDAO;
import dto.AllDTO;

public class WorksService extends DBAccess{
	
	public WorksService() {
		super.access();
	}
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
			
			
			return caseSumList ;
		}
		
		
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
			
			return memberSumList;
		}
		
		
		
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
