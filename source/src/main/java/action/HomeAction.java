package action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import dto.AllDTO;
import service.CasesService;
import service.HomeService;
import service.TasksService;




public class HomeAction {
	
	HttpServletRequest request ;
	//コンストラクタ
	public HomeAction(HttpServletRequest request) {
		this.request=request;
	}
		
	
	/**
	 * 各項目の件数を取得するメソッド
	 * 案件一覧を表示するメソッド
	 * タスク一覧表示メソッド
	 * @return String page
	 */
	
	public String Intilize() throws UnsupportedEncodingException {
		String page="/WEB-INF/jsp/Home.jsp";
		//Intilize にするのは、複数のServiceを呼び出しているから
		
		
		//Serviceを呼びだす
		HomeService hservice = new HomeService();
		TasksService tservice = new TasksService();
		CasesServise cservice = new CasesService();
		
		//各項目の件数のデータを取得
		
		
		//タスク一覧を表示
		ArrayList<AllDTO> taskList = tservice.selectAll();
		

		//reqestスコープに格納する
		request.setAttribute("taskList", taskList);
		
		//ページを返す
		return page;
	}
}
	