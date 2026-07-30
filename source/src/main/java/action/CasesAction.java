package action;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import dto.AllDTO;
import dto.CasesDTO;
import dto.TasksDTO;
import dto.UsersDTO;
import service.CasesService;

public class CasesAction {

	HttpServletRequest request;

	//コンストラクタ
	public CasesAction(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * 案件詳細を持ってくるメソッド
	 * @throws UnsupportedEncodingException
	 */
	public String intiCasesDetail() throws UnsupportedEncodingException {
		String page = "/WEB-INF/jsp/cases_details.jsp";

		String ans = null;
		CasesDTO dto = null;
		request.setCharacterEncoding("UTF-8");
		//案件のIDを取得
		int id = Integer.parseInt(request.getParameter("caseId"));

		intiCasesDetail1(id);
		intiCasesDetail2(id);
		intiCasesDetail3(id);
		return page;
	}

	/**
	 * 指定された案件IDの案件詳細情報を取得し、
	 * リクエストスコープに casesList として設定する。
	 *
	 * <p>取得した案件情報は AllDTO に格納され、以下の項目を含む。</p>
	 * <ul>
	 *   <li>caseId（案件ID）</li>
	 *   <li>caseName（案件名）</li>
	 *   <li>caseCode（案件コード）</li>
	 *   <li>customerName（顧客名）</li>
	 *   <li>casePriority（案件優先度）</li>
	 *   <li>pmId（PMユーザーID）</li>
	 *   <li>userName（PM名）</li>
	 *   <li>caseStatus（案件ステータス）</li>
	 *   <li>startDate（開始日）</li>
	 *   <li>plannedEndDate（終了予定日）</li>
	 *   <li>caseDescription（案件概要）</li>
	 *   <li>casePlannedHours（予定工数）</li>
	 *   <li>actualHoursSum（実績工数合計）</li>
	 *   <li>caseSum（タスク数）</li>
	 *   <li>caseNow（完了タスク数）</li>
	 *   <li>caseProgressRate（進捗率）</li>
	 * </ul>
	 *
	 * @param id 案件ID
	 * @throws UnsupportedEncodingException 文字エンコーディング処理でエラーが発生した場合
	 */
	public void intiCasesDetail1(int id) throws UnsupportedEncodingException {

		CasesService service1 = new CasesService();
		ArrayList<AllDTO> casesList = service1.intiCasesDetail(id);
		for (AllDTO allDTO : casesList) {
			System.out.println(allDTO.toString());
		}
		request.setAttribute("casesList", casesList);
	}

	//堀越ローリングスペシャルメソッド（上ののパクリ）
	public String intiCasesDetail1ex(int id) throws UnsupportedEncodingException {

		CasesService service1 = new CasesService();
		ArrayList<AllDTO> casesList = service1.intiCasesDetail(id);
		for (AllDTO allDTO : casesList) {
			System.out.println(allDTO.toString());
		}
		request.setAttribute("casesList", casesList);

		return "/WEB-INF/jsp/cases_details.jsp";

	}

	/**
	 * 指定された案件IDに紐づくタスク一覧を取得し、
	 * リクエストスコープに tasksList として設定する。
	 *
	 * <p>取得したタスク情報は AllDTO に格納され、以下の項目を含む。</p>
	 * <ul>
	 *   <li>taskId（タスクID）</li>
	 *   <li>taskName（タスク名）</li>
	 *   <li>taskStatus（タスクステータス）</li>
	 *   <li>managerId（担当者ID）</li>
	 *   <li>userName（担当者名）</li>
	 *   <li>actualHoursSum（タスク実績工数合計）</li>
	 * </ul>
	 *
	 * @param id 案件ID
	 * @throws UnsupportedEncodingException 文字エンコーディング処理でエラーが発生した場合
	 */
	public void intiCasesDetail2(int id) throws UnsupportedEncodingException {

		CasesService service = new CasesService();
		ArrayList<AllDTO> tasksList = service.intiCasesDetail2(id);
		request.setAttribute("tasksList", tasksList);

	}

	/**
	 * 指定された案件IDに紐づく作業実績一覧を取得し、
	 * リクエストスコープに worksList として設定する。
	 *
	 * <p>取得した作業実績情報は AllDTO に格納され、以下の項目を含む。</p>
	 * <ul>
	 *   <li>workId（作業実績ID）</li>
	 *   <li>taskId（タスクID）</li>
	 *   <li>taskName（タスク名）</li>
	 *   <li>userId（作業者ID）</li>
	 *   <li>userName（作業者名）</li>
	 *   <li>workDate（作業日）</li>
	 *   <li>actualHours（実績工数）</li>
	 *   <li>workDescription（作業内容）</li>
	 * </ul>
	 *
	 * <p>作業日の降順、同日の場合は作業実績IDの降順で取得する。</p>
	 *
	 * @param id 案件ID
	 * @throws UnsupportedEncodingException 文字エンコーディング処理でエラーが発生した場合
	 */

	public void intiCasesDetail3(int id) throws UnsupportedEncodingException {

		CasesService service = new CasesService();
		ArrayList<AllDTO> worksList = service.intiCasesDetail3(id);
		request.setAttribute("worksList", worksList);

	}

	/**
	 * タスクを削除するメソッド
	 * @return String pageのurl
	 * @throws UnsupportedEncodingException
	 * @throws SQLException
	 */
	public String tasksDelete() throws UnsupportedEncodingException {
		String page = "/WEB-INF/jsp/cases_details.jsp";

		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("taskId"));

		CasesService service = new CasesService();
		TasksDTO dto = new TasksDTO();
		dto.setId(id);

		boolean result = service.tasksDelete(dto);
		if (result == true) {

			request.setAttribute("msg", "タスクの削除が完了しました。");
		} else {
			request.setAttribute("msg", "タスクの削除が失敗しました。");
		}

		return page;

	}

