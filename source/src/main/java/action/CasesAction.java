package action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import dto.AllDTO;
import dto.CasesDTO;
import dto.UsersDTO;
import service.CasesService;

public class CasesAction {

	HttpServletRequest request;

	//コンストラクタ
	public CasesAction(HttpServletRequest request) {
		this.request = request;
	}

	//案件詳細を持ってくるメソッド
	public String intiCasesDetail() throws UnsupportedEncodingException {
		String page = "/WEB-INF/jsp/cases_details.jsp";

		String ans = null;
		CasesDTO dto = null;
		request.setCharacterEncoding("UTF-8");
		//案件のIDを取得
		int id = Integer.parseInt(request.getParameter("case_id"));

		CasesService service = new CasesService();
		ArrayList<AllDTO> casesList = service.intiCasesDetail(id);
		request.setAttribute("casesList", casesList);

		return page;

	}

	//タスクを持ってくるメソッド
	public String intiCasesDetail2() throws UnsupportedEncodingException {
		String page = "/WEB-INF/jsp/cases_details.jsp";

		String ans = null;
		CasesDTO dto = null;
		request.setCharacterEncoding("UTF-8");
		//案件のIDを取得
		int id = Integer.parseInt(request.getParameter("case_id"));

		CasesService service = new CasesService();
		ArrayList<AllDTO> tasksList = service.intiCasesDetail2(id);
		request.setAttribute("tasksList", tasksList);

		return page;

	}

	//工数を持ってくるメソッド
	public String intiCasesDetail3() throws UnsupportedEncodingException {
		String page = "/WEB-INF/jsp/cases_details.jsp";

		String ans = null;
		CasesDTO dto = null;
		request.setCharacterEncoding("UTF-8");
		//案件のIDを取得
		int id = Integer.parseInt(request.getParameter("case_id"));

		CasesService service = new CasesService();
		ArrayList<AllDTO> worksList = service.intiCasesDetail3(id);
		request.setAttribute("worksList", worksList);

		return page;

	}

