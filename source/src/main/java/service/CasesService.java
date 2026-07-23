package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.CasesDAO;
import dao.utils.DBUtils;
import dto.AllDTO;
import dto.CasesDTO;

/**
 * 
 */
public class CasesService extends DBAccess {

	/**
	 * ページを開いた時に表示するもののメソッド
	 * @param id
	 * @return ArrayList<AllDTO>
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ArrayList<AllDTO> intiCasesDetail(int id) {
		//一旦空にする
		ArrayList<AllDTO> casesList = null;

		// データベースに接続する

		// DBUtilsで接続
		Connection conn;
		try {
			conn = DBUtils.getConnection();
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		CasesDAO dao = new CasesDAO(super.conn);

		try {
			casesList = dao.selectCases(id);
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		} finally {

		}
		//まとめたリストの文を書く

		//返す
		return casesList;

	}

	public ArrayList<AllDTO> intiCasesDetail2(int id) {
		//一旦空にする
		ArrayList<AllDTO> tasksList = null;

		// データベースに接続する

		// DBUtilsで接続
		Connection conn;
		try {
			conn = DBUtils.getConnection();
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		CasesDAO dao = new CasesDAO(super.conn);

		try {
			tasksList = dao.selectTasks(id);
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		} finally {

		}
		//まとめたリストの文を書く

		//返す
		return tasksList;

	}

	public ArrayList<AllDTO> intiCasesDetail3(int id) {
		//一旦空にする
		ArrayList<AllDTO> worksList = null;

		// データベースに接続する

		// DBUtilsで接続
		Connection conn;
		try {
			conn = DBUtils.getConnection();
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		CasesDAO dao = new CasesDAO(super.conn);

		try {
			worksList = dao.selectWorks(id);
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		} finally {

		}
		//まとめたリストの文を書く

		//返す
		return worksList;

	}

	//案件一覧の初期画面の表示のメソッド
	public ArrayList<AllDTO> initCases() {
		//一旦空にする
		ArrayList<AllDTO> casesList = null;

		CasesDAO dao = new CasesDAO(super.conn);

		try {
			casesList = dao.initiCases();
		} catch (SQLException e) {
			System.out.println("SQL文おかしいよ");
			e.printStackTrace();//エラーの説明をしてくれる
		} finally {
			super.close();
		}
		return casesList;

	}

	//	//案件一覧の検索メソッド	ジェークエリーを使うから使わなかった	
	//			public ArrayList<AllDTO> select() {
	//				//一旦空にする
	//				ArrayList<AllDTO> casesCard = null;
	//				CasesDAO dao = new CasesDAO(super.conn);
	//				
	//				try {
	//					casesCard = dao.select();
	//				} catch (SQLException e) {
	//					System.out.println("SQL文おかしいよ");
	//					e.printStackTrace();
	//				}
	//				super.close();
	//				
	//				return casesList;
	//			}
	//新規案件登録のdoGetメソッド
//	public ArrayList<CasesDTO> intiCases(cDto CasesDTO) {
//		//一旦空にする
//		ArrayList<CasesDTO> casesList = null;
//		CasesDAO dao = new CasesDAO(super.conn);
//
//		try {
//			casesList = dao.insertAll();
//		} catch (SQLException e) {
//			System.out.println("SQL文おかしいよ");
//			e.printStackTrace();
//		}
//		super.close();
//
//		return casesList;
//	}

	//新規案件登録のinsertメソッド
	public int insert(CasesDTO dto) {
		//一旦空にする
		CasesDAO dao = new CasesDAO(super.conn);
		
		int ans=0;
		try {
			ans = dao.insert(dto);
		} catch (SQLException e) {
			System.out.println("SQL文おかしいよ");
			e.printStackTrace();
		}
		super.close();

		return ans;
	}


	//案件編集のupdateメソッド
	public int update(CasesDTO dto) {
		//一旦空にする
		CasesDAO dao = new CasesDAO(super.conn);
		
		int ans=0;
		try {
			ans = dao.update(dto);
		} catch (SQLException e) {
			System.out.println("SQL文おかしいよ");
			e.printStackTrace();
		}
		super.close();

		return ans;
	}
	
	//全ての案件を取得するメソッド
		public ArrayList<CasesDTO> selectAll() {
			ArrayList<CasesDTO> casesList = null;
			
			CasesDAO dao = new CasesDAO(super.conn);
			try {
				casesList = dao.selectAll();
			} catch (SQLException e) {
				System.out.println("SQL文おかしいよ");
				e.printStackTrace();
			}
			super.close();
			
			return casesList ;
			
		}
}
