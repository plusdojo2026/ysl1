package service;

import java.sql.SQLException;

import dao.HomeDAO;
import dto.AllDTO;


	

public class HomeService extends DBAccess{
	
	//空のコンストラクタを生成
	public HomeService() {
		super.access();
	}
	
	/**
	 * 各項目の件数を取得するメソッド
	 * 
	 * @param AllDTO adto (条件が入っているDTO)
	 * @return AllDTO
	 * @throws SQLException 
	 */
	
	public AllDTO select(int id) throws SQLException {		//idという名前の条件で探す
		AllDTO count = null;
		
		//AllDTOでcountを作成し、それを返す
		HomeDAO dao = new HomeDAO(super.conn);
		count = dao.select(id);		
		
		return count;
	}
}