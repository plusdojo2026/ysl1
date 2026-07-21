package dto;

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
	private int casePlannedHours;
	private int caseSum;
	private int caseNow;
	private double actualHoursSum;
	
	//タスク
	private int taskId;
	private String taskName;
	private int managerId;
	private String taskStatus;
	private String taskPriority;
	private String deadline;
	private int progressRate;
	private double taskPlannedHours;
	private String taskDescription;

	//工数
	private int workId;
	private String workDate;
	private String actualHours;
	private String workDescription;
	
	
	//--------------------------------コンストラクタ----------------------------------
	public AllDTO() {
		super();
	}

	public AllDTO(int userId, String loginId, String loginPw, String userName, String mailAddress, boolean authority,
			boolean active, String created_at, String update_at, int caseId, String caseName, int caseCode,
			String customerName, String casePriority, int pmId, String caseStatus, String startDate,
			String plannedEndDate, String caseDescription, int casePlannedHours, int caseSum, int caseNow,
			double actualHoursSum, int taskId, String taskName, int managerId, String taskStatus, String taskPriority,
			String deadline, int progressRate, double taskPlannedHours, String taskDescription, int workId,
			String workDate, String actualHours, String workDescription) {
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
		this.taskId = taskId;
		this.taskName = taskName;
		this.managerId = managerId;
		this.taskStatus = taskStatus;
		this.taskPriority = taskPriority;
		this.deadline = deadline;
		this.progressRate = progressRate;
		this.taskPlannedHours = taskPlannedHours;
		this.taskDescription = taskDescription;
		this.workId = workId;
		this.workDate = workDate;
		this.actualHours = actualHours;
		this.workDescription = workDescription;
	}

	public int getUserId() {
		return userId;
	}	

	//------------------------------getter setter--------------------------------------
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

	public double getActualHoursSum() {
		return actualHoursSum;
	}

	public void setActualHoursSum(double actualHoursSum) {
		this.actualHoursSum = actualHoursSum;
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

	public int getProgressRate() {
		return progressRate;
	}

	public void setProgressRate(int progressRate) {
		this.progressRate = progressRate;
	}

	public double getTaskPlannedHours() {
		return taskPlannedHours;
	}

	public void setTaskPlannedHours(double taskPlannedHours) {
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

	public String getActualHours() {
		return actualHours;
	}

	public void setActualHours(String actualHours) {
		this.actualHours = actualHours;
	}

	public String getWorkDescription() {
		return workDescription;
	}

	public void setWorkDescription(String workDescription) {
		this.workDescription = workDescription;
	}
	
	
	
}