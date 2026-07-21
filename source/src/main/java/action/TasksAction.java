package action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import dto.AllDTO;
import service.TasksService;




public class TasksAction {
	
	HttpServletRequest request ;
	//コンストラクタ
	public TasksAction(HttpServletRequest request) {
		this.request=request;
	}
	
	/**
	 * 一覧表示メソッド
	 * @return String page
	 */
	public String selectAll() throws UnsupportedEncodingException {
		String page="/WEB-INF/jsp/Tasks.jsp";
		
		//Serviceを呼びだす
		TasksService service = new TasksService();
		
		//タスク一覧を表示
		ArrayList<AllDTO> taskList = service.selectAll();
		
		//reqestスコープに格納する
		request.setAttribute("taskList", taskList);
		
		//ページを返す
		return page;
	}
}