package cn.systoon.qc.dao;

import java.util.List;

import cn.systoon.qc.domain.ApiInfo;

public interface ApiInfoDAO {
	
	
	public ApiInfo get(Integer id);
	
	public ApiInfo get(String path);
	
	public void save(ApiInfo apiInfo);
	
	public void update(ApiInfo apiInfo);
	
	public List<ApiInfo> getListWithWarnameId(String warnameId);
	
}
