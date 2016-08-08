package cn.systoon.qc.dao.impl;

import java.util.List;
import cn.systoon.qc.dao.DAO;
import cn.systoon.qc.dao.ParametersDAO;
import cn.systoon.qc.domain.Parameters;;

public class ParametersDAOImpl extends DAO<Parameters> implements ParametersDAO{

	@Override
	public Parameters get(Integer id) {
		return null;
	}

	@Override
	public void save(Parameters apiInfo) {
		
	}

	@Override
	public void update(Parameters apiInfo) {
		
	}

	@Override
	public List<Parameters> getListWithApiId(Integer apiId) {
		 String sql = "SELECT param_name paramName,param_value paramValue from parameter where api_id = ?";
		return getForList(sql, apiId);
	}

}

