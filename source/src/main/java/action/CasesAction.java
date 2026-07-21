package action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import dto.AllDTO;
import dto.CasesDTO;
import service.CasesService;

public class CasesAction {

	HttpServletRequest request;

	//コンストラクタ
	public CasesAction(HttpServletRequest request) {
		this.request = request;
	}

	//ページを開いた時に表示するもののメソッド
	public String intiCasesDetail() throws UnsupportedEncodingException {
		String page = "/WEB-INF/jsp/cases_details.jsp";

		String ans = null;
		CasesDTO dto = null;

		request.setCharacterEncoding("UTF-8");

		CasesService service = new CasesService();
		ArrayList<CasesDTO> casesList = service.intiCasesDetail();
		request.setAttribute("", casesList);

	}
	
	//案件一覧の初期画面の表示のメソッド
		public String initicases() throws UnsupportedEncodingException{
			String page = "/WEB-INF/jsp/cases.jsp";
			
			CasesService service = new CasesService();
			ArrayList<AllDTO> casesList = service.intiCases();
			
			request.setAttribute("casesList", casesList);
			}
	
	//案件一覧の検索メソッド
		public String select() throws UnsupportedEncodingException{
			String page="/WEB-INF/jsp/cases.jsp";
			
			//値の取得
			request.setCharacterEncoding("UTF-8");		
			String id = request.getParameter("id");
			String caseName = request.getParameter("case_name");
			String caseCode = request.getParameter("case_code");
			String customerName = request.getParameter("customer_name");
			String caseStatus = request.getParameter("case_status");
			String casePriority = request.getParameter("case_priority");
			
			CasesService service = new CasesService();
			//serviceに処理を依頼　真似しただけなので書き直す
			boolean ans = service.select(id);
			
			ArrayList<AllDTO> casesCard = service.selectAll();
			request.setAttribute("casesCard", casesCard);
		}
		
		
		//新規案件登録のdoGetメソッド（有効か無効かはまだ見分けられない）
		public String casesRegist() throws UnsupportedEncodingException{
			String page="/WEB-INF/jsp/cases_regist.jsp";
			request.setCharacterEncoding("UTF-8");		
			String id = request.getParameter("id");
			String userName=request.getParameter("pm_id");
			
			CasesService service = new CasesService();
			//serviceに処理を依頼　真似しただけなので書き直す
			boolean ans = service.select(id);
			
			ArrayList<CasesDTO> casesList = service.selectAll();
			request.setAttribute("casesList", casesList);
		}
		//新規案件登録のdoPost,insertメソッド
		public String insert() throws UnsupportedEncodingException{
			String page="/WEB-INF/jsp/cases_regist.jsp";
			
			//値の取得
			request.setCharacterEncoding("UTF-8");		
			String id = request.getParameter("id");
			String caseName = request.getParameter("case_name");
			String caseCode = request.getParameter("case_code");
			String customerName = request.getParameter("customer_name");
			String caseStatus = request.getParameter("case_status");
			String casePriority = request.getParameter("case_priority");
			String startDate = request.getParameter("start_date");
			String plannedEndDate = request.getParameter("planned_end_date");
			String casePlannedHours = request.getParameter("case_planned_hours");
			String caseDescription = request.getParameter("case_description");
			
		}
		
		//案件編集のdoGetメソッド
		public String casesEdit() throws UnsupportedEncodingException{
			String page="/WEB-INF/jsp/cases_regist.jsp";
			
			//値の取得	すでに入っているデータをとってくる
			
		}
		//案件編集のdoPostメソッド
		public String update() throws UnsupportedEncodingException{
			String page="/WEB-INF/jsp/cases_regist.jsp";
			
			//値の取得
			request.setCharacterEncoding("UTF-8");		
			String id = request.getParameter("id");
			String caseName = request.getParameter("case_name");
			String caseCode = request.getParameter("case_code");
			String customerName = request.getParameter("customer_name");
			String caseStatus = request.getParameter("case_status");
			String casePriority = request.getParameter("case_priority");
			String startDate = request.getParameter("start_date");
			String plannedEndDate = request.getParameter("planned_end_date");
			String casePlannedHours = request.getParameter("case_planned_hours");
			String caseDescription = request.getParameter("case_description");
		}
}