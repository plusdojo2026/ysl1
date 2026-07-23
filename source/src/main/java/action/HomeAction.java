package action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dto.AllDTO;
import dto.UsersDTO;
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
		
		//セッションからログインユーザーの情報を取得する
		UsersDTO user = new UsersDTO();
		HttpSession usersession = request.getSession();
		user =  (UsersDTO) usersession.getAttribute ("user");
		
		//UserIdを取得する
		int id = user.getUserId();
		
		//カウントした結果を返すメソッド呼び出し
		AllDTO count = hservice.select(id);
		
		
		//タスク一覧を表示するメソッド呼び出し
		ArrayList<AllDTO> taskList = tservice.selectAll();
		
		//案件一覧を表示
		
		
		//reqestスコープに格納する
		request.setAttribute("taskList", taskList);		//名前 taskList 値 taskList
		request.setAttribute("count", count);
		
		//ページを返す
		return page;
	}
}
	