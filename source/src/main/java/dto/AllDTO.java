package dto;

import java.math.BigDecimal;

/**
 * AllDTO オールオブジェクトのJAVAbeans
 * 未定義の項目のみ以下に記載する
 * @param caseId
 * @param caseSum
 * @param caseNow
 * @param actualHoursSum
 * @param taskId
 * @param workId
 * @author 石田
 */

public class AllDTO {
	//ユーザー
	private int userId;
	private String loginId;
	private String loginPw;
	private String userName;
	private String mailAddress;
	private boolean authority;
	private boolean active;
	private String created_at;
	private String update_at;

	//案件
	private int caseId;
	private String caseName;
	private int caseCode;
	private String customerName;
	private String casePriority;
	private int pmId;
	private String caseStatus;
	private String startDate;
	private String plannedEndDate;
	private String caseDescription;
	private int casePlannedHours; //案件予定工数
	private int caseSum; //総タスク数
	private int caseNow; //完了タスク数
	private BigDecimal actualHoursSum; //実績工数の合計
	private int caseProgressRate; //案件進捗バー（完了タスク/総タスク）

	//タスク
	private int taskId;
	private String taskName;
	private int managerId;
	private String taskStatus;
	private String taskPriority;
	private String deadline;
	private int taskProgressRate; //タスク進捗バー（任意で設定）
	private BigDecimal taskPlannedHours; //タスク予定工数
	private String taskDescription;

	//工数
	private int workId;
	private String workDate;
	private BigDecimal actualHours;
	private String workDescription;

	//ダッシュボード
	private int inProgressCase; //進行中案件
	private int assignedTask; //自分の担当タスク
	private int deadlineNumber; //期限超過タスク 

	//月次集計
	private BigDecimal monthlyTotalHours; //月合計工数
	private int caseCount; //集計案件数
	private int memberCount; //稼働メンバー数
	private int workRate; //メンバー別集計の全体に占める割合

	//--------------------------------コンストラクタ----------------------------------

	public AllDTO() {
		super();
	}

	public AllDTO(int userId, String loginId, String loginPw, String userName, String mailAddress, boolean authority,
			boolean active, String created_at, String update_at, int caseId, String caseName, int caseCode,
			String customerName, String casePriority, int pmId, String caseStatus, String startDate,
			String plannedEndDate, String caseDescription, int casePlannedHours, int caseSum, int caseNow,
			BigDecimal actualHoursSum, int caseProgressRate, int taskId, String taskName, int managerId,
			String taskStatus, String taskPriority, String deadline, int taskProgressRate, BigDecimal taskPlannedHours,
			String taskDescription, int workId, String workDate, BigDecimal actualHours, String workDescription,
			int inProgressCase, int assignedTask, int deadlineNumber, BigDecimal monthlyTotalHours, int caseCount,
			int memberCount, int workRate) {
		super();
		this.userId = userId;
		this.loginId = loginId;
		this.loginPw = loginPw;
		this.userName = userName;
		this.mailAddress = mailAddress;
		this.authority = authority;
		this.active = active;
		this.created_at = created_at;
		this.update_at = update_at;
		this.caseId = caseId;
		this.caseName = caseName;
		this.caseCode = caseCode;
		this.customerName = customerName;
		this.casePriority = casePriority;
		this.pmId = pmId;
		this.caseStatus = caseStatus;
		this.startDate = startDate;
		this.plannedEndDate = plannedEndDate;
		this.caseDescription = caseDescription;
		this.casePlannedHours = casePlannedHours;
		this.caseSum = caseSum;
		this.caseNow = caseNow;
		this.actualHoursSum = actualHoursSum;
		this.caseProgressRate = caseProgressRate;
		this.taskId = taskId;
		this.taskName = taskName;
		this.managerId = managerId;
		this.taskStatus = taskStatus;
		this.taskPriority = taskPriority;
		this.deadline = deadline;
		this.taskProgressRate = taskProgressRate;
		this.taskPlannedHours = taskPlannedHours;
		this.taskDescription = taskDescription;
		this.workId = workId;
		this.workDate = workDate;
		this.actualHours = actualHours;
		this.workDescription = workDescription;
		this.inProgressCase = inProgressCase;
		this.assignedTask = assignedTask;
		this.deadlineNumber = deadlineNumber;
		this.monthlyTotalHours = monthlyTotalHours;
		this.caseCount = caseCount;
		this.memberCount = memberCount;
		this.workRate = workRate;
	}

