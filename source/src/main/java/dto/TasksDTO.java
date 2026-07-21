package dto;

/**
 ** TasksDTO タスクオブジェクトのJAVAbeans
 *  @param  id
 *  @param  caseId
 *  @param  taskName
 *  @param  manegerId
 *  @param  taskStatus
 *  @param  taskPriority
 *  @param  deadline
 *  @param  progressRate
 *  @param  startDate
 *  @param  taskPlannedHours
 *  @param  taskDescription
 *  @author 石田
 */

public class TasksDTO { 
	private int id;
	private int caseId;
	private String taskName;
	private int managerId;
	private String taskStatus;
	private String taskPriority;
	private String deadline;
	private int progressRate;
	private String startDate;
	private double taskPlannedHours;
	private String taskDescription;
	
	public TasksDTO(int id, int caseId, String taskName, int managerId, String taskStatus, String taskPriority,
			String deadline, int progressRate, String startDate, double taskPlannedHours, String taskDescription) {
		super();
		this.id = id;
		this.caseId = caseId;
		this.taskName = taskName;
		this.managerId = managerId;
		this.taskStatus = taskStatus;
		this.taskPriority = taskPriority;
		this.deadline = deadline;
		this.progressRate = progressRate;
		this.startDate = startDate;
		this.taskPlannedHours = taskPlannedHours;
		this.taskDescription = taskDescription;
	}
	public TasksDTO() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCaseId() {
		return caseId;
	}
	public void setCaseId(int caseId) {
		this.caseId = caseId;
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
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
	
}