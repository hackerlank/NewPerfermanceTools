package cn.systoon.qc.domain;

import java.util.Date;

public class TestPlan {
	private Integer id;
	private String testPlanName;
	private String descript;
	private Integer apiId;
	private Integer jmxPlanType;
	private Integer paramType;
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

	public Integer getJmxPlanType() {
		return jmxPlanType;
	}

	public void setJmxPlanType(Integer jmxPlanType) {
		this.jmxPlanType = jmxPlanType;
	}
	
	public Integer getParamType() {
		return paramType;
	}

	public void setParamType(Integer paramType) {
		this.paramType = paramType;
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

	
	/**
	 * 
	 * @param testPlanName  测试计划名称
	 * @param descript		测试计划描述
	 * @param apiId         对应接口的apiId
	 * @param jmxPlanType	测试计划类型   1 为单接口   2为聚合场景
	 * @param parameters    单接口时，保存实际输入的参数值（2聚合场景时，查看聚合场景步骤列表）
	 * @param jmxPlan		保存测试计划路径
	 * @param createTime    测试计划创建时间
	 */
	public TestPlan(String testPlanName, String descript, Integer apiId, Integer jmxPlanType,
			Integer paramType,String parameters, String jmxPlan, Date createTime) {
		super();
		this.testPlanName = testPlanName;
		this.descript = descript;
		this.apiId = apiId;
		this.jmxPlanType = jmxPlanType;
		this.paramType = paramType;
		this.parameters = parameters;
		this.jmxPlan = jmxPlan;
		this.createTime = createTime;
	}

	public TestPlan(){
		
	}

	@Override
	public String toString() {
		return "TestPlan [id=" + id + ", testPlanName=" + testPlanName + ", descript=" + descript + ", apiId=" + apiId
				+ ", jmxPlanType=" + jmxPlanType + ", paramType=" + paramType + ", parameters=" + parameters
				+ ", jmxPlan=" + jmxPlan + ", createTime=" + createTime + "]";
	}
	

	
	
	
}
