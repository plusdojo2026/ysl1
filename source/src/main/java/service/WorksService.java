package service;

import java.sql.SQLException;
import java.util.ArrayList;


import dao.WorksDAO;
import dto.AllDTO;
s
public class WorksService extends DBAccess{
	
	public WorksService() {
		super.access();
	}
	//工数を登録するメソッド---------------------------------------
		public int worksInsert(String workDate ,String actualHours ,String workDescription) {
			//DAOに処理を任せる
			WorksDAO dao = new WorksDAO(conn);
			int ans=0;
			try {
				ans = dao.worksInsert(workDate,actualHours,workDescription);
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			return ans;		
		}
		
	//工数を削除するメソッド---------------------------------------
		public int worksDelete(String id) {
			//DAOに処理を任せる
			WorksDAO dao = new WorksDAO(super.conn);
			int ans=0;
			try {
				ans = dao.worksDelete(Integer.parseInt(id));
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			return ans;		
			
		}
		
	//サマリー（月合計工数、集計案件数、稼働メンバー数）---------------------------------------	
		public AllDTO selectSummary() {
			AllDTO summaryList =null;
			WorksDAO dao = new WorksDAO(super.conn);
			
			//月合計工数、集計案件数、稼働メンバー数
			AllDTO summary=dao.selectSummary();
			
			return summaryList;
		}
			

	//案件別集計---------------------------------------
		public ArrayList<AllDTO> selectCaseSummary() {
			ArrayList<AllDTO> selectCaseSumaary =null;
			WorksDAO dao = new WorksDAO(super.conn);
			
			
			//案件別集計
			ArrayList<AllDTO> caseList=dao.selectCaseSummary();
			
			
			return selectCaseSumaary ;
		}
		
		
	//メンバー別集計---------------------------------------
		public ArrayList<AllDTO> selectMemberSummary() {
			ArrayList<AllDTO> selectMemberSummary= null;
			WorksDAO dao = new WorksDAO(super.conn);
			
			//メンバー別集計
			ArrayList<AllDTO> memberList=dao.selectMemberSummary();
			
			return selectMemberSummary;
		}
		
		
		
	//指定した月の工数ログ（月次集計画面 ）---------------------------------------
		public ArrayList<AllDTO> selectByMonth() {
			WorksDAO dao = new WorksDAO(super.conn);
			try {
				workList = dao.selecyByMonth();
			} catch (SQLException e) {
				System.out.println("SQL文おかしいよ");
				e.printStackTrace();
			}
			super.close();
			
			return workList ;		
		}
	
	
	//指定した月の月次集計（月次集計画面）(aggregateメソッドで3つのサマリーを取得)---------------------------------------
		public ArrayList<AllDTO> aggregate() {
			WorksDAO dao = new WorksDAO(super.conn);
			try {
				aggregateList = dao.aggregate();
			} catch (SQLException e) {
				System.out.println("SQL文おかしいよ");
				e.printStackTrace();
			}
			super.close();
			
			return aggregatesList ;		
		}
		}	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
