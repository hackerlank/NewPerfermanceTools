package cn.systoon.qc.domain;

import java.util.Date;

public class ApiInfo {
	private Integer id;
	private String path;
	private String warnameId;
	private Integer paramType;
	private String parameters;
	private String method;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getWarnameId() {
		return warnameId;
	}
	public void setWarnameId(String warnameId) {
		this.warnameId = warnameId;
	}
	public int getParamType() {
		return paramType;
	}
	public void setParamType(int paramType) {
		this.paramType = paramType;
	}
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public ApiInfo(Integer id, String path, String warnameId, int paramType, String parameters, String method) {
		super();
		this.id = id;
		this.path = path;
		this.warnameId = warnameId;
		this.paramType = paramType;
		this.parameters = parameters;
		this.method = method;
	}
	public ApiInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ApiInfo [id=" + id + ", path=" + path + ", warnameId=" + warnameId + ", paramType=" + paramType
				+ ", parameters=" + parameters + ", method=" + method + "]";
	}
	
	
	
}

