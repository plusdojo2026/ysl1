package dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class WorksDTO implements Serializable{
	//フィールド
	private int id;
	private int userId;
	private int taskId;
	private String workDate;
	private BigDecimal actualHours;
	private String workDescription;
	private int progressRate;
	
	
	//コンストラクタ
	public WorksDTO(int id, int userId, int taskId, String workDate, BigDecimal actualHours, String workDescription,int progressRate) {
		super();
		this.id = id;
		this.userId = userId;
		this.taskId = taskId;
		this.workDate = workDate;                //作業日
		this.actualHours = actualHours;          //実績工数
		this.workDescription = workDescription;  //作業内容
		this.progressRate = progressRate;
	}

	//引数のないコンストラクタ
	public WorksDTO() {
		super();
		// TODO 自動生成されたコンストラクター・スタブ
	}

	
	
	//ゲッターセッター
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
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

	public int getProgressRate() {
		return progressRate;
	}

	public void setProgressRate(int progressRate) {
		this.progressRate = progressRate;
	}

	
	
	

}
