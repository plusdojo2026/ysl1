package service;


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
	 */
	
	public AllDTO select(AllDTO adto) {		//AllDTOのなかで、adtoという名前の条件で探す
		AllDTO count = null;
		
		HomeDAO dao = new HomeDAO(super.conn);
		count = dao.select(adto);		
		
		return count;
	}
}