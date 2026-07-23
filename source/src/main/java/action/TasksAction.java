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
		
		//TaskServiceを呼びだす
		TasksService service = new TasksService();
	
		
		//タスク一覧を表示
		ArrayList<AllDTO> taskList = service.selectAll();
		

		//reqestスコープに格納する
		request.setAttribute("taskList", taskList);
		
		//ページを返す
		return page;
	}

	public String functions() throws UnsupportedEncodingException {
        String page = "/WEB-INF/jsp/tasks_regist.jsp";

        String mode = "regist";
        //タスクIDを取得（nullなら新規登録、任意の値ならそのタスクの編集）
        String taskIdStr = request.getParameter("taskId");
        //service呼び出し
        TasksService service = new TasksService();

        //案件名とPM名のリストを格納
        ArrayList<AllDTO> registList = service.regist();
        request.setAttribute("registList", registList);
        
        //編集ボタンからの遷移なら、一致するタスクIDの詳細を格納
        if(taskIdStr != null && !taskIdStr.isEmpty()) {
			int taskId = Integer.parseInt(taskIdStr);
			//編集モードを決定
			mode = "edit";
            ArrayList<AllDTO> taskList = service.edit(TasksDTO tDTO);
            request.setAttribute("taskList", taskList);
			request.setAttribute("mode", mode);
        }

        return page;
    }
}