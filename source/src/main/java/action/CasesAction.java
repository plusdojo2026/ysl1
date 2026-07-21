package action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

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

}