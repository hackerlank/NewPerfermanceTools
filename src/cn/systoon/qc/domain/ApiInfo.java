package cn.systoon.qc.domain;

import java.util.Date;

public class ApiInfo {
	private Integer id;
	private String apiName;
	private String warnameId;
	private String path;
	private String paramaters;
	private String method;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getApiName() {
		return apiName;
	}
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	public String getWarnameId() {
		return warnameId;
	}
	public void setWarnameId(String warnameId) {
		this.warnameId = warnameId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getParamaters() {
		return paramaters;
	}
	public void setParamaters(String paramaters) {
		this.paramaters = paramaters;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public ApiInfo(Integer id, String apiName, String warnameId, String path, String paramaters, String method) {
		super();
		this.id = id;
		this.apiName = apiName;
		this.warnameId = warnameId;
		this.path = path;
		this.paramaters = paramaters;
		this.method = method;
	}
	
	public ApiInfo(){
		
	}
	@Override
	public String toString() {
		return "ApiInfo [id=" + id + ", apiName=" + apiName + ", warnameId=" + warnameId + ", path=" + path
				+ ", paramaters=" + paramaters + ", method=" + method + "]";
	}
	
	
}

