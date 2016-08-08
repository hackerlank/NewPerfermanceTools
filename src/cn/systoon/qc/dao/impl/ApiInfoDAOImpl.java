package cn.systoon.qc.dao.impl;

import java.util.List;

import cn.systoon.qc.dao.ApiInfoDAO;
import cn.systoon.qc.dao.DAO;
import cn.systoon.qc.domain.ApiInfo;

public class ApiInfoDAOImpl extends DAO<ApiInfo> implements ApiInfoDAO {

	@Override
	public ApiInfo get(Integer id) {
		return null;
	}

	@Override
	public void save(ApiInfo apiInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ApiInfo apiInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ApiInfo> getListWithWarnameId(String warnameId) {
		String sql = "SELECT id,path from apiInfo where warnameid = ?";
		return getForList(sql, warnameId);
	
	}

	@Override
	public ApiInfo get(String id) {
		String sql = "SELECT id, path, warnameId, paramType, parameters, method from apiInfo where id = ?";
		return get(sql, id);
	}


}