	//------------------------------getter setter--------------------------------------
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginPw() {
		return loginPw;
	}

	public void setLoginPw(String loginPw) {
		this.loginPw = loginPw;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public boolean isAuthority() {
		return authority;
	}

	public void setAuthority(boolean authority) {
		this.authority = authority;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdate_at() {
		return update_at;
	}

	public void setUpdate_at(String update_at) {
		this.update_at = update_at;
	}

	public int getCaseId() {
		return caseId;
	}

	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public int getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(int caseCode) {
		this.caseCode = caseCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCasePriority() {
		return casePriority;
	}

	public void setCasePriority(String casePriority) {
		this.casePriority = casePriority;
	}

	public int getPmId() {
		return pmId;
	}

	public void setPmId(int pmId) {
		this.pmId = pmId;
	}

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getPlannedEndDate() {
		return plannedEndDate;
	}

	public void setPlannedEndDate(String plannedEndDate) {
		this.plannedEndDate = plannedEndDate;
	}

	public String getCaseDescription() {
		return caseDescription;
	}

	public void setCaseDescription(String caseDescription) {
		this.caseDescription = caseDescription;
	}

	public int getCasePlannedHours() {
		return casePlannedHours;
	}

	public void setCasePlannedHours(int casePlannedHours) {
		this.casePlannedHours = casePlannedHours;
	}

	public int getCaseSum() {
		return caseSum;
	}

	public void setCaseSum(int caseSum) {
		this.caseSum = caseSum;
	}

	public int getCaseNow() {
		return caseNow;
	}

	public void setCaseNow(int caseNow) {
		this.caseNow = caseNow;
	}

	public BigDecimal getActualHoursSum() {
		return actualHoursSum;
	}

	public void setActualHoursSum(BigDecimal actualHoursSum) {
		this.actualHoursSum = actualHoursSum;
	}

	public int getCaseProgressRate() {
		return caseProgressRate;
	}

	public void setCaseProgressRate(int caseProgressRate) {
		this.caseProgressRate = caseProgressRate;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getTaskPriority() {
		return taskPriority;
	}

	public void setTaskPriority(String taskPriority) {
		this.taskPriority = taskPriority;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public int getTaskProgressRate() {
		return taskProgressRate;
	}

	public void setTaskProgressRate(int taskProgressRate) {
		this.taskProgressRate = taskProgressRate;
	}

	public BigDecimal getTaskPlannedHours() {
		return taskPlannedHours;
	}

	public void setTaskPlannedHours(BigDecimal taskPlannedHours) {
		this.taskPlannedHours = taskPlannedHours;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public int getWorkId() {
		return workId;
	}

	public void setWorkId(int workId) {
		this.workId = workId;
	}

	public String getWorkDate() {
		return workDate;
	}

	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}

	public BigDecimal getActualHours() {
		return actualHours;
	}

	public void setActualHours(BigDecimal actualHours) {
		this.actualHours = actualHours;
	}

	public String getWorkDescription() {
		return workDescription;
	}

	public void setWorkDescription(String workDescription) {
		this.workDescription = workDescription;
	}

	public int getInProgressCase() {
		return inProgressCase;
	}

	public void setInProgressCase(int inProgressCase) {
		this.inProgressCase = inProgressCase;
	}

	public int getAssignedTask() {
		return assignedTask;
	}

	public void setAssignedTask(int assignedTask) {
		this.assignedTask = assignedTask;
	}

	public int getDeadlineNumber() {
		return deadlineNumber;
	}

	public void setDeadlineNumber(int deadlineNumber) {
		this.deadlineNumber = deadlineNumber;
	}

	public BigDecimal getMonthlyTotalHours() {
		return monthlyTotalHours;
	}

	public void setMonthlyTotalHours(BigDecimal monthlyTotalHours) {
		this.monthlyTotalHours = monthlyTotalHours;
	}

	public int getCaseCount() {
		return caseCount;
	}

	public void setCaseCount(int caseCount) {
		this.caseCount = caseCount;
	}

	public int getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}

	public int getWorkRate() {
		return workRate;
	}

	public void setWorkRate(int workRate) {
		this.workRate = workRate;
	}

}