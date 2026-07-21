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
		public String initialize() throws UnsupportedEncodingException{
			String page = "/WEB-INF/jsp/cases.jsp";
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
			//serviceに処理を依頼
			boolean ans = service.select(id);
			
			ArrayList<AllDTO> userList = service.selectAll();
			request.setAttribute("", userList);
		}
		
		
		//新規案件登録のdoGetメソッド
		public String casesRegist() throws UnsupportedEncodingException{
			String page="/WEB-INF/jsp/cases_regist.jsp";
			request.setCharacterEncoding("UTF-8");		
			String id = request.getParameter("id");
			String userName=request.getParameter("pm_id");
		}
		//新規案件登録のdoPost,insertメソッド
		
		//案件編集のdoGetメソッド
		public String casesEdit() throws UnsupportedEncodingException{
			String page="/WEB-INF/jsp/cases_regist.jsp";
		}
		//案件編集のdoPostメソッド
		
}