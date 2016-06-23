package cn.systoon.qc.domain;

import java.util.Date;

public class TestPlan {
	private Integer id;
	private String testPlanName;
	private String descript;
	private Integer apiId;
	private Integer testPlanType;
	private String parameters;
	private String jmxPlan;
	private Date createTime;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTestPlanName() {
		return testPlanName;
	}

	public void setTestPlanName(String testPlanName) {
		this.testPlanName = testPlanName;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public Integer getApiId() {
		return apiId;
	}

	public void setApiId(Integer apiId) {
		this.apiId = apiId;
	}

	public Integer getTestPlanType() {
		return testPlanType;
	}

	public void setTestPlanType(Integer testPlanType) {
		this.testPlanType = testPlanType;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getJmxPlan() {
		return jmxPlan;
	}

	public void setJmxPlan(String jmxPlan) {
		this.jmxPlan = jmxPlan;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public TestPlan(Integer id, String testPlanName, String descript, Integer apiId, Integer testPlanType,
			String parameters, String jmxPlan, Date createTime) {
		super();
		this.id = id;
		this.testPlanName = testPlanName;
		this.descript = descript;
		this.apiId = apiId;
		this.testPlanType = testPlanType;
		this.parameters = parameters;
		this.jmxPlan = jmxPlan;
		this.createTime = createTime;
	}

	public TestPlan(){
		
	}
	
	@Override
	public String toString() {
		return "TestPlan [id=" + id + ", testPlanName=" + testPlanName + ", descript=" + descript + ", apiId=" + apiId
				+ ", testplanType=" + testPlanType + ", parameters=" + parameters + ", jmxPlan=" + jmxPlan
				+ ", createTime=" + createTime + "]";
	}
	
	
	
}
