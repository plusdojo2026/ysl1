package action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import dto.AllDTO;
import dto.TasksDTO;
import service.WorksService;

public class WorksAction {
	
	private AllDTO allDTO;
	private ArrayList<AllDTO> caseSummaryList;
	private ArrayList<AllDTO> memberSummaryList;
	
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
			String page = "/WEB-INF/jsp/monthly_sum.jsp";
			
			//値の取得
			request.setCharacterEncoding("UTF-8");		
			
			 WorksService service = new WorksService();
			 
			 //サマリー情報を取得して、リストに入れる
			allDTO = service.initialize();
			
			//案件別集計を取得して、リストに入れる
			caseSummaryList = service.selectCaseSummary();
			
			//メンバー別集計を取得して、リストに入れる
			memberSummaryList = service.selectMemberSummary();
			
			//JSPへ渡すためにAttributeに入れる
			 request.setAttribute("monthlyTotalHours", allDTO.getMonthlyTotalHours()); //月合計工数(h)
			 request.setAttribute("caseCount", allDTO.getCaseCount());                 //集計案件数
			 request.setAttribute("memberCount", allDTO.getMemberCount());             //稼働メンバー数
			 request.setAttribute("caseSummaryList", caseSummaryList);                 //案件別工数
			 request.setAttribute("memberSummaryList", memberSummaryList);             //メンバー別工数
			 request.setAttribute("displayMode", "summary");                           //最初に月次集計を表示
			return page;
		}	
		
		
	//指定した月の工数ログ（月次集計画面 ）---------------------------------------		
		public String selectByMonth() throws UnsupportedEncodingException {	
			String page = "/WEB-INF/jsp/monthly_sum.jsp";
			
			//値の取得
			request.setCharacterEncoding("UTF-8");
			String month = request.getParameter("month");
			
			WorksService service = new WorksService();
			
			//リストを作成して、月の工数ログをリストに格納する
			ArrayList<AllDTO> workList = service.selectByMounth();
			
			//Attributeに保存
			request.setAttribute("workList", workList);           //工数ログ一覧のリスト
			request.setAttribute("selectedMonth", month);         //選択された月
			request.setAttribute("displayMode", "summary");       //集計ボタンを押すと月次集計が表示される
			
			return page;
	
		}
	//指定した月の月次集計（月次集計画面）(aggregateメソッドで3つのサマリーを取得)---------------------------------------	
		public String aggregate() throws UnsupportedEncodingException {
			String page = "/WEB-INF/jsp/monthly_sum.jsp";
			
			//値の取得
			request.setCharacterEncoding("UTF-8");
			String month = request.getParameter("month");
			
			WorksService service = new WorksService();
			
			//サマリー取得
			AllDTO summary = service.aggregate(month);
			
			//案件別
			ArrayList<AllDTO> caseSummaryList = service.selectCaseSummary(month);
			
			//メンバー別
			ArrayList<AllDTO> memberSummaryList = service.selectMemberSummary(month);
			
			//Attributeに保存
			request.setAttribute("summary", summary);                         //サマリー（月合計工数、集計案件数、稼働メンバー数）
			request.setAttribute("caseSummaryList", caseSummaryList);         //案件別集計
			request.setAttribute("memberSummaryList", memberSummaryList);     //メンバー別集計
			request.setAttribute("selectedMonth",month);                      //選択された月
			request.setAttribute("displayMode", "workList");                   //工数一覧ボタンを押すと工数ログが表示される
			
			return page;
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