	//案件一覧の初期画面の表示のメソッド
	/**
	 * 
	 *  - 案件一覧の初期画面表示 -
	 *  返り値:案件一覧画面
	 * @return String page
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

	/**
	 * 
	 *  - 案件一覧の初期画面表示 -
	 *  返り値:案件登録画面のページリンク（編集モード）
	 * @return String page
	 * @throws UnsupportedEncodingException
	 */
	//案件一覧から編集ボタンで個別の案件を表示するcasesEditメソッド
	public String casesEdit() throws UnsupportedEncodingException {
		String page = "/WEB-INF/jsp/cases_regist.jsp";

		//値の取得
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));

		CasesService service = new CasesService();

		CasesDTO dto = service.casesEdit(id);

		ArrayList<UsersDTO> userList = service.pmList();
		request.setAttribute("cases", dto);
		request.setAttribute("userList", userList);

		return page;
	}

	/**
	 * 一覧表示メソッド
	 * @return String page
	 */
	public String selectAll() throws UnsupportedEncodingException {
		String page = "/WEB-INF/jsp/cases.jsp";

		//CasesServiceを呼びだす
		CasesService service = new CasesService();

		//案件一覧を表示
		ArrayList<AllDTO> casesList = service.selectAll();

		//reqestスコープに格納する
		request.setAttribute("casesList", casesList);

		//ページを返す
		return page;
	}

	//新規案件登録処理insertメソッド
	/**
	 * - 新規案件登録処理 -
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String insert() throws UnsupportedEncodingException {
		String page = "/WEB-INF/jsp/cases.jsp";

		//値の取得
		request.setCharacterEncoding("UTF-8");
		//String id = request.getParameter("id");登録すると自動でつくため。
		String caseName = request.getParameter("caseName");
		String caseCode = request.getParameter("caseCode");
		String customerName = request.getParameter("customerName");
		int pmId = Integer.parseInt(request.getParameter("pmId"));//担当PM
		String caseStatus = request.getParameter("status");
		String casePriority = request.getParameter("priority");
		String startDate = request.getParameter("startDate");
		if (startDate.equals("")) {
			startDate = "1970-12-31";
		}
		String plannedEndDate = request.getParameter("plannedEndDate");
		if (plannedEndDate.equals("")) {
			plannedEndDate = "1970-12-31";
		}
		double casePlannedHours = 9999.0;
		if (!request.getParameter("casePlannedHours").equals("")) {
			casePlannedHours = Double.parseDouble(request.getParameter("casePlannedHours"));
		}
		String caseDescription = request.getParameter("description");

		CasesDTO dto = new CasesDTO(0, caseName, caseCode, customerName, casePriority, pmId, caseStatus,
				startDate, plannedEndDate, caseDescription, casePlannedHours);

		CasesService service = new CasesService();
		//serviceに処理を依頼する
		int ans = service.insert(dto);
		//ちゃんと登録できたか確認
		if (ans == 1) {
			request.setAttribute("msg", "※登録完了！");
		} else {
			request.setAttribute("msg", "※登録失敗！案件コードが重複しています。");
			page = "/WEB-INF/jsp/cases_regist.jsp";
		}
		//ユーザー情報を全て取得する,
		//案件登録をした後の画面で案件一覧を出すために全部取ってくる。selectAll。しかし、casesiniti()メソッドとの違いがわからない…
		ArrayList<AllDTO> casesList = service.selectAll();
		request.setAttribute("casesList", casesList);
		this.initialize();
		this.casesRegist();
		service.close();

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
	 * - 案件編集の処理 -
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String update() throws UnsupportedEncodingException {
		String page = "/WEB-INF/jsp/cases.jsp";

		//値の取得
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));//編集だとidが必要かな。
		String caseName = request.getParameter("caseName");
		String caseCode = request.getParameter("caseCode");
		String customerName = request.getParameter("customerName");
		int pmId = Integer.parseInt(request.getParameter("pmId"));//担当PM
		String caseStatus = request.getParameter("status");
		String casePriority = request.getParameter("priority");
		String startDate = request.getParameter("startDate");
		if (startDate.equals("")) {
			startDate = "1970-12-31";
		}
		String plannedEndDate = request.getParameter("plannedEndDate");
		if (plannedEndDate.equals("")) {
			plannedEndDate = "1970-12-31";
		}
		double casePlannedHours = 9999.0;
		if (!request.getParameter("casePlannedHours").equals("")) {
			casePlannedHours = Double.parseDouble(request.getParameter("casePlannedHours"));
		}
		String caseDescription = request.getParameter("description");

		CasesDTO dto = new CasesDTO(id, caseName, caseCode, customerName, casePriority, pmId, caseStatus,
				startDate, plannedEndDate, caseDescription, casePlannedHours);

		CasesService service = new CasesService();
		//serviceに処理を依頼する
		int ans = service.update(dto);
		//ちゃんと登録できたか確認
		if (ans == 1) {
			request.setAttribute("msg", "登録完了！");
		} else {
			request.setAttribute("msg", "※登録失敗！案件コードが重複しています。");
			page = "/WEB-INF/jsp/cases_regist.jsp";
		}
		//ユーザー情報を全て取得する
		//案件登録をした後の画面で案件一覧を出すために全部取ってくる。selectAll。しかし、casesiniti()メソッドとの違いがわからない…
		ArrayList<AllDTO> casesList = service.selectAll();
		request.setAttribute("casesList", casesList);
		this.initialize();
		casesEdit();

		service.close();

		return page;
	}

	//案件一覧から案件登録に移動するメソッド
	public String casesRegist() throws UnsupportedEncodingException {
		// TODO 自動生成されたメソッド・スタブ
		String page = "/WEB-INF/jsp/cases_regist.jsp";

		CasesService service = new CasesService();

		ArrayList<UsersDTO> userList = service.pmList();
		request.setAttribute("userList", userList);
		return page;
	}

	public String changeStatus() throws UnsupportedEncodingException {
		// TODO 自動生成されたメソッド・スタブ
		String page = "/WEB-INF/jsp/cases_details.jsp";
		int caseId = Integer.parseInt(request.getParameter("id"));
		String buttonString = request.getParameter("buttonId");
		String buttonId = request.getParameter("buttonId");
		if (buttonId.equals("完了にする") || buttonId.equals("中止にする")) {
			buttonString = buttonString.substring(0, 2);
		} else if (buttonId.equals("進行中にする")
				|| buttonId.equals("未着手にする")) {
			buttonString = buttonString.substring(0, 3);
		}
		//System.out.println(buttonString);

		CasesService service = new CasesService();

		boolean result = service.status(caseId, buttonString);
		if (result == true) {
			request.setAttribute("msg", "変更完了しました");
		} else {
			request.setAttribute("msg", "変更失敗しました");
		}

		intiCasesDetail();

		return page;
	}

	//案件一覧から案件詳細に遷移するメソッド
	public String initiCasesDetail() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
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

}