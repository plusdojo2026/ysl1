package action;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dto.AllDTO;
import dto.UsersDTO;
import service.TasksService;
import service.WorksService;

/**
 * 
 * WorksActionクラス。
 * <p>
 * TODO 画面上のデータをJSPから取得し、表示する。Serviceメソッドを呼び出す。
 * </p>
 *
 * @author YSL土屋莉里子
 */

public class WorksAction {
	
	private AllDTO selectSum;
	private ArrayList<AllDTO> caseSumList;
	private ArrayList<AllDTO> memberSumList;
	
	HttpServletRequest request ;
	//コンストラクタ
	public WorksAction(HttpServletRequest request) {
		this.request=request;
	}
	
	
	/**
	 * 工数登録メソッド
	 * 
	 * @return タスク詳細画面のurl
	 * @throws UnsupportedEncodingException
	 */
	//工数登録メソッド（ダッシュボード、案件詳細画面、タスク詳細画面）---------------------------------------
		public String worksInsert() throws UnsupportedEncodingException {
			String page="/WEB-INF/jsp/task_details.jsp";
			
			//値の取得
			request.setCharacterEncoding("UTF-8");	
			HttpSession session = request.getSession();
			UsersDTO user = (UsersDTO) session.getAttribute("user");
			int userId = user.getUserId();
			int taskId= Integer.parseInt(request.getParameter("taskId"));		
			String workDate = request.getParameter("workDate");
			BigDecimal actualHours = new BigDecimal(request.getParameter("actualHours"));
			String workDescription = request.getParameter("workDescription");
			
			
			WorksService service = new WorksService();
			//serviceに処理を依頼
			int ans = service.worksInsert(userId,taskId,workDate,actualHours,workDescription);
			//ちゃんと登録できたか確認
			if(ans == 1) {
				request.setAttribute("msg", "※工数登録完了！");
			}else {
				request.setAttribute("msg", "※登録失敗！");
			}
			
			//タスク詳細表示メソッドを呼び出す
			TasksService tasksService = new TasksService();
			tasksService.edit(taskId);
			
			return page;
		}
		
		
	/**
	 * 工数削除メソッド
	 * 
	 * @return タスク詳細画面のurl
	 * @throws UnsupportedEncodingException
	 */
	//工数削除メソッド（タスク詳細画面）---------------------------------------
		public String delete() throws UnsupportedEncodingException {
			String page="/WEB-INF/jsp/task_details.jsp";
					
			//値の取得
			request.setCharacterEncoding("UTF-8");		
			int id = Integer.parseInt(request.getParameter("id"));
						
			WorksService service = new WorksService();
			
			//serviceに処理を依頼
			int ans=service.worksDelete(id);
			
			//ちゃんと削除できたか確認
			if(ans == 1) {
				request.setAttribute("msg", "※削除完了！");
			}else {
				request.setAttribute("msg", "※削除失敗！");
			}
					
			//タスク詳細表示メソッドを呼び出す
			TasksService tasksService = new TasksService();
			String taskIdStr = request.getParameter("taskId");
			int taskId = Integer.parseInt(taskIdStr);
			tasksService.edit(taskId);
					
			return page;
		}
		
		
	/**
	 * 月次集計画面の初期表示メソッド
	 * 			
	 * @return 月次集計画面のurl
	 * @throws UnsupportedEncodingException
	 */
	//月次集計画面の初期表示 ---------------------------------------
	
		public String initialize() throws UnsupportedEncodingException {
			//返却する次の飛び先のURLをとりあえず定義
			String page = "/WEB-INF/jsp/monthly_sum.jsp";
			
			//値の取得
			request.setCharacterEncoding("UTF-8");		
			String month=request.getParameter("month");
			
			//現在の年月を取得
			if(month==null || month.isEmpty()) {
				month= YearMonth.now().toString();
			}
			
			System.out.println("month = " + month);
			
			 WorksService service = new WorksService();
			 
			 //サマリー情報を取得して、リストに入れる
			selectSum = service.selectSum(month);
			
			//案件別集計を取得して、リストに入れる
			caseSumList = service.selectCaseSum(month);
			
			//メンバー別集計を取得して、リストに入れる
			memberSumList = service.selectMemberSum(month);
			
			//JSPへ渡すためにAttributeに入れる
			 request.setAttribute("monthlyTotalHours", selectSum.getMonthlyTotalHours()); //月合計工数(h)
			 request.setAttribute("caseCount", selectSum.getCaseCount());                 //集計案件数
			 request.setAttribute("memberCount", selectSum.getMemberCount());             //稼働メンバー数
			 request.setAttribute("caseSumList", caseSumList);                            //案件別工数
			 request.setAttribute("memberSumList", memberSumList);      				  //メンバー別工数
			 request.setAttribute("displayMode", "summary");                              //最初に月次集計を表示
			 request.setAttribute("selectMonth", month);                                  //作ったMonthを表示
			return page;
		}	
		
		
	/**
	 * 指定した月の工数ログメソッド
	 * 	
	 * @return 月次集計画面のurl
	 * @throws UnsupportedEncodingException
	 */
	//指定した月の工数ログ（月次集計画面 ）---------------------------------------		
		public String selectByMonth() throws UnsupportedEncodingException {	
			String page = "/WEB-INF/jsp/monthly_sum.jsp";
			
			//値の取得
			request.setCharacterEncoding("UTF-8");
			String month = request.getParameter("month");
			
			WorksService service = new WorksService();
			
			//リストを作成して、月の工数ログをリストに格納する
			ArrayList<AllDTO> workList = service.selectByMonth(month);
			
			//Attributeに保存
			request.setAttribute("workList", workList);           //工数ログ一覧のリスト
			request.setAttribute("selectedMonth", month);         //選択された月
			request.setAttribute("displayMode", "workList");      //工数一覧ボタンを押すと工数ログが表示される
			return page;
	
		}
	//指定した月の月次集計（月次集計画面）(aggregateメソッドで3つのサマリーを取得)---------------------------------------	
//		public String aggregate() throws UnsupportedEncodingException {
//			String page = "/WEB-INF/jsp/monthly_sum.jsp";
//			
//			//値の取得
//			request.setCharacterEncoding("UTF-8");
//			String month = request.getParameter("month");
//			
//			WorksService service = new WorksService();
//			
//			//サマリー取得
//			AllDTO summary = service.aggregate(month);
//			
//			//案件別
//			ArrayList<AllDTO> caseSummaryList = service.selectCaseSum(month);
//			
//			//メンバー別
//			ArrayList<AllDTO> memberSummaryList = service.selectMemberSum(month);
//			
//			//Attributeに保存
//			request.setAttribute("summary", summary);                         //サマリー（月合計工数、集計案件数、稼働メンバー数）
//			request.setAttribute("caseSummaryList", caseSummaryList);         //案件別集計
//			request.setAttribute("memberSummaryList", memberSummaryList);     //メンバー別集計
//			request.setAttribute("selectedMonth",month);                      //選択された月
//			
//			
//			return page;
//		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
