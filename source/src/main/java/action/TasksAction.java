package action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import dto.AllDTO;
import dto.CasesDTO;
import dto.TasksDTO;
import dto.UsersDTO;
import service.TasksService;

public class TasksAction {

	HttpServletRequest request;

	//コンストラクタ
	public TasksAction(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * 一覧表示メソッド
	 * @return String page
	 */
	public String selectAll() throws UnsupportedEncodingException {
		String page = "/WEB-INF/jsp/tasks.jsp";

		//TaskServiceを呼びだす
		TasksService service = new TasksService();

		//タスク一覧を表示
		ArrayList<AllDTO> taskList = service.selectAll();

		//reqestスコープに格納する
		request.setAttribute("taskList", taskList);

		//ページを返す
		return page;
	}

	/**
	 * - タスク詳細表示 -
	 * @return String page;
	 * jspへ渡す値:
	 * ・detailsList(AllDTO)
	 * 
	 * @author haruto.tanaka
	 */
	public String details() throws UnsupportedEncodingException {
		String page = "/WEB-INF/jsp/tasks_details.jsp";
		// タスクID取得
		int taskId = Integer.parseInt(request.getParameter("taskId"));

		// 詳細取得
		TasksService service = new TasksService();
		AllDTO detailsList = service.details(taskId);

		//そのタスクの工数一覧取得
		ArrayList<AllDTO> worksList = service.selectByTasksId(taskId);

		request.setAttribute("detailsList", detailsList);
		request.setAttribute("worksList", worksList);

		return page;
	}

	/**
	 * - タスク登録・編集 -
	 * 
	 * 返り値: タスク登録・編集画面のページリンク
	 * @return String page
	 * 
	 * 
	 * jspへ渡す値
	 * 共通:
	 * ・registList[案件名、PM名をリストで取得]
	 * +編集モードのみ:
	 * ・taskList[選択したタスクの詳細を取得]
	 * ・mode[編集モードの判別に使用]
	 * 
	 * 
	 * @author haruto.tanaka
	 */
	public String functions() throws UnsupportedEncodingException {
		String page = "/WEB-INF/jsp/tasks_regist.jsp";
		String mode = "regist";
		TasksService service = new TasksService();

		int caseId = 0;

		//タスクIDをStringでいったん取得（nullなら新規登録、任意の値ならそのタスクの編集）
		String taskIdStr = request.getParameter("taskId");
		String caseIdStr = request.getParameter("caseId");

		if (caseIdStr != null) {
			caseId = Integer.parseInt(caseIdStr);
		}

		//タスクIDがあるなら、編集モード -----------------------------
		if (taskIdStr != null && !taskIdStr.isEmpty()) {
			//タスクIDを取得
			int taskId = Integer.parseInt(taskIdStr);

			//編集モードを決定
			mode = "edit";

			//タスク詳細を取得
			TasksDTO taskList = service.edit(taskId);

			//タスク詳細と、編集モードの状態を格納
			request.setAttribute("taskList", taskList);
		} else if (caseId == 0) {
			return "/WEB-INF/jsp/tasks.jsp";
		}

		//service呼び出し、案件名とPM名のリストを格納
		ArrayList<CasesDTO> casesList = service.selectCases();
		ArrayList<UsersDTO> pmList = service.selectPM();

		request.setAttribute("casesList", casesList);
		request.setAttribute("pmList", pmList);
		request.setAttribute("caseId", caseId);

		request.setAttribute("mode", mode);

		return page;
	}

	/**
	 * - タスク登録処理 -
	 * 
	 * 
	 * @author haruto.tanaka
	 */
	public String insert() throws UnsupportedEncodingException {

		String page = "/WEB-INF/jsp/cases_details.jsp";

		// パラメータ取得
		String caseIdStr = request.getParameter("caseId");
		String taskName = request.getParameter("taskName");
		String managerIdStr = request.getParameter("managerId");
		String taskStatus = request.getParameter("taskStatus");
		String taskPriority = request.getParameter("taskPriority");
		String taskPlannedHoursStr = request.getParameter("taskPlannedHours");
		String progressRateStr = request.getParameter("progressRate");
		String startDate = request.getParameter("startDate");
		String deadline = request.getParameter("deadline");
		String taskDescription = request.getParameter("taskDescription");

		// DTOへ格納
		TasksDTO tDTO = new TasksDTO();

		tDTO.setCaseId(Integer.parseInt(caseIdStr));
		tDTO.setTaskName(taskName);
		tDTO.setManagerId(Integer.parseInt(managerIdStr));
		tDTO.setTaskStatus(taskStatus);
		tDTO.setTaskPriority(taskPriority);
		tDTO.setTaskPlannedHours(Double.parseDouble(taskPlannedHoursStr));
		tDTO.setProgressRate(Integer.parseInt(progressRateStr));
		tDTO.setStartDate(startDate);
		tDTO.setDeadline(deadline);
		tDTO.setTaskDescription(taskDescription);

		// 登録処理
		TasksService service = new TasksService();

		boolean result = service.insert(tDTO);

		if (!result) {
			page = "/WEB-INF/jsp/tasks_regist.jsp";
		}

		return page;
	}

	/**
	 * - 更新処理 -
	 * 
	 * 
	 * @author haruto.tanaka
	 */
	public String update() throws UnsupportedEncodingException {
		String page = "/WEB-INF/jsp/cases_details.jsp";

		// パラメータ取得
		String taskIdStr = request.getParameter("taskId");
		String caseIdStr = request.getParameter("caseId");
		String managerIdStr = request.getParameter("managerId");
		String taskName = request.getParameter("taskName");
		String taskStatus = request.getParameter("taskStatus");
		String taskPriority = request.getParameter("taskPriority");
		String taskPlannedHoursStr = request.getParameter("taskPlannedHours");
		String progressRateStr = request.getParameter("progressRate");
		String startDate = request.getParameter("startDate");
		String deadline = request.getParameter("deadline");
		String taskDescription = request.getParameter("taskDescription");

		// DTOへ格納
		TasksDTO tDTO = new TasksDTO();

		tDTO.setId(Integer.parseInt(taskIdStr));
		tDTO.setCaseId(Integer.parseInt(caseIdStr));
		tDTO.setManagerId(Integer.parseInt(managerIdStr));
		tDTO.setTaskName(taskName);
		tDTO.setTaskStatus(taskStatus);
		tDTO.setTaskPriority(taskPriority);
		tDTO.setTaskPlannedHours(Double.parseDouble(taskPlannedHoursStr));
		tDTO.setProgressRate(Integer.parseInt(progressRateStr));
		tDTO.setStartDate(startDate);
		tDTO.setDeadline(deadline);
		tDTO.setTaskDescription(taskDescription);

		// 更新処理
		TasksService service = new TasksService();
		boolean result = service.update(tDTO);

		if (!result) {
			page = "/WEB-INF/jsp/tasks_regist.jsp";
		}
		CasesAction action = new CasesAction(request);
		action.intiCasesDetail1();

		return page;
	}

	/**
	 * - タスクステータス更新処理 -
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 * @author haruto.tanaka
	 */
	public String updateStatus() throws UnsupportedEncodingException {

		String page = "/WEB-INF/jsp/tasks_details.jsp";

		// パラメータ取得
		String taskIdStr = request.getParameter("taskId");
		String taskStatus = request.getParameter("buttonId");
		int taskId = 0;

		if (taskIdStr != null) {
			taskId = Integer.parseInt(taskIdStr);
		} else {
			System.out.println("タスクIDを入れろやボケェ！");
			//中断
			return page;
		}

		// 更新処理
		TasksService service = new TasksService();
		boolean result = service.updateStatus(taskId, taskStatus);

		if (!result) {
			System.out.println("お前は間違いを犯した。");
		}
		//再度タスク詳細を取得
		AllDTO detailsList = service.details(taskId);
		request.setAttribute("detailsList", detailsList);

		return page;
	}

	/**
	 * - 削除処理 -
	 *
	 * @author haruto.tanaka
	 */
	public String delete() throws UnsupportedEncodingException {

		String page = "/WEB-INF/jsp/cases_details.jsp";

		// パラメータ取得
		String taskIdStr = request.getParameter("taskId");

		// タスクID取得
		int taskId = Integer.parseInt(taskIdStr);

		// 削除処理
		TasksService service = new TasksService();
		boolean result = service.delete(taskId);

		if (!result) {
			page = "/WEB-INF/jsp/tasks_regist.jsp";
		}
		//案件詳細取得
		CasesAction action = new CasesAction(request);
		action.initiCasesDetail();

		return page;
	}
}