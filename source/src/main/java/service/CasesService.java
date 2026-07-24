package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.CasesDAO;
import dao.utils.DBUtils;
import dto.AllDTO;
import dto.CasesDTO;
import dto.UsersDTO;

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
	
	/**
	 * 一覧表示メソッド
	 * @return ArrayList<AllDTO>
	 */
	//案件一覧の初期画面の表示のメソッド
	public ArrayList<AllDTO> initialize() {
		//一旦空にする
		ArrayList<AllDTO> casesList = null;

		CasesDAO dao = new CasesDAO(super.conn);

		try {
			casesList = dao.initialize();
		} catch (SQLException e) {
			System.out.println("SQL文おかしいよ");
			e.printStackTrace();//エラーの説明をしてくれる
		} finally {
			super.close();
		}
		return casesList;

	}
	/**
	 * - 選択したcasesIDのレコードの詳細を取得する
	 * 
	 * @return dto;
	 * 
	 * 
	 * @author 横山
	 */
	//案件一覧から編集ボタンで個別の案件を表示するcasesEditメソッド
	public CasesDTO casesEdit(int id) {
		//DAOに処理を任せる
		CasesDAO dao = new CasesDAO(conn);
		CasesDTO dto = null;
		try {
			dto = dao.casesEdit(id);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return dto;		
	}
	/**
	 * - 登録されているすべてのPMの名前を取得する
	 * 
	 * @return selectPmList;
	 * 
	 * 
	 * @author 横山
	 */
	//PM一覧を取得するメソッド
	public ArrayList<UsersDTO>
	selectPmList(){
		CasesDAO dao=new CasesDAO(conn);
		
		ArrayList<UsersDTO>
		usersList=null;
		try {
			usersList = dao.selectPmList();
		} catch (SQLException e) {
			System.out.println("SQL文おかしいよ");
			e.printStackTrace();//エラーの説明をしてくれる
		} finally {
			super.close();
		}
		return usersList;
	}

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

	/**
     * - 案件を編集する -
     *ごめん、booleanに直します
     * @return 1:成功 0:失敗
     *
     *
     * @author haruto.tanaka
     */
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
	/**
	 * - 登録されている全案件の名前を取得する -
	 * 
	 * @return casesList;
	 * @author 横山
	 */
	//全ての案件を取得するメソッド
		public ArrayList<AllDTO> selectAll() {
			ArrayList<AllDTO> casesList = null;
			
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
//		//案件一覧の検索メソッド	ジェークエリーを使うから使わなかった	
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
//		public ArrayList<CasesDTO> intiCases(cDto CasesDTO) {
//			//一旦空にする
//			ArrayList<CasesDTO> casesList = null;
//			CasesDAO dao = new CasesDAO(super.conn);
	//
//			try {
//				casesList = dao.insertAll();
//			} catch (SQLException e) {
//				System.out.println("SQL文おかしいよ");
//				e.printStackTrace();
//			}
//			super.close();
	//
//			return casesList;
//		}
}
