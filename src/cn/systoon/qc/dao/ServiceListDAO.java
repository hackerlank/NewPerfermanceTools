package cn.systoon.qc.dao;

import java.util.List;

import cn.systoon.qc.domain.ServiceList;

public interface ServiceListDAO {
	
	
	public void save(ServiceList serviceList);
	
	public void update(ServiceList serviceList);
	
	public List<ServiceList> getAll();
	
	public ServiceList getServiceById(String id);
	
}
