package cn.systoon.qc.domain;


/**
 * Parameters 表
 * 保存Key-Value格式参数名称，和参数【初始值】
 * @author perfermance
 *
 */
public class Parameters {
	private Integer id;
	private Integer apiId;
	private String paramName;
	private String paramValue;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getApiId() {
		return apiId;
	}
	public void setApiId(Integer apiId) {
		this.apiId = apiId;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public Parameters(Integer id, Integer apiId, String paramName, String paramValue) {
		super();
		this.id = id;
		this.apiId = apiId;
		this.paramName = paramName;
		this.paramValue = paramValue;
	}
	
	public Parameters(Integer apiId, String paramName, String paramValue) {
		super();
		this.apiId = apiId;
		this.paramName = paramName;
		this.paramValue = paramValue;
	}
	public Parameters() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Parameters [id=" + id + ", apiId=" + apiId + ", paramName=" + paramName + ", paramValue=" + paramValue
				+ "]";
	}
	
	
	
	

}

