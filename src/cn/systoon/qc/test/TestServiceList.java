package cn.systoon.qc.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import cn.systoon.qc.dao.impl.ServiceListDAOImpl;
import cn.systoon.qc.domain.ServiceList;

public class TestServiceList {

	@Test
	public void testGetAll() {
		List<ServiceList> serviceLists = new ServiceListDAOImpl().getAll();
		for(ServiceList serviceList:serviceLists){
			System.out.println(serviceList.getId() + "  " + serviceList.getProjectCN() + "  " + serviceList.getPrdomain() + "  " + serviceList.getPressIp() + "  ");
		}
	}

}

