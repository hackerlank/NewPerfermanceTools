package cn.systoon.qc.domain;

public class SummaryReport {
	private Integer id;
	private String summaryName;
	private Integer testPlanId;
	private Integer jtlLogId;
	private String summaryReportPath;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSummaryName() {
		return summaryName;
	}
	public void setSummaryName(String summaryName) {
		this.summaryName = summaryName;
	}
	public Integer getTestPlanId() {
		return testPlanId;
	}
	public void setTestPlanId(Integer testPlanId) {
		this.testPlanId = testPlanId;
	}
	public Integer getJtlLogId() {
		return jtlLogId;
	}
	public void setJtlLogId(Integer jtlLogId) {
		this.jtlLogId = jtlLogId;
	}
	public String getSummaryReportPath() {
		return summaryReportPath;
	}
	public void setSummaryReportPath(String summaryReportPath) {
		this.summaryReportPath = summaryReportPath;
	}
	public SummaryReport(Integer id, String summaryName, Integer testPlanId, Integer jtlLogId, String summaryReportPath) {
		super();
		this.id = id;
		this.summaryName = summaryName;
		this.testPlanId = testPlanId;
		this.jtlLogId = jtlLogId;
		this.summaryReportPath = summaryReportPath;
	}
	public SummaryReport() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ServiceList [id=" + id + ", summaryName=" + summaryName + ", testPlanId=" + testPlanId + ", jtlLogId="
				+ jtlLogId + ", summaryReportPath=" + summaryReportPath + "]";
	}
}

