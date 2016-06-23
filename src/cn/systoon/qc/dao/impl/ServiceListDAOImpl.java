package cn.systoon.qc.dao.impl;

import java.util.List;

import cn.systoon.qc.dao.DAO;
import cn.systoon.qc.dao.ServiceListDAO;
import cn.systoon.qc.domain.ServiceList;

public class ServiceListDAOImpl extends DAO<ServiceList> implements ServiceListDAO{

	@Override
	public void save(ServiceList serviceList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ServiceList serviceList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ServiceList> getAll() {
		String sql = "SELECT id,project_CN projectCN from servicelist";
		return getForList(sql);
	}

	@Override
	public ServiceList getServiceById(String id) {
		String sql = "SELECT id,project_CN projectCN,pressIP pressIp, prdomain,warname from servicelist where id = ?";
		return get(sql, id);
	}

}

