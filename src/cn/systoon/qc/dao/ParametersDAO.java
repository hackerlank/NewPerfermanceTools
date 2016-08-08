package cn.systoon.qc.dao;

import java.util.List;

import cn.systoon.qc.domain.ApiInfo;
import cn.systoon.qc.domain.Parameters;

public interface ParametersDAO {
	
	
	public Parameters get(Integer id);
	
	public void save(Parameters apiInfo);
	
	public void update(Parameters apiInfo);
	
	public List<Parameters> getListWithApiId(Integer apiId);
	
}
