package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.CasesDAO;
import dao.utils.DBUtils;
import dto.AllDTO;

public class CasesService {

	//ページを開いた時に表示するもののメソッド
	public ArrayList<AllDTO> intiCasesDetail() {
		//一旦空にする
		ArrayList<AllDTO> casesList = null;
		ArrayList<AllDTO> tasksList = null;
		ArrayList<E>
		// データベースに接続する
		// DBUtilsで接続
		Connection conn = DBUtils.getConnection();
		CasesDAO dao = new CasesDAO(conn);

		casesList = dao.select();
		tasksList
		list
		//まとめたリストの文

		return casesList;

	}
	
	//案件一覧の初期画面の表示のメソッド
			public ArrayList<AllDTO> intiCases(aDto AllDTO) {
				//一旦空にする
				ArrayList<AllDTO> casesCard = null;
				CasesDAO dao = new CasesDAO(super.conn);
				
				try {
					casesCard = dao.selectAll();
				} catch (SQLException e) {
					System.out.println("SQL文おかしいよ");
					e.printStackTrace();
				}
				super.close();
				
				return casesCard;

			}
	//案件一覧の検索メソッド		
			public ArrayList<AllDTO> intiCases(aDto AllDTO) {
				//一旦空にする
				ArrayList<AllDTO> casesCard = null;
				CasesDAO dao = new CasesDAO(super.conn);
				
				try {
					casesCard = dao.selectAll();
				} catch (SQLException e) {
					System.out.println("SQL文おかしいよ");
					e.printStackTrace();
				}
				super.close();
				
				return casesCard;
			}
	//新規案件登録のdoGetメソッド
			public ArrayList<CasesDTO> intiCases(cDto CasesDTO) {
				//一旦空にする
				ArrayList<CasesDTO> casesList = null;
				CasesDAO dao = new CasesDAO(super.conn);
				
				try {
					casesList = dao.insertAll();
				} catch (SQLException e) {
					System.out.println("SQL文おかしいよ");
					e.printStackTrace();
				}
				super.close();
				
				return casesList;
			}
	//新規案件登録のdoPost,insertメソッド
			public ArrayList<CasesDTO> intiCases(cDto CasesDTO) {
				//一旦空にする
				ArrayList<CasesDTO> casesList = null;
				CasesDAO dao = new CasesDAO(super.conn);
				
				try {
					casesList = dao.insertAll();
				} catch (SQLException e) {
					System.out.println("SQL文おかしいよ");
					e.printStackTrace();
				}
				super.close();
				
				return casesList;
			}	
	//案件編集のdoGetメソッド
			public ArrayList<CasesDTO> intiCases(cDto CasesDTO) {
				//一旦空にする
				ArrayList<CasesDTO> casesList = null;
				CasesDAO dao = new CasesDAO(super.conn);
				
				try {
					casesList = dao.insertAll();
				} catch (SQLException e) {
					System.out.println("SQL文おかしいよ");
					e.printStackTrace();
				}
				super.close();
				
				return casesList;
			}	
	//案件編集のdoPostメソッド
			public ArrayList<CasesDTO> intiCases(cDto CasesDTO) {
				//一旦空にする
				ArrayList<CasesDTO> casesList = null;
				CasesDAO dao = new CasesDAO(super.conn);
				
				try {
					casesList = dao.insertAll();
				} catch (SQLException e) {
					System.out.println("SQL文おかしいよ");
					e.printStackTrace();
				}
				super.close();
				
				return casesList;
			}	
}
