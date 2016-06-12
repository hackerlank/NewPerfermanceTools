package cn.systoon.qc.domain;

public class JtlLog {
	private Integer id;
	private String jtlName;
	private Integer testPlanId;
	private String jtlPath;
	@Override
	public String toString() {
		return "JtlLog [id=" + id + ", jtlName=" + jtlName + ", testPlanId=" + testPlanId + ", jtlPath=" + jtlPath
				+ "]";
	}
	public JtlLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	public JtlLog(Integer id, String jtlName, Integer testPlanId, String jtlPath) {
		super();
		this.id = id;
		this.jtlName = jtlName;
		this.testPlanId = testPlanId;
		this.jtlPath = jtlPath;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getJtlName() {
		return jtlName;
	}
	public void setJtlName(String jtlName) {
		this.jtlName = jtlName;
	}
	public Integer getTestPlanId() {
		return testPlanId;
	}
	public void setTestPlanId(Integer testPlanId) {
		this.testPlanId = testPlanId;
	}
	public String getJtlPath() {
		return jtlPath;
	}
	public void setJtlPath(String jtlPath) {
		this.jtlPath = jtlPath;
	}
}