	//案件一覧の初期画面の表示のメソッド
	/**
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String initialize() throws UnsupportedEncodingException {
		String page = "/WEB-INF/jsp/cases.jsp";

		//Serviceを呼びだす
		CasesService service = new CasesService();
		//初期の案件一覧を表示
		ArrayList<AllDTO> casesList = service.initialize();
		//reqestスコープに格納する
		request.setAttribute("casesList", casesList);

		return page;
	}
	
	//案件一覧から編集ボタンで個別の案件を表示するcasesRegistメソッド
	public String casesEdit() throws UnsupportedEncodingException {
		String page = "/WEB-INF/jsp/cases_regist.jsp";
		
		//値の取得
		request.setCharacterEncoding("UTF-8");		
		int id = Integer.parseInt(request.getParameter("id"));
		
		CasesService service = new CasesService();
		
		CasesDTO dto=service.casesEdit(id);
		
		ArrayList<UsersDTO>
		userList=service.selectPmList();
		request.setAttribute("cases", dto);
		request.setAttribute("userList", userList);
		
		return page;
	}

	//	//案件一覧の検索メソッド　ジェークエリーを使うから使わなかった
	//		public String select() throws UnsupportedEncodingException{
	//			String page="/WEB-INF/jsp/cases.jsp";
	//			
	//			//値の取得
	//			request.setCharacterEncoding("UTF-8");		
	//			String id = request.getParameter("id");
	//			String caseName = request.getParameter("case_name");
	//			String caseCode = request.getParameter("case_code");
	//			String customerName = request.getParameter("customer_name");
	//			String caseStatus = request.getParameter("case_status");
	//			String casePriority = request.getParameter("case_priority");
	//			
	//			CasesService service = new CasesService();
	//			//serviceに処理を依頼　真似しただけなので書き直す
	//			boolean ans = service.select();
	//			
	//			ArrayList<AllDTO> casesList = service.select();
	//			request.setAttribute("casesList", casesList);
	//		}

//	//新規案件登録のdoGetメソッド（有効か無効かはまだ見分けられない）
//	public String casesRegist() throws UnsupportedEncodingException {
//		String page = "/WEB-INF/jsp/cases_regist.jsp";
//		request.setCharacterEncoding("UTF-8");
//		String id = request.getParameter("id");
//		String userName = request.getParameter("pm_id");
//
//		CasesService service = new CasesService();
//		//serviceに処理を依頼　真似しただけなので書き直す
//		boolean ans = service.select(id);
//
//		ArrayList<CasesDTO> casesList = service.selectAll();
//		request.setAttribute("casesList", casesList);
//	}

	//新規案件登録のinsertメソッド
	/**
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String insert() throws UnsupportedEncodingException {
		String page = "/WEB-INF/jsp/cases_regist.jsp";

		//値の取得
		request.setCharacterEncoding("UTF-8");
		//String id = request.getParameter("id");登録すると自動でつくため。
		String caseName = request.getParameter("case_name");
		int caseCode = Integer.parseInt(request.getParameter("case_code"));
		String customerName = request.getParameter("customer_name");
		int pmId=Integer.parseInt(request.getParameter("pm_id"));//担当PM
		String caseStatus = request.getParameter("case_status");
		String casePriority = request.getParameter("case_priority");
		String startDate = request.getParameter("start_date");
		String plannedEndDate = request.getParameter("planned_end_date");
		int casePlannedHours = Integer.parseInt(request.getParameter("case_planned_hours"));
		String caseDescription = request.getParameter("case_description");
		
		CasesDTO dto=new CasesDTO(0,caseName,caseCode,customerName,casePriority,pmId,caseStatus,
				startDate,plannedEndDate,caseDescription,casePlannedHours);
		
		CasesService service =new CasesService();
		//serviceに処理を依頼する
		int ans=service.insert(dto);
		//ちゃんと登録できたか確認
				if(ans == 1) {
					request.setAttribute("msg", "※の登録完了！");
				}else {
					request.setAttribute("msg", "※登録失敗！");
				}
				//ユーザー情報を全て取得する,
				//案件登録をした後の画面で案件一覧を出すために全部取ってくる。selectAll。しかし、casesiniti()メソッドとの違いがわからない…
				ArrayList<AllDTO> casesList = service.selectAll();
				request.setAttribute("casesList", casesList);
				
				return page;
			}
	

//	//案件編集のdoGetメソッド
//	public String casesEdit() throws UnsupportedEncodingException {
//		String page = "/WEB-INF/jsp/cases_regist.jsp";
//
//		//値の取得	すでに入っているデータをとってくる
//
//	}

	//案件編集のupdateメソッド
	/**
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String update() throws UnsupportedEncodingException {
		String page = "/WEB-INF/jsp/cases_regist.jsp";

		//値の取得
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));//編集だとidが必要かな。
		String caseName = request.getParameter("case_name");
		int caseCode = Integer.parseInt(request.getParameter("case_code"));
		String customerName = request.getParameter("customer_name");
		int pmId=Integer.parseInt(request.getParameter("pm_id"));//担当PM
		String caseStatus = request.getParameter("case_status");
		String casePriority = request.getParameter("case_priority");
		String startDate = request.getParameter("start_date");
		String plannedEndDate = request.getParameter("planned_end_date");
		int casePlannedHours = Integer.parseInt(request.getParameter("case_planned_hours"));
		String caseDescription = request.getParameter("case_description");
		
		CasesDTO dto=new CasesDTO(id,caseName,caseCode,customerName,casePriority,pmId,caseStatus,
				startDate,plannedEndDate,caseDescription,casePlannedHours);
		
		CasesService service =new CasesService();
		//serviceに処理を依頼する
		int ans=service.update(dto);
		//ちゃんと登録できたか確認
				if(ans == 1) {
					request.setAttribute("msg", "※の登録完了！");
				}else {
					request.setAttribute("msg", "※登録失敗！");
				}
				//ユーザー情報を全て取得する
				//案件登録をした後の画面で案件一覧を出すために全部取ってくる。selectAll。しかし、casesiniti()メソッドとの違いがわからない…
				ArrayList<AllDTO> casesList = service.selectAll();
				request.setAttribute("casesList", casesList);
				
				return page;
			}
}