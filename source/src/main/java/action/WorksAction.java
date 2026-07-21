package action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import dto.TasksDTO;
import service.WorksService;

public class WorksAction {
	
	HttpServletRequest request ;
	//コンストラクタ
	public WorksAction(HttpServletRequest request) {
		this.request=request;
	}
	
	
	//工数登録メソッド（ダッシュボード、案件詳細画面、タスク詳細画面）---------------------------------------
		public String insert() throws UnsupportedEncodingException {
			String page="/WEB-INF/jsp/task_details.jsp";
			
			//値の取得
			request.setCharacterEncoding("UTF-8");		
			String workDate = request.getParameter("workDate");
			String actualHours = request.getParameter("actualHours");
			String workDescription = request.getParameter("workDescription");
			
			
			WorksService service = new WorksService();
			//serviceに処理を依頼
			int ans = service.workInsert(workDate,actualHours,workDescription);
			//ちゃんと登録できたか確認
			if(ans == 1) {
				request.setAttribute("msg", "※工数登録完了！");
			}else {
				request.setAttribute("msg", "※登録失敗！");
			}
			//タスク詳細情報を全て取得する
			ArrayList<TasksDTO> taskList = service.selectAll();
			request.setAttribute("taskList", taskList);
			
			return page;
		}
	
	//工数削除メソッド（タスク詳細画面）---------------------------------------
		public String delete() throws UnsupportedEncodingException {
			String page="/WEB-INF/jsp/task_details.jsp";
					
			//値の取得
			request.setCharacterEncoding("UTF-8");		
			String id = request.getParameter("id");
					
					
			WorksService service = new WorksService();
			//serviceに処理を依頼
			int ans = service.workDelete(id);
					
			//タスク詳細情報を全て取得する
			ArrayList<TasksDTO> taskList = service.selectAll();
			request.setAttribute("taskList", taskList);
					
			return page;
		}
				
	//月次集計画面の初期表示 ---------------------------------------
	
		public String initialize() throws UnsupportedEncodingException {
			//返却する次の飛び先のURLをとりあえず定義
			//String ans = null;
			
			//値の取得
			request.setCharacterEncoding("UTF-8");		
			String month = request.getParameter("month");
			
			allDTO= worksService.initialize();
			
			return SUCCESS;
		}	
		
		
		
	//指定した月の工数ログ（月次集計画面 ）---------------------------------------		
		public String selectByMonth() throws UnsupportedEncodingException {	
		
	
	
		}
	//指定した月の月次集計（月次集計画面）---------------------------------------	
		public String aggregate() throws UnsupportedEncodingException {
	
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
