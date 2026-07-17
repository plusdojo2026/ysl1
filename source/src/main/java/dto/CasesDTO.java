package dto;

public class CasesDTO {

	private int id;
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

	public CasesDTO() {
		super();
	}

	public CasesDTO(int id, String caseName, int caseCode, String customerName, String casePriority, int pmId,
			String caseStatus, String startDate, String plannedEndDate, String caseDescription, int casePlannedHours) {
		super();
		this.id = id;
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
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
}
